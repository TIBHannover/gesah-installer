#!/bin/bash

URL_PREFIX=""
url="$URL_PREFIX/authenticate"
newline=$'\n'
loginName=""
loginPassword=""
loginForm="Log in"
models=CONTENT
# Log in and get session cookies
COOKIE=$(curl -k -c - --data-urlencode "loginName=$loginName" --data-urlencode "loginPassword=$loginPassword" -d "loginForm=$loginForm" $url)
echo "Starting dump..."
dump_file_path="$models.NQ"
target_dir=gesah-content
status_code=$(curl -k --write-out %{http_code} --cookie "$session/cookies.txt" "$URL_PREFIX/dumpRestore/dump/$models.nq?which=$models" -o "$dump_file_path" --cookie <(echo "$COOKIE"))

if [[ "$status_code" -ne 200 ]]; then
    echo "Dump failed, status code is $status_code, check login credentials, parameters and try again."
fi

if [ -s $dump_file_path ]; then
    fgrep -v _:B $dump_file_path | fgrep -v http://vitro.mannlib.cornell.edu/default/vitro-kb-inf | sort -o "$dump_file_path.filtered"
    diff $dump_file_path.filtered.0 $dump_file_path.filtered > "CHANGES"
    removed_lines=$(grep '^<' CHANGES | cut -c3-)
    added_lines=$(grep '^>' CHANGES | cut -c3-)
    commit_message='Content dump made on '"$(date)"
    if [ -n "$added_lines" ]; then
	    commit_message+="${newline}${newline}Added:${newline}${newline}${added_lines}"
    fi
    if [ -n "$removed_lines" ]; then
	    commit_message+="${newline}${newline}Removed:${newline}${newline}${removed_lines}"
    fi
    if ! diff -q $dump_file_path.filtered.0 $dump_file_path.filtered &>/dev/null; then
      7z a -t7z -ms=on -myx=9 -mx=9 -mf=off -m0=PPMd:mem2g:o32 "$target_dir/$models.7z" "$dump_file_path"
      cd $target_dir
      git add "$models.7z"
      git commit -m "$commit_message"
      git push origin main
      cd ..
    fi
    mv -f $dump_file_path.filtered $dump_file_path.filtered.0
    rm -f $dump_file_path
else
    echo "Dump file is empty, deleting..."
    rm -f "$dump_file_path"
fi

