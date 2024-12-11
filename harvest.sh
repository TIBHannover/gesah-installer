#!/bin/bash
#Requires jq, curl
cd /home/litvinovg/LIDO
URL_PREFIX="http://127.0.0.1:8080/gesah"
VALID_OBJECTS_URL="${URL_PREFIX}/api/rest/1/cultural_object/get_valid_objects"
SECONDS=0
echo `date`
echo "harvest started"
COOKIE=$(curl -s -k -X POST -c - ${URL_PREFIX}/api/login -H  "accept: */*" -d "username=" -d "password=")
curl -k -s -X POST $VALID_OBJECTS_URL -H  "accept: */*" -H  "Content-Type: application/json" -d "{}" -o valid_objects.json --cookie <(echo "$COOKIE")
VALID_OBJECTS=`cat valid_objects.json | jq -r '.cultural_object[].cultural_object.value'`
while IFS= read -r coUri; do
    ID=`cut -d "/" -f5 <<< $coUri`
    curl -k -s -X POST "${URL_PREFIX}/api/rest/1/cultural_object/lido_export" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"resource_id\":\"${coUri}\"}" -o response.json --cookie <(echo "$COOKIE")
    IS_VALID=`cat response.json | jq -r '.valid'`
    if [ "$IS_VALID" = true ] ; then
        cat response.json | jq -r '.lido' > tmp.xml
        if [ ! -f collection/${ID}.xml ] || ! $(cmp --silent "tmp.xml" "collection/${ID}.xml") ; then
            if [ ! -f collection/${ID}.xml ] ; then
                echo "Created new xml collection/${ID}"
            else
                echo "Updated xml collection/${ID}"
            fi
            mv tmp.xml collection/${ID}.xml
        else
            echo "Skipping xml for ${ID} as it didn't change"
        fi
    else
        echo "response for ${ID} is invalid"
    fi
done <<< "$VALID_OBJECTS"
duration=$SECONDS
echo "$((duration / 60)) minutes and $((duration % 60)) seconds elapsed."
