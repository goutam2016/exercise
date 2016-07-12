package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.util.Map;

public interface Processor {

	public PurchaseSummary compute(Map<String, Integer> countPerItem);

}
