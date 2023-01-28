import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object AverageSales {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("AverageSales")
      .getOrCreate()

    val inputPath  = "/opt/bitnami/spark/work/data/sales_data_sample.csv"
    val outputPath = "/opt/bitnami/spark/work/data/output"

    val inputDF = spark.read
      .format("csv")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(inputPath)

    // Filter the data to only include records with status equal to 'Shipped'
    val filteredDF = inputDF.filter("STATUS = 'Shipped'")

    // Compute the average sales amount for each year by product code
    val avgSalesDF = filteredDF.groupBy("YEAR_ID", "PRODUCTCODE")
      .agg(round(avg("SALES"), 2).as("AVERAGE_SALES_AMT"))
    val joinedDF = avgSalesDF.join(inputDF, Seq("YEAR_ID", "PRODUCTCODE"), "inner").select("YEAR_ID", "PRODUCTLINE", "AVERAGE_SALES_AMT")

    // Order the data by YEAR_ID and PRODUCTLINE
    val orderedDF = joinedDF.orderBy("YEAR_ID", "PRODUCTLINE")

    // Write the output to a CSV file
    orderedDF.write
      .format("csv")
      .option("header", "true")
      .save(outputPath)
  }
}
