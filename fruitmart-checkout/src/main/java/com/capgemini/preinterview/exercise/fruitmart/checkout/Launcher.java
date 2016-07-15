package com.capgemini.preinterview.exercise.fruitmart.checkout;

import java.io.Console;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.capgemini.preinterview.exercise.fruitmart.checkout.process.Processor;
import com.capgemini.preinterview.exercise.fruitmart.checkout.process.PropertiesProvider;
import com.capgemini.preinterview.exercise.fruitmart.checkout.process.ShoppingCart;

/**
 * Launches the fruitmart checkout system application. Pass fruits selected as command line arguments, separated by
 * space.
 * 
 * @author goutam.bhattacharjee
 *
 */
public class Launcher {

	private static final Logger LOGGER = Logger.getLogger(Launcher.class);

	public static void main(String[] args) {
		try (ConfigurableApplicationContext appContext = new ClassPathXmlApplicationContext(
				"fruitmart-checkout-context.xml")) {
			PropertiesProvider fruitmartProperties = appContext.getBean(PropertiesProvider.class);
			Set<String> fruitsAvailable = fruitmartProperties.getItemsAvailable();
			ArgumentParser argumentParser = appContext.getBean(ArgumentParser.class);
			Map<String, Integer> countPerFruit = argumentParser.parseArguments(args, fruitsAvailable);
			Processor checkoutProcessor = appContext.getBean(Processor.class);
			ShoppingCart shoppingCart = checkoutProcessor.compute(countPerFruit);
			printOutput(shoppingCart);
		} catch (Exception ex) {
			LOGGER.error("Exception caught in main", ex);
		}
	}

	private static void printOutput(ShoppingCart shoppingCart) {
		Console console = System.console();

		if (console != null) {
			console.printf("---------------------------------------------------------------------\n");
			console.format("Final items: %s\n", shoppingCart.getFinalItems());
			console.format("Gross price: GBP %s\n", shoppingCart.getGrossPrice());
			console.format("Total discount: GBP %s\n", shoppingCart.getDiscount());
			console.format("Net price: GBP %s\n", shoppingCart.getNetPrice());
			console.printf("---------------------------------------------------------------------\n");
		}
	}
}
