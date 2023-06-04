package projectcore;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import scala.collection.immutable.Seq;
import scala.collection.JavaConverters;

public class SparkSQL {

	public static void main(final String args[]) throws IOException, InterruptedException, ConnectException {
		try(final var  spark = SparkSession.builder().appName("SparkProgramOne").master("local[*]").getOrCreate();
			final var sc = new JavaSparkContext(spark.sparkContext())){
				
			HttpClient httpClient = HttpClient.newHttpClient();
			HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create("https://restcountries.com/v3.1/all")).version(HttpClient.Version.HTTP_2).GET().build();
			HttpResponse<String> response = httpClient.send(httpRequest, BodyHandlers.ofString());
			String responseBody = response.body();
			
			//responseBody = responseBody.substring(2, responseBody.length()-1);
			
			PrintWriter pout = new PrintWriter("c:/Users/kaust/Desktop/apachedta.json");
			pout.println(responseBody);
			pout.close();
			
			Dataset<Row> dataset = spark.read().option("header", "false").option("inferSchema", "true").json("c:/Users/kaust/Desktop/apachedta.json");
			//Dataset<Row> lis = spark.read().json(responseBody);
			dataset.show();
		}
		
	}

}
