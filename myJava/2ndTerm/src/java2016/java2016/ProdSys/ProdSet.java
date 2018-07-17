/**
 * ProdSet.java
 * A set of productions
 * 2013 version
 */

 import java.util.*;
 import java.io.*;

 public class RuleSet {

 	private ArrayList<Prodn> prods;

 	public RuleSet (ArrayList a){
 		prods = a;
 		}


    public ArrayList<Prodn> getProds() {return prods;}
}
