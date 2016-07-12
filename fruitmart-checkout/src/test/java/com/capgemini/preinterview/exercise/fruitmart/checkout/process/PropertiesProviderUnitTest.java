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
	
}
