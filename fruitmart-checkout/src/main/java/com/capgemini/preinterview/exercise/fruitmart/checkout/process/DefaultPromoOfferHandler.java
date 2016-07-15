package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Default implementation of <code>PromotionalOfferHandler</code> interface.
 * 
 * @author goutam.bhattacharjee
 *
 */
@Component
public class DefaultPromoOfferHandler implements PromotionalOfferHandler {

	private static final Logger LOGGER = Logger.getLogger(DefaultPromoOfferHandler.class);

	@Resource
	private PropertiesProvider propertiesProvider;

	@Override
	public void applyOffers(ShoppingCart shoppingCart) {
		Map<String, Integer> origCountPerItem = shoppingCart.getOriginalItemsSelected();
		BigDecimal totalDiscountInPence = BigDecimal.ZERO;

		Set<Entry<String, Integer>> origCountPerItemEntries = origCountPerItem.entrySet();

		for (Entry<String, Integer> origCountPerItemEntry : origCountPerItemEntries) {
			String itemName = origCountPerItemEntry.getKey();
			Integer count = origCountPerItemEntry.getValue();

			Integer bonusUnits = calculateBonusUnits(itemName, count);
			BigDecimal discountInPence = calculateDiscountInPence(itemName, count);

			if (bonusUnits.intValue() != 0) {
				shoppingCart.addAdditionalItemsOffered(itemName, bonusUnits);
				shoppingCart.addFinalItems(itemName, bonusUnits);
			}

			totalDiscountInPence = totalDiscountInPence.add(discountInPence);
		}

		BigDecimal totalDiscountInPound = totalDiscountInPence.divide(BigDecimal.valueOf(100));
		BigDecimal netPriceInPound = shoppingCart.getGrossPrice().subtract(totalDiscountInPound);
		LOGGER.info(String.format("Calculated discount: GBP %s, net price: GBP %s", totalDiscountInPound, netPriceInPound));

		shoppingCart.setDiscount(totalDiscountInPound);
		shoppingCart.setNetPrice(netPriceInPound);
	}

	private Integer calculateBonusUnits(String itemName, Integer count) {
		BonusOffer bonusOffer = propertiesProvider.getBonusOffer(itemName);

		if (bonusOffer == null) {
			LOGGER.info(String.format("No bonus offer for %s", itemName));
			return 0;
		}

		Integer selectedUnits = bonusOffer.getSelectedUnits();
		Integer bonusUnits = bonusOffer.getBonusUnits();
		LOGGER.info(String.format("Bonus offer for %s, buy %d, get %d free", itemName, selectedUnits, bonusUnits));

		Integer selectedUnitsMultiples = count / selectedUnits;
		return selectedUnitsMultiples * bonusUnits;
	}

	private BigDecimal calculateDiscountInPence(String itemName, Integer count) {
		DiscountOffer discountOffer = propertiesProvider.getDiscountOffer(itemName);

		if (discountOffer == null) {
			LOGGER.info(String.format("No discount offer for %s", itemName));
			return BigDecimal.ZERO;
		}

		Integer selectedUnits = discountOffer.getSelectedUnits();
		Integer priceOfUnits = discountOffer.getPriceOfUnits();
		LOGGER.info(
				String.format("Discount offer for %s, get %d, in price of %d", itemName, selectedUnits, priceOfUnits));

		BigDecimal pencePerUnit = propertiesProvider.getPencePerUnit(itemName);
		Integer selectedUnitsMultiples = count / selectedUnits;
		Integer discountedUnits = (selectedUnits - priceOfUnits) * selectedUnitsMultiples;
		return pencePerUnit.multiply(BigDecimal.valueOf(discountedUnits.longValue()));
	}
}
