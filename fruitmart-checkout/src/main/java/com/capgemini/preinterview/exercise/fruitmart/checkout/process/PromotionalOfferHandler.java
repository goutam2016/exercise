package com.capgemini.preinterview.exercise.fruitmart.checkout.process;

/**
 * Represents promotional offer handling. If specific items have been setup for bonus offers or discounts then adds the
 * bonus items and/or discounts to the shopping cart. Updates the net price accordingly.
 * 
 * @author goutam.bhattacharjee
 *
 */
public interface PromotionalOfferHandler {

	public void applyOffers(ShoppingCart shoppingCart);
}
