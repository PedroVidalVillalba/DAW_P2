#!/bin/bash

files="src/ministore/*.java"
srcpath="src/"
outpath="WEB-INF/classes"
servlet_api="/usr/share/tomcat10/lib/servlet-api.jar"

javac -cp $servlet_api -d $outpath --source-path $srcpath $files
