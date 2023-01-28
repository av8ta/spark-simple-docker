#!/bin/bash

docker exec -it sparkworker /bin/bash /opt/bitnami/spark/bin/spark-submit --master spark://spark:7077 --class AverageSales /opt/bitnami/spark/work/target/spark-project-1.0-SNAPSHOT.jar

CSVFILE=$(ls data/output/*.csv)
printf '\n\nCopying result file %s to ./output.csv' "$CSVFILE"
cp "$CSVFILE" ./output.csv
