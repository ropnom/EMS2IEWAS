#!/bin/bash

clear
echo "EMS script RUNING...."

#Define INPUT variables
year=2015
initday=12
enday=25
d2="$(printf "%03d" $(($initday-1)))"

#Create the subfolder
if [ ! -d /home/mlzboy/b2c2/shared/db ] 
then
    mkdir historial	
fi
cd historial

#Create url of previous day
url="ftp://ems.estec.esa.int/pub/PRN120/y"$year"/d"$d2"/h23.ems"

#Loop along the days
for d in `seq  -w $initday $enday`
do

	echo "day: "$d
#	Dowload previous day
	wget $url -O a0.ems
	cat a0.ems | gawk '{if (($8==18)|| ($8==26)) print $0 }' > a0_filtrado.ems

#	Dowload 0-23 hours
	for h in `seq -w 0 23`
	do
		url="ftp://ems.estec.esa.int/pub/PRN120/y"$year"/d"$(printf "%03d" $d)"/h"$h".ems"
		echo "-------------------"
		echo " Downloading from : "$url
		#echo "-------------------"
		#echo " "$url
		wget $url
#		Filtrer
		cat "h"$h".ems" | gawk '{if (($8==18)|| ($8==26)) print $0 }' > "h"$h"_filtrado.ems"

		#echo "...\n"
	done

#	Concatenate filter files
	file=$year"_"$d".ioms"
	ls -1 *_filtrado.ems | sort | while read fn ; do cat "$fn" >> $file; done
#	Remove temporal files
	rm *.ems
	echo "...**\n"
#	Make a sleep to prevent server segurity
	sleep 1
	
done

#Delete days without data
find . -size 0 -delete

echo "-------------------"
echo "******* FIN *******"
echo "-------------------"

 
