#!/bin/bash

function compile_dir()  # $1 is the dir to get it
{
    cd $1
    ./build.sh
    cd ..
}

echo "** Building all"

compile_dir "datasaver-service"

compile_dir "message-publisher-service"

compile_dir "message-retriever-service"

# remove # to add pause in script execution
#read -p "Press any key to continue... "

echo "** Done all"
