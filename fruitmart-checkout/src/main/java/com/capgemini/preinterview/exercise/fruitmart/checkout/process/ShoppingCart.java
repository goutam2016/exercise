package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ShoppingCart {
	private Map<String, Integer> originalItemsSelected;
	private Map<String, Integer> additionalItemsOffered;
	private Map<String, Integer> finalItems;
	private BigDecimal grossPrice;
	private BigDecimal discount;
	private BigDecimal netPrice;

	ShoppingCart() {
		originalItemsSelected = new HashMap<>();
		additionalItemsOffered = new HashMap<>();
		finalItems = new HashMap<>();
		grossPrice = BigDecimal.ZERO;
		discount = BigDecimal.ZERO;
		netPrice = BigDecimal.ZERO;
	}

	public Map<String, Integer> getOriginalItemsSelected() {
		return originalItemsSelected;
	}

	public void setOriginalItemsSelected(Map<String, Integer> originalItemsSelected) {
		this.originalItemsSelected = originalItemsSelected;
	}

	public void addOriginalItemsSelected(String itemName, Integer count) {
		if (originalItemsSelected == null) {
			originalItemsSelected = new HashMap<>();
		}
		BiFunction<Integer, Integer, Integer> itemCountUpdater = (Integer existingCount,
				Integer newCount) -> existingCount.intValue() + newCount.intValue();
		originalItemsSelected.merge(itemName, count, itemCountUpdater);
	}

	public Map<String, Integer> getAdditionalItemsOffered() {
		return additionalItemsOffered;
	}

	public void setAdditionalItemsOffered(Map<String, Integer> additionalItemsOffered) {
		this.additionalItemsOffered = additionalItemsOffered;
	}

	public void addAdditionalItemsOffered(String itemName, Integer count) {
		if (additionalItemsOffered == null) {
			additionalItemsOffered = new HashMap<>();
		}
		BiFunction<Integer, Integer, Integer> itemCountUpdater = (Integer existingCount,
				Integer newCount) -> existingCount.intValue() + newCount.intValue();
		additionalItemsOffered.merge(itemName, count, itemCountUpdater);
	}
	
	public Map<String, Integer> getFinalItems() {
		return finalItems;
	}
	
	public void setFinalItems(Map<String, Integer> finalItems) {
		this.finalItems = finalItems;
	}

	public void addFinalItems(String itemName, Integer count) {
		if (finalItems == null) {
			finalItems = new HashMap<>();
		}
		BiFunction<Integer, Integer, Integer> itemCountUpdater = (Integer existingCount,
				Integer newCount) -> existingCount.intValue() + newCount.intValue();
		finalItems.merge(itemName, count, itemCountUpdater);
	}

	public BigDecimal getGrossPrice() {
		return grossPrice;
	}

	public void setGrossPrice(BigDecimal grossPrice) {
		this.grossPrice = grossPrice;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}
}
