package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

import java.util.Map;

/**
 * The main interface that represents a purchase processing system at checkout. If promotional offers have been
 * configured, then adds bonus items or discounts to the shopping cart.
 * 
 * @author goutam.bhattacharjee
 *
 */
public interface Processor {

	/**
	 * 
	 * @param countPerItem
	 *            A Map that contains item name as key and units selected as value
	 * @return The shopping cart that contain the computed gross price, bonus items (if applicable) and total discount
	 *         (if applicable)
	 */
	public ShoppingCart compute(Map<String, Integer> countPerItem);

}
