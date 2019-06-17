#/bin/bash
echo "for line in cat /root/2.txt"
for line in $(cat /root/2.txt)
do
   echo $line;
done
