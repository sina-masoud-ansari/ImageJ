#!/bin/bash

HOST="login.uoa.nesi.org.nz"

ant clean

echo "Zipping up ..."
cd ..
if [ -e ImageJ.zip ]; then
  rm ImageJ.zip
fi
zip -r ImageJ.zip ImageJ -x 'ImageJ/.git/*'

echo "Copy over to NeSI cluster ..."
scp ImageJ.zip ${HOST}:~

#echo "Run tests ..."
ssh ${HOST} 'unzip -o ImageJ.zip; cd ImageJ; ant cluster-performance'
