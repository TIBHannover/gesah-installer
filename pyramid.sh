#!/bin/bash
shopt -s globstar
for file in ./**/*.tif
do
	layers=$(identify "$file" | wc -l)
	if [ "$layers" -eq "1" ]
	then
		echo Converting $file
		convert $file -define tiff:tile-geometry=256x256 -compress lzw 'ptif:temp.tif'
		rm $file
		mv temp.tif $file
	fi
done

