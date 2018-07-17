package uk.ac.sheffield.com1003.problemsheet2;

import org.junit.*;


import static org.junit.Assert.*;

public class TestItemEquals {
	Item a,b,c,d,e,f,g;


	@Before
	public void setup() {
		a = new ItemByWeight("Double Chocolate SWISS ROLL", 4.35, 0.230);
		b = new ItemByWeight("Double Chocolate SWISS ROLL", 4.35, 0.230); //testing for identical object
		
		c = new Item("HARIBO", 4.52);
		d = new ItemByWeight("HARIBO",  4.52, 0.215); //testing for different classes
		
		e = new Item("COLA", 0.55);
		f = new Item("SENSATIONS", 1.00);
		e = f;//testing for reference copy			
		
	}
	
	@Test
	public void testing1(){
		assertTrue(a.equals(b)); //testing for identical object
	}
	
	@Test
	public void testing2(){
		assertFalse(c.equals(d)); //testing for different classes
	}
	
	@Test
	public void testing3(){
		assertTrue(e.equals(f)); //testing for reference copy	
	}
	
	@Test
	public void testing4(){
		assertFalse(a.equals(g)); //testing for null object
	}

	@After
	public void tearDown() {
		
	}
	
}
