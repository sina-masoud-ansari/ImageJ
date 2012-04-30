#!/bin/bash

ant clean

echo "Zipping up ..."
cd ..
if [ -e ImageJ.zip ]; then
  rm ImageJ.zip
fi
zip -r ImageJ.zip ImageJ -x 'ImageJ/.git/*'

echo "Copy over to Hexa ..."
scp ImageJ.zip hexa.ece.auckland.ac.nz:~

echo "Run tests ..."
ssh hexa.ece.auckland.ac.nz 'unzip -o ImageJ.zip; cd ImageJ; ant performance' 

cd ImageJ
echo "Download results ..."
scp -r hexa.ece.auckland.ac.nz:~/ImageJ/results .

echo "Processing results ..."
./results-mysql.sh
