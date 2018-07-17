/*
 * ReadingMatters.java  	1.1 21/12/2015
 *
 * Copyright (c) University of Sheffield 2016
 */

/** 
* ReadingMatters.java
* 
* 
* @version 1.1 21/12/2015
* 
* @author Phil McMinn/Richard Clayton
*/

package uk.ac.sheffield.com1003.objectville.polymorphism;

import uk.ac.sheffield.com1003.objectville.inheritance.Book;
import uk.ac.sheffield.com1003.objectville.inheritance.Magazine;
import uk.ac.sheffield.com1003.objectville.inheritance.Newspaper;
import uk.ac.sheffield.com1003.objectville.inheritance.ReadingMatter;

public class ReadingMatters {

	public static void main(String[] args) {
		
		ReadingMatter[] readingMatters = {
			new Magazine(100, "Wired", "monthly"),
			new Book(100, "Race over the Cliff", "Hugo First"),
			new Book(200, "The Grass is Greener", "I.P. Daley"),
			new Newspaper(20, "New Study of Obesity Looks for Larger Test Group")
		};
		
		for (int i=0; i < readingMatters.length; i++) {
			readingMatters[i].printInfo();
		}
	}
}
