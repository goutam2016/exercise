package com.capgemini.preinterview.exercise.fruitmart.checkout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.capgemini.preinterview.exercise.fruitmart.checkout.process.Processor;
import com.capgemini.preinterview.exercise.fruitmart.checkout.process.PropertiesProvider;

public class Launcher {

	private static final Logger LOGGER = Logger.getLogger(Launcher.class);

	public static void main(String[] args) {
		try (ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext(
				"fruitmart-checkout-context.xml")) {
			PropertiesProvider fruitmartProperties = appContext.getBean(PropertiesProvider.class);
			Set<String> fruitsAvailable = fruitmartProperties.getItemsAvailable();
			Map<String, Integer> countPerFruit = parseArguments(args, fruitsAvailable);
			Processor checkoutProcessor = appContext.getBean(Processor.class);
			checkoutProcessor.compute(countPerFruit);
		} catch (Exception ex) {
			LOGGER.error("Exception caught in main", ex);
		}
	}

	private static Map<String, Integer> parseArguments(String[] args, Set<String> fruitsAvailable) {
		String concatenatedAvlFruits = fruitsAvailable.stream().collect(Collectors.joining(", "));

		if (args == null || args.length == 0) {
			StringBuilder exceptionMsgBuilder = new StringBuilder("No fruit mentioned.\n")
					.append("Pass names of fruits in the following manner: <fruit-1> <fruit-2> <fruit-3> and so on.\n")
					.append("Currently following fruits are available: ").append(concatenatedAvlFruits);
			throw new IllegalArgumentException(exceptionMsgBuilder.toString());
		}

		Stream<String> fruitNames = Arrays.stream(args);
		Collector<String, ?, Integer> fruitCountCollector = Collectors.collectingAndThen(Collectors.counting(),
				Long::intValue);
		Map<String, Integer> countPerFruit = fruitNames
				.collect(Collectors.groupingBy(String::toLowerCase, fruitCountCollector));

		List<String> unavlFruits = new ArrayList<>();

		BiConsumer<String, Integer> unavlFruitSelector = (String fruitName, Integer count) -> {
			if (!fruitsAvailable.contains(fruitName)) {
				unavlFruits.add(fruitName);
			}
		};

		countPerFruit.forEach(unavlFruitSelector);

		if (!unavlFruits.isEmpty()) {
			String concatenatedUnavlFruits = unavlFruits.stream().collect(Collectors.joining(", "));
			StringBuilder exceptionMsgBuilder = new StringBuilder("Currently following fruits are available: ")
					.append(concatenatedAvlFruits).append("\n")
					.append("Please remove the following fruit names and try again. ").append(concatenatedUnavlFruits);
			throw new IllegalArgumentException(exceptionMsgBuilder.toString());
		}

		return countPerFruit;
	}
}
