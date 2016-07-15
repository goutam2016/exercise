package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Simple configuration data (items available, price per 1 unit of an item, promotional offers etc.) are loaded into
 * this class from a properties file. This data is later used by purchase processing components like Processor and
 * PromotionalOfferHandler etc.
 * 
 * @author goutam.bhattacharjee
 *
 */
@Component
public class PropertiesProvider {

	private static final Logger LOGGER = Logger.getLogger(PropertiesProvider.class);
	static final String PROP_PENCE_PERUNIT_PREFIX = "pence.perunit.";
	static final String PROP_BONUS_OFFER_PREFIX = "promo.offer.bonus.";
	static final String PROP_DISCOUNT_OFFER_PREFIX = "promo.offer.discount.";
	static final BigDecimal DEFAULT_PENCE_PERUNIT = BigDecimal.valueOf(10);

	@Resource(name = "fruitmartProperties")
	private Properties properties;

	public Set<String> getItemsAvailable() {
		List<String> itemsAvlList = new ArrayList<>();
		String itemsAvailable = properties.getProperty("items.available");
		if (itemsAvailable != null) {
			String[] itemsAvlArr = itemsAvailable.split(",");
			itemsAvlList = Arrays.asList(itemsAvlArr);
		}

		return new HashSet<>(itemsAvlList);
	}

	BigDecimal getPencePerUnit(String item) {
		String pencePerUnitPropKey = new StringBuilder(PROP_PENCE_PERUNIT_PREFIX).append(item).toString();
		String pencePerUnitAsStr = properties.getProperty(pencePerUnitPropKey);

		try {
			return new BigDecimal(pencePerUnitAsStr);
		} catch (NullPointerException | NumberFormatException nfe) {
			LOGGER.error("Could not convert pence-per-unit to BigDecimal", nfe);
		}

		return DEFAULT_PENCE_PERUNIT;
	}

	BonusOffer getBonusOffer(String item) {
		String[] bonusOfferArr = getPromotionalOffer(item, PROP_BONUS_OFFER_PREFIX);
		BonusOffer bonusOffer = null;

		if (bonusOfferArr.length == 2) {
			try {
				Integer selectedUnits = Integer.parseInt(bonusOfferArr[0]);
				Integer bonusUnits = Integer.parseInt(bonusOfferArr[1]);
				bonusOffer = new BonusOffer(selectedUnits, bonusUnits);
			} catch (NumberFormatException nfe) {
				LOGGER.error(String.format("Promotional bonus offer for %s not setup properly, offer will be ignored.",
						item), nfe);
			}
		} else if (bonusOfferArr.length != 0) {
			LOGGER.error(
					String.format("Promotional bonus offer for %s not setup properly, offer will be ignored.", item));
		}

		return bonusOffer;
	}

	DiscountOffer getDiscountOffer(String item) {
		String[] discountOfferArr = getPromotionalOffer(item, PROP_DISCOUNT_OFFER_PREFIX);
		DiscountOffer discountOffer = null;

		if (discountOfferArr.length == 2) {
			try {
				Integer selectedUnits = Integer.parseInt(discountOfferArr[0]);
				Integer priceOfUnits = Integer.parseInt(discountOfferArr[1]);
				discountOffer = new DiscountOffer(selectedUnits, priceOfUnits);
			} catch (NumberFormatException nfe) {
				LOGGER.error(String.format(
						"Promotional discount offer for %s not setup properly, offer will be ignored.", item), nfe);
			}
		} else if (discountOfferArr.length != 0) {
			LOGGER.error(String.format("Promotional discount offer for %s not setup properly, offer will be ignored.",
					item));
		}

		return discountOffer;
	}

	private String[] getPromotionalOffer(String item, String offerPrefix) {
		String offerPropKey = new StringBuilder(offerPrefix).append(item).toString();
		String offerAsStr = properties.getProperty(offerPropKey);

		String[] promoOfferArr = {};

		if (offerAsStr != null) {
			if (offerAsStr.split(",").length == 2) {
				promoOfferArr = offerAsStr.split(",");
			}
		}

		return promoOfferArr;
	}
}
