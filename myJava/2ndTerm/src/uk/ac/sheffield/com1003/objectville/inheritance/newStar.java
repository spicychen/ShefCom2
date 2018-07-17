package uk.ac.sheffield.com1003.objectville.inheritance;

public class newStar {
	public static void main(String[]arg){
		AstronomicalObject newDot = new AstronomicalObject(10);
		Star[] newStars = {new Star(30, 100), new RedGiant(100, -30, 100000000)};
		newDot.printInfo();
		newStars[0].printInfo();
		newStars[1].printInfo();
		newStars[0] = newStars[1];
		//RedGiant newRedGiant = (RedGiant) newStars[1].makeCopy();
		//newRedGiant.printInfo();
		if (newStars[0].equals(newStars[1]))
			System.out.println("you HAve identical stars.");
		
	}

}
