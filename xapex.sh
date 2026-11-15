#!/bin/bash

clear
#echo "CHECKING THE ATLAS RESOURCES"

#USER_FILES_EXISTS=0

#if [ ! -d ~/.config/atlas ]; then
#	USER_FILES_EXISTS=1
#	mkdir ~/.config/atlas
#fi
#if [ ! -d ~/.local/share/atlas ]; then
#	USER_FILES_EXISTS=1
#	mkdir ~/.local/share/atlas
#fi
#if [ ! -d ~/Desktop/atlas ]; then
#	USER_FILES_EXISTS=1
#	mkdir ~/Desktop/atlas
#fi

#if [ $USER_FILES_EXISTS -gt 0 ]; then
#	echo "GETTING THE ATLAS RESOURCES"
#	for a in ls /opt/atlas/unixInstallDispatch/*; do
#		if [ $a = "/opt/atlas/unixInstallDispatch/userConfigs" ]; then
#			echo "user configs found"
#		fi
#		if [ $a = "/opt/atlas/unixInstallDispatch/userAssets" ]; then
#			echo "user assets found"
#		fi
#		if [ $a = "/opt/atlas/unixInstallDispatch/userFiles" ]; then
#			echo "user files found"
#		fi
#	done
#fi

#echo "RESOURCES CHECK - POSITIVE"
#echo "ENGAGING ATLAS..."

CLASSPATH="/opt/"

SKIPFIRST=1

for a in ls /lib/xapex/libs/*.jar; do
	if [ $SKIPFIRST -eq 0 ]; then
		CLASSPATH="$CLASSPATH:$a"
	else
		((SKIPFIRST--))
	fi
done

echo "LOGS: libs paths collected"

cd ~/xapex
pwd
ls -l
java -cp "$CLASSPATH" xapex.Xapex "$@"

echo "XAPEX TERMINATED"

exit 0
