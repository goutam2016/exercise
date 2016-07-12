package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class DefaultProcessor implements Processor {

	private static final Logger LOGGER = Logger.getLogger(DefaultProcessor.class);

	@Resource
	private PropertiesProvider propertiesProvider;

	@Override
	public PurchaseSummary compute(Map<String, Integer> countPerItem) {
		PurchaseSummary purchaseSummary = new PurchaseSummary();
		BigDecimal grossPriceInPence = BigDecimal.ZERO;
		
		Set<Entry<String, Integer>> countPerItemEntries = countPerItem.entrySet();
		
		for (Entry<String, Integer> countPerItemEntry : countPerItemEntries) {
			String itemName = countPerItemEntry.getKey();
			Integer count = countPerItemEntry.getValue();
			BigDecimal pencePerUnit = propertiesProvider.getPencePerUnit(itemName);
			BigDecimal pencePerItem = pencePerUnit.multiply(BigDecimal.valueOf(count.longValue()));
			grossPriceInPence = grossPriceInPence.add(pencePerItem);
			purchaseSummary.addOriginalItemsSelected(itemName, count);
		}
		
		BigDecimal grossPriceInPound = grossPriceInPence.divide(BigDecimal.valueOf(100));
		purchaseSummary.setGrossPrice(grossPriceInPound);
		purchaseSummary.setNetPrice(grossPriceInPound);
		
		return purchaseSummary;
	}

}
