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

import org.springframework.stereotype.Component;

/**
 * Helper class for parsing item names passed from the command line. Converts arguments into a Map, with item name as
 * key and no. of occurrences as value.
 * 
 * @author goutam.bhattacharjee
 *
 */
@Component
public class ArgumentParser {

	Map<String, Integer> parseArguments(String[] args, Set<String> itemsAvailable) {
		String concatenatedAvlItems = itemsAvailable.stream().collect(Collectors.joining(", "));

		if (args == null || args.length == 0) {
			StringBuilder exceptionMsgBuilder = new StringBuilder("No fruit mentioned.\n")
					.append("Pass names of fruits in the following manner: <fruit-1> <fruit-2> <fruit-3> and so on.\n")
					.append("Currently following fruits are available: ").append(concatenatedAvlItems);
			throw new IllegalArgumentException(exceptionMsgBuilder.toString());
		}

		Stream<String> itemsNames = Arrays.stream(args);
		Collector<String, ?, Integer> itemCountCollector = Collectors.collectingAndThen(Collectors.counting(),
				Long::intValue);
		Map<String, Integer> countPerItem = itemsNames
				.collect(Collectors.groupingBy(String::toLowerCase, itemCountCollector));

		List<String> unavlItems = new ArrayList<>();

		BiConsumer<String, Integer> unavlItemSelector = (String itemName, Integer count) -> {
			if (!itemsAvailable.contains(itemName)) {
				unavlItems.add(itemName);
			}
		};

		countPerItem.forEach(unavlItemSelector);

		if (!unavlItems.isEmpty()) {
			String concatenatedUnavlItems = unavlItems.stream().collect(Collectors.joining(", "));
			StringBuilder exceptionMsgBuilder = new StringBuilder("Currently following fruits are available: ")
					.append(concatenatedAvlItems).append("\n")
					.append("Please remove the following fruit names and try again. ").append(concatenatedUnavlItems);
			throw new IllegalArgumentException(exceptionMsgBuilder.toString());
		}

		return countPerItem;
	}

}
