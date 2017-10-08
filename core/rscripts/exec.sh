#!/bin/bash 
while IFS='' read -r line || [[ -n "$line" ]]; do
      phantomjs ./getaffiliation.js $line 
done < "$1"
