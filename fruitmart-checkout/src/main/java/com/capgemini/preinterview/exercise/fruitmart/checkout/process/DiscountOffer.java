package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

public class DiscountOffer {

	private final Integer selectedUnits;
	private final Integer priceOfUnits;

	DiscountOffer(Integer selectedUnits, Integer priceOfUnits) {
		super();
		this.selectedUnits = selectedUnits;
		this.priceOfUnits = priceOfUnits;
	}

	public Integer getSelectedUnits() {
		return selectedUnits;
	}
	
	public Integer getPriceOfUnits() {
		return priceOfUnits;
	}
}
