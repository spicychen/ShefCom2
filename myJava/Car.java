/*
 * Car.java  	1.1 21/12/2015
 *
 * Copyright (c) University of Sheffield 2016
 */

/** 
* Car.java
* 
* 
* @version 1.1 21/12/2015
* 
* @author Richard Clayton  (r.h.clayton@sheffield.ac.uk)
*/
	

public class Car{

    // constructor
    public Car( String mk, String c, int p, int ma, boolean s){
	make = mk;
	colour = c;
	price = p;
	sold = s;
	mileAge = ma;
    }
    
    // get methods 
    public String getMake(){
	return make;
    }
    
    public String getColour(){
	return colour;
    }
    
    public int getPrice(){
	return price;
    }
    
    public int getMileAge(){
    return mileAge;
    }
    
    public boolean isSold() {
	return sold;
    }

    // set methods
    public void setPrice(int p) {
	price=p;
    }
    
    public void sellCar(){
	sold = true;
    }
    
    // toString: writing a toString method allows object to 
    // be printed using System.out.println(car);
    public String toString() {
	String outString = "Make="+make+"  Colour="+colour+"  Price="+price+"  Mileage="+mileAge;
	if (sold)
	    outString+=" [SOLD]";
	return outString;
    }
    
    // instance fields
    private String make;
    private String colour;
    private int price;
    private boolean sold;
    private int mileAge;
}
