package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

public class BonusOffer {

	private final Integer selectedUnits;
	private final Integer bonusUnits;

	BonusOffer(Integer selectedUnits, Integer bonusUnits) {
		super();
		this.selectedUnits = selectedUnits;
		this.bonusUnits = bonusUnits;
	}

	public Integer getSelectedUnits() {
		return selectedUnits;
	}

	public Integer getBonusUnits() {
		return bonusUnits;
	}
}
