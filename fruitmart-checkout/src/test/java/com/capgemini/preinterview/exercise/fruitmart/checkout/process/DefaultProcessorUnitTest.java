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
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultProcessorUnitTest {

	@InjectMocks
	private DefaultProcessor testTarget;

	@Mock
	private PropertiesProvider propertiesProvider;

	@Test
	public void compute_NoItemSelected() {
		final Map<String, Integer> countPerItem = new HashMap<>();

		PurchaseSummary purchaseSummary = testTarget.compute(countPerItem);

		Assert.assertEquals(0, purchaseSummary.getOriginalItemsSelected().size());
		Assert.assertEquals(BigDecimal.ZERO, purchaseSummary.getGrossPrice());
	}

	@Test
	public void compute_SomeItemsSelected() {
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

		PurchaseSummary purchaseSummary = testTarget.compute(countPerItem);

		Assert.assertEquals(2, purchaseSummary.getOriginalItemsSelected().size());
		Assert.assertEquals(BigDecimal.valueOf(2.85), purchaseSummary.getGrossPrice());
	}
}
