#! /bin/bash




dateyyyy=`date '+%Y%m%d'`
echo $dateyyyy

###########################################################################

cd `dirname $0`

program_path=$PWD

java -jar download_warncap.jar "$program_path"/Warn_config.properties "$program_path"/log4j2.properties


