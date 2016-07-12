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

@Component
public class PropertiesProvider {

	private static final Logger LOGGER = Logger.getLogger(PropertiesProvider.class);
	static final String PROP_PENCE_PERUNIT_PREFIX = "pence.perunit.";
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

	public BigDecimal getPencePerUnit(String item) {
		String pencePerUnitPropKey = new StringBuilder(PROP_PENCE_PERUNIT_PREFIX).append(item).toString();
		String pencePerUnitAsStr = properties.getProperty(pencePerUnitPropKey);

		try {
			return new BigDecimal(pencePerUnitAsStr);
		} catch (NullPointerException | NumberFormatException nfe) {
			LOGGER.error("Could not convert pence-per-unit to BigDecimal", nfe);
		}

		return DEFAULT_PENCE_PERUNIT;
	}
}
