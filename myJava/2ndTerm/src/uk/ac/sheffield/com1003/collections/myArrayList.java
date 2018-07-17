package uk.ac.sheffield.com1003.collections;
import java.util.ArrayList;
import java.util.Iterator;


public class myArrayList {
	public static void main(String[]args){
		
		ArrayList<String> animals = new ArrayList<String>();
		animals.add("elephant");
		animals.add("lion");
		animals.add("leopoard");
		animals.add("tiger");
		
		Iterator<String> it = animals.iterator();
		for(String i: animals)
		{	System.out.println(it.next());
		}
		animals.remove(0);
		System.out.println(animals);
		
	}

}
