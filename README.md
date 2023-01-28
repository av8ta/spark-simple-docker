# Average Sales

## This project demonstrates a simple way to get started with [Apache Spark](https://spark.apache.org/) using [docker containers](https://docs.docker.com/get-started/)

It's not production-ready, it's for getting started quickly with Spark. Please see official documentation for securely [deploying a Spark Cluster](https://spark.apache.org/docs/3.3.1/cluster-overview.html).

The cluster is run with `docker compose`. See [docker-compose.yml](./docker-compose.yml) to understand how it works. If you have an older version of docker compose installed you may need to change the command to `docker-compose up/down`. Since 2021 `compose` has been a subcommand of `docker`. This is how we use it below.

## Build the Scala project [AverageSales.scala](./src/main/scala/org/opensky/spark/exercise/AverageSales.scala) with [maven](https://maven.apache.org/)

This Spark application written in Scala takes as input the file sales_data_sample.csv. It finds the average sales amount for each year by product code. Rounds the average to 2 decimal places while only considering records with status equal to 'Shipped'. The output is written to a CSV file named output.csv with headers YEAR_ID, PRODUCTLINE, AVERAGE_SALES_AMT and be ordered by YEAR_ID and PRODUCTLINE.

```shell
# Build Scala Spark application.
mvn install
mvn package
```

Ensure the correct privileges on data directory.

```shell
chmod -R 777 data
```

## Run the AverageSales Spark Job

Start docker daemon first.

```shell
# Start Spark cluster. Master plus one Worker.
docker compose up -d
```

To see the running containers in docker.

```shell
docker ps
```

Or go to <http://localhost:8080> in your browser to see the web ui

Submit the job to the Spark Worker.

```shell
./run.sh
```

See [output.csv](./output.csv) for the result.

```shell
# Stop the Spark Cluster.
docker compose down
```

If you want to run a second time, the output in `data/` needs to be deleted first. Since it's owned by the Spark container user you need to run it with sudo.

```shell
# Erase all output for a fresh run.
./erase.sh
```
