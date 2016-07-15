package com.capgemini.preinterview.exercise.fruitmart.checkout;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArgumentParserUnitTest {

	private ArgumentParser argumentParser;

	@Before
	public void setup() {
		argumentParser = new ArgumentParser();
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseArguments_NoArg() {
		final String[] args = {};
		final String itemsAvlAsStr = "item-1,item-2";
		String[] itemsAvlArr = itemsAvlAsStr.split(",");
		Set<String> itemsAvailable = new HashSet<>(Arrays.asList(itemsAvlArr));

		argumentParser.parseArguments(args, itemsAvailable);

		Assert.fail("IllegalArgumentException was expected.");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseArguments_ItemUnavailable() {
		final String[] args = { "item-3" };
		final String itemsAvlAsStr = "item-1,item-2";
		String[] itemsAvlArr = itemsAvlAsStr.split(",");
		Set<String> itemsAvailable = new HashSet<>(Arrays.asList(itemsAvlArr));

		argumentParser.parseArguments(args, itemsAvailable);

		Assert.fail("IllegalArgumentException was expected.");
	}

	@Test
	public void parseArguments() {
		final String[] args = { "item-1", "item-2", "item-1", "item-2", "item-1" };
		final String itemsAvlAsStr = "item-1,item-2";
		String[] itemsAvlArr = itemsAvlAsStr.split(",");
		Set<String> itemsAvailable = new HashSet<>(Arrays.asList(itemsAvlArr));

		Map<String, Integer> countPerItem = argumentParser.parseArguments(args, itemsAvailable);

		Assert.assertEquals(3, countPerItem.get("item-1").intValue());
		Assert.assertEquals(2, countPerItem.get("item-2").intValue());
	}
}
