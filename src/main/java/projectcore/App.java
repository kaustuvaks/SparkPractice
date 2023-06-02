package projectcore;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class App {

	public static void main(final String args[]) {
		try(final var  spark = SparkSession.builder().appName("SparkProgramOne").master("local[*]").getOrCreate();
			final var sc = new JavaSparkContext(spark.sparkContext())){
				
				Optional<Integer> data =  Stream.iterate(1, n -> n+1).limit(5).collect(Collectors.toList()).stream().reduce((a, b) -> a+b);
				
				
				data.ifPresent(System.out::println);
		}
	}

}
