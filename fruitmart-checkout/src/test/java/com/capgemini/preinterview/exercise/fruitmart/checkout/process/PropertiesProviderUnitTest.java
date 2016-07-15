package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesProviderUnitTest {

	@InjectMocks
	private PropertiesProvider testTarget;

	@Mock
	private Properties properties;

	@Test
	public void getItemsAvailable_NoneAvl() {
		final String itemsAvailableKey = "items.available";
		Mockito.when(properties.getProperty(itemsAvailableKey)).thenReturn(null);

		Set<String> itemsAvailable = testTarget.getItemsAvailable();

		Assert.assertEquals(0, itemsAvailable.size());
	}

	@Test
	public void getItemsAvailable_ThreeAvl() {
		final String itemsAvailableKey = "items.available";
		final String itemsAvailableCommaSeparated = "item-1, item-2, item-3";
		Mockito.when(properties.getProperty(itemsAvailableKey)).thenReturn(itemsAvailableCommaSeparated);

		Set<String> itemsAvailable = testTarget.getItemsAvailable();

		Assert.assertEquals(3, itemsAvailable.size());
	}

	@Test
	public void getPencePerUnit_NotListed() {
		final String itemName = "item-1";
		final String pencePerUnitAsStr = null;
		final String pencePerUnitPropKey = PropertiesProvider.PROP_PENCE_PERUNIT_PREFIX.concat(itemName);
		Mockito.when(properties.getProperty(pencePerUnitPropKey)).thenReturn(pencePerUnitAsStr);

		BigDecimal pencePerUnit = testTarget.getPencePerUnit(itemName);

		Assert.assertEquals(PropertiesProvider.DEFAULT_PENCE_PERUNIT, pencePerUnit);
	}

	@Test
	public void getPencePerUnit_NotNumber() {
		final String itemName = "item-1";
		final String pencePerUnitAsStr = "not_a_number";
		final String pencePerUnitPropKey = PropertiesProvider.PROP_PENCE_PERUNIT_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(pencePerUnitPropKey)).thenReturn(pencePerUnitAsStr);

		BigDecimal pencePerUnit = testTarget.getPencePerUnit(itemName);

		Assert.assertEquals(PropertiesProvider.DEFAULT_PENCE_PERUNIT, pencePerUnit);
	}

	@Test
	public void getPencePerUnit() {
		final String itemName = "item-1";
		final String pencePerUnitAsStr = "26";
		final String pencePerUnitPropKey = PropertiesProvider.PROP_PENCE_PERUNIT_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(pencePerUnitPropKey)).thenReturn(pencePerUnitAsStr);

		BigDecimal pencePerUnit = testTarget.getPencePerUnit(itemName);

		Assert.assertEquals(new BigDecimal(pencePerUnitAsStr), pencePerUnit);
	}

	@Test
	public void getBonusOffer_None() {
		final String itemName = "item-1";
		final String bonusOfferAsStr = null;
		final String bonusOfferPropKey = PropertiesProvider.PROP_BONUS_OFFER_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(bonusOfferPropKey)).thenReturn(bonusOfferAsStr);

		BonusOffer bonusOffer = testTarget.getBonusOffer(itemName);

		Assert.assertNull(bonusOffer);
	}

	@Test
	public void getBonusOffer_NotNumbers() {
		final String itemName = "item-1";
		final String bonusOfferAsStr = "A,B";
		final String bonusOfferPropKey = PropertiesProvider.PROP_BONUS_OFFER_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(bonusOfferPropKey)).thenReturn(bonusOfferAsStr);

		BonusOffer bonusOffer = testTarget.getBonusOffer(itemName);

		Assert.assertNull(bonusOffer);
	}

	@Test
	public void getBonusOffer_TooManyParams() {
		final String itemName = "item-1";
		final String bonusOfferAsStr = "2,1,3";
		final String bonusOfferPropKey = PropertiesProvider.PROP_BONUS_OFFER_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(bonusOfferPropKey)).thenReturn(bonusOfferAsStr);

		BonusOffer bonusOffer = testTarget.getBonusOffer(itemName);

		Assert.assertNull(bonusOffer);
	}

	@Test
	public void getBonusOffer_BuyTwoGetOneExtra() {
		final String itemName = "item-1";
		final String bonusOfferAsStr = "2,1";
		final String bonusOfferPropKey = PropertiesProvider.PROP_BONUS_OFFER_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(bonusOfferPropKey)).thenReturn(bonusOfferAsStr);

		BonusOffer bonusOffer = testTarget.getBonusOffer(itemName);

		Assert.assertEquals(2, bonusOffer.getSelectedUnits().intValue());
		Assert.assertEquals(1, bonusOffer.getBonusUnits().intValue());
	}

	@Test
	public void getDiscountOffer_None() {
		final String itemName = "item-1";
		final String discountOfferAsStr = null;
		final String discountOfferPropKey = PropertiesProvider.PROP_DISCOUNT_OFFER_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(discountOfferPropKey)).thenReturn(discountOfferAsStr);

		DiscountOffer discountOffer = testTarget.getDiscountOffer(itemName);

		Assert.assertNull(discountOffer);
	}

	@Test
	public void getDiscountOffer_NotNumbers() {
		final String itemName = "item-1";
		final String discountOfferAsStr = "A,B";
		final String discountOfferPropKey = PropertiesProvider.PROP_DISCOUNT_OFFER_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(discountOfferPropKey)).thenReturn(discountOfferAsStr);

		DiscountOffer discountOffer = testTarget.getDiscountOffer(itemName);

		Assert.assertNull(discountOffer);
	}

	@Test
	public void getDiscountOffer_TooManyParams() {
		final String itemName = "item-1";
		final String discountOfferAsStr = "2,1,3";
		final String discountOfferPropKey = PropertiesProvider.PROP_DISCOUNT_OFFER_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(discountOfferPropKey)).thenReturn(discountOfferAsStr);

		DiscountOffer discountOffer = testTarget.getDiscountOffer(itemName);

		Assert.assertNull(discountOffer);
	}

	@Test
	public void getDiscountOffer_BuyThreeInPriceOfTwo() {
		final String itemName = "item-1";
		final String discountOfferAsStr = "3,2";
		final String discountOfferPropKey = PropertiesProvider.PROP_DISCOUNT_OFFER_PREFIX.concat(itemName);

		Mockito.when(properties.getProperty(discountOfferPropKey)).thenReturn(discountOfferAsStr);

		DiscountOffer discountOffer = testTarget.getDiscountOffer(itemName);

		Assert.assertEquals(3, discountOffer.getSelectedUnits().intValue());
		Assert.assertEquals(2, discountOffer.getPriceOfUnits().intValue());
	}
}
