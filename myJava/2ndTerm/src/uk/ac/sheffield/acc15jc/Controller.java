package uk.ac.sheffield.acc15jc;

import java.util.Calendar;
import java.util.HashMap;

public class Controller {
	public static void main(String[]args){
		
		HashMap<String, String> ICAOcode = new HashMap<String,String>();

//		ICAOcode.put("Manchester", "EGCB");
//		ICAOcode.put("Birmingham", "EGBB");
//		ICAOcode.put("Coventry", "EGBE");
//		ICAOcode.put("Leicester", "EGBG");
//		ICAOcode.put("Nottingham", "EGBN");
//		ICAOcode.put("Bristol", "EGGD");
//		ICAOcode.put("Liverpool", "EGGP");
//		ICAOcode.put("London", "EGLL");
//		ICAOcode.put("Sheffield", "EGCN");
//		
//		ViewerFrame vf = new ViewerFrame();
//		vf.buildCode(ICAOcode);//put the code into Frame
		System.out.println(Calendar.getInstance().get(Calendar.DATE));
	}

}
