#!/bin/bash

function log() {
    echo "### [SCRIPT-INFO] " "$@"
}

log "Making tmp dir..."
log $(pwd)
mkdir -pv tmp
log "Entering tmp dir..."
log $(pwd)
cd tmp
log "Copying JAR resources..."
log $(pwd)
cp -v ../../dist-asm/target/dist-asm-*-bin/*.jar ./
log "Extracting JARs..."
log $(pwd)
unzip *.jar
log "Cleaning old JARs..."
log $(pwd)
rm -fv *.jar
log "Copying native resources..."
log $(pwd)
cp -v ../../dist-asm/target/dist-asm-*-bin/*.so ./
log "Packaging JAR..."
log $(pwd)
jar cvfe jvmagic.jar date.willnot.amy.jvmagic.JVMagic *
log "Moving finished JAR out..."
log $(pwd)
mv -v jvmagic.jar ../
cd ..
rm -rfv tmp
mv -v jvmagic.jar ../
log "Done!"
log $(pwd)
