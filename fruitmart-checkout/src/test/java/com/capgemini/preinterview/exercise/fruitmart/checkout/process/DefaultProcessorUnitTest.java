package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class DefaultProcessorUnitTest {

	@InjectMocks
	private DefaultProcessor testTarget;

	@Mock
	private PropertiesProvider propertiesProvider;

	@Mock
	private PromotionalOfferHandler promotionalOfferHandler;

	@Test
	public void compute_NoItemSelected() {
		final Map<String, Integer> countPerItem = new HashMap<>();

		ShoppingCart purchaseSummary = testTarget.compute(countPerItem);

		Assert.assertEquals(0, purchaseSummary.getOriginalItemsSelected().size());
		Assert.assertEquals(BigDecimal.ZERO, purchaseSummary.getGrossPrice());
	}

	@Test
	public void compute_SomeItemsSelected_NoPromoOffer() {
		final String item1 = "item-1";
		final Integer item1Count = 5;
		final BigDecimal item1PencePerUnit = BigDecimal.valueOf(30);
		final String item2 = "item-2";
		final Integer item2Count = 3;
		final BigDecimal item2PencePerUnit = BigDecimal.valueOf(45);
		final Map<String, Integer> countPerItem = new HashMap<>();
		countPerItem.put(item1, item1Count);
		countPerItem.put(item2, item2Count);

		Mockito.when(propertiesProvider.getPencePerUnit(item1)).thenReturn(item1PencePerUnit);
		Mockito.when(propertiesProvider.getPencePerUnit(item2)).thenReturn(item2PencePerUnit);
		Mockito.doNothing().when(promotionalOfferHandler).applyOffers(Mockito.any(ShoppingCart.class));

		ShoppingCart shoppingCart = testTarget.compute(countPerItem);

		Assert.assertEquals(2, shoppingCart.getOriginalItemsSelected().size());
		Assert.assertEquals(2, shoppingCart.getFinalItems().size());
		Assert.assertEquals(BigDecimal.valueOf(2.85), shoppingCart.getGrossPrice());
	}

	@Test
	public void compute_SomeItemsSelected_WithPromoOffers() {
		final String item1 = "item-1";
		final Integer item1Count = 5;
		final BigDecimal item1PencePerUnit = BigDecimal.valueOf(30);
		final Integer item1BonusUnits = 2;
		final String item2 = "item-2";
		final Integer item2Count = 4;
		final BigDecimal item2PencePerUnit = BigDecimal.valueOf(45);
		final BigDecimal item2TotalDiscountInPound = BigDecimal.valueOf(0.45);
		final Map<String, Integer> countPerItem = new HashMap<>();
		countPerItem.put(item1, item1Count);
		countPerItem.put(item2, item2Count);

		Answer<Void> promoOffersApplied = (InvocationOnMock invocation) -> {
			ShoppingCart shoppingCart = invocation.getArgumentAt(0, ShoppingCart.class);
			shoppingCart.addAdditionalItemsOffered(item1, item1BonusUnits);
			shoppingCart.addFinalItems(item1, item1BonusUnits);
			shoppingCart.setDiscount(item2TotalDiscountInPound);
			BigDecimal netPrice = shoppingCart.getGrossPrice().subtract(item2TotalDiscountInPound);
			shoppingCart.setNetPrice(netPrice);
			return null;
		};

		Mockito.when(propertiesProvider.getPencePerUnit(item1)).thenReturn(item1PencePerUnit);
		Mockito.when(propertiesProvider.getPencePerUnit(item2)).thenReturn(item2PencePerUnit);
		Mockito.doAnswer(promoOffersApplied).when(promotionalOfferHandler).applyOffers(Mockito.any(ShoppingCart.class));

		ShoppingCart shoppingCart = testTarget.compute(countPerItem);

		Assert.assertEquals(2, shoppingCart.getOriginalItemsSelected().size());
		Assert.assertEquals(5, shoppingCart.getOriginalItemsSelected().get(item1).intValue());
		Assert.assertEquals(4, shoppingCart.getOriginalItemsSelected().get(item2).intValue());
		Assert.assertEquals(BigDecimal.valueOf(3.30), shoppingCart.getGrossPrice());
		Assert.assertEquals(2, shoppingCart.getAdditionalItemsOffered().get(item1).intValue());
		Assert.assertEquals(7, shoppingCart.getFinalItems().get(item1).intValue());
		Assert.assertEquals(item2TotalDiscountInPound, shoppingCart.getDiscount());
		Assert.assertEquals(shoppingCart.getGrossPrice().subtract(item2TotalDiscountInPound),
				shoppingCart.getNetPrice());
	}
}
