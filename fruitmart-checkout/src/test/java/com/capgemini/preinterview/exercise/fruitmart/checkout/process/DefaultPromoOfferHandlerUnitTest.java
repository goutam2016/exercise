package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultPromoOfferHandlerUnitTest {

	@InjectMocks
	private DefaultPromoOfferHandler testTarget;

	@Mock
	private PropertiesProvider propertiesProvider;

	@Test
	public void applyOffers_NoOffer() {
		final String item1 = "item-1";
		final Integer item1Count = 5;
		final String item2 = "item-2";
		final Integer item2Count = 3;
		final BigDecimal grossPrice = BigDecimal.valueOf(3.75);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addOriginalItemsSelected(item1, item1Count);
		shoppingCart.addOriginalItemsSelected(item2, item2Count);
		shoppingCart.setGrossPrice(grossPrice);
		shoppingCart.setNetPrice(grossPrice);

		Mockito.when(propertiesProvider.getBonusOffer(Mockito.anyString())).thenReturn(null);
		Mockito.when(propertiesProvider.getDiscountOffer(Mockito.anyString())).thenReturn(null);

		testTarget.applyOffers(shoppingCart);

		Assert.assertEquals(BigDecimal.ZERO, shoppingCart.getDiscount());
		Assert.assertEquals(grossPrice, shoppingCart.getNetPrice());
	}

	@Test
	public void applyOffers_WithBonusAndDiscountOffers() {
		final String item1 = "item-1";
		final Integer item1Count = 10;
		final BigDecimal item1PencePerUnit = BigDecimal.valueOf(30);
		final String item2 = "item-2";
		final Integer item2Count = 8;
		final BigDecimal item2PencePerUnit = BigDecimal.valueOf(45);
		final BigDecimal grossPrice = BigDecimal.valueOf(3.75);
		final BonusOffer bonusOffer = new BonusOffer(3, 1);
		final DiscountOffer discountOffer = new DiscountOffer(3, 2);

		ShoppingCart shoppingCart = new ShoppingCart();
		shoppingCart.addOriginalItemsSelected(item1, item1Count);
		shoppingCart.addFinalItems(item1, item1Count);
		shoppingCart.addOriginalItemsSelected(item2, item2Count);
		shoppingCart.addFinalItems(item2, item2Count);
		shoppingCart.setGrossPrice(grossPrice);
		shoppingCart.setNetPrice(grossPrice);

		Mockito.when(propertiesProvider.getBonusOffer(item1)).thenReturn(bonusOffer);
		Mockito.when(propertiesProvider.getDiscountOffer(item2)).thenReturn(discountOffer);
		Mockito.when(propertiesProvider.getPencePerUnit(item1)).thenReturn(item1PencePerUnit);
		Mockito.when(propertiesProvider.getPencePerUnit(item2)).thenReturn(item2PencePerUnit);

		testTarget.applyOffers(shoppingCart);

		Assert.assertEquals(3, shoppingCart.getAdditionalItemsOffered().get(item1).intValue());
		Assert.assertNull(shoppingCart.getAdditionalItemsOffered().get(item2));
		Assert.assertEquals(13, shoppingCart.getFinalItems().get(item1).intValue());
		Assert.assertEquals(8, shoppingCart.getFinalItems().get(item2).intValue());
		Assert.assertEquals(BigDecimal.valueOf(0.90), shoppingCart.getDiscount());
	}
}
