package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Default implementation of <code>Processor</code> interface.
 * 
 * @author goutam.bhattacharjee
 *
 */
@Component
public class DefaultProcessor implements Processor {

	private static final Logger LOGGER = Logger.getLogger(DefaultProcessor.class);

	@Resource
	private PropertiesProvider propertiesProvider;
	
	@Resource
	private PromotionalOfferHandler promotionalOfferHandler;

	@Override
	public ShoppingCart compute(Map<String, Integer> countPerItem) {
		ShoppingCart shoppingCart = new ShoppingCart();
		BigDecimal grossPriceInPence = BigDecimal.ZERO;
		
		Set<Entry<String, Integer>> countPerItemEntries = countPerItem.entrySet();
		
		for (Entry<String, Integer> countPerItemEntry : countPerItemEntries) {
			String itemName = countPerItemEntry.getKey();
			Integer count = countPerItemEntry.getValue();
			BigDecimal pencePerUnit = propertiesProvider.getPencePerUnit(itemName);
			BigDecimal pencePerItem = pencePerUnit.multiply(BigDecimal.valueOf(count.longValue()));
			grossPriceInPence = grossPriceInPence.add(pencePerItem);
			shoppingCart.addOriginalItemsSelected(itemName, count);
			shoppingCart.addFinalItems(itemName, count);
		}
		
		BigDecimal grossPriceInPound = grossPriceInPence.divide(BigDecimal.valueOf(100));
		shoppingCart.setGrossPrice(grossPriceInPound);
		shoppingCart.setNetPrice(grossPriceInPound);
		
		LOGGER.info(String.format("Calculated gross price: GBP %s", grossPriceInPound));
		LOGGER.info("Applying promotional offers...");
		
		promotionalOfferHandler.applyOffers(shoppingCart);
		
		return shoppingCart;
	}

}
