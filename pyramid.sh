#!/bin/bash
shopt -s globstar
for file in ./**/*.tif
do
	layers=$(identify "$file" | wc -l)
	if [ "$layers" -eq "1" ]
	then
		echo Converting $file
		convert $file -define tiff:tile-geometry=256x256 -compress lzw 'ptif:temp.tif'
		status=$?
		if [ $status -ne 0 ]; then
		  echo Error converting $file
		  rm temp.tif
		else
      rm $file
      mv temp.tif $file
		fi
	fi
done

