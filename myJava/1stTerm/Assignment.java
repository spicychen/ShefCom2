import sheffield.*;
public class Assignment {
	public static void main(String args[]){
		
		EasyReader keyboard = new EasyReader();
		EasyWriter screen = new EasyWriter();
//read
		int stones = keyboard.readInt(
			"Enter a number representing weight in stones (0~1000): ");
		int pounds = keyboard.readInt(
			"And in pounds, please (0~13): ");
		
//possible input
		if ( stones<0 || pounds<0)
			screen.println("Impossible weights!!");
		else if (stones>1000 || pounds>13)
			screen.println(
				"Too heavy to calculate or pounds can be converted to stones.");
		else {
			
		EasyReader fileInput = new EasyReader("planets.txt");
		String txt = fileInput.readString();
		
//find a number
		int dot = txt.indexOf('.');
		String p = txt.substring(3,5);
		String num = txt.substring(dot-2,dot+3);
		switch (p) {
			case "Ve": case "ve": case "Ma": case "ma": case "Io": case "io":
				case "Eu": case "eu": case "Ca": case "ca":case "Pl": case "pl":
					num = txt.substring(dot-2,dot+4); break;
			case "Mo": case "mo": case "Ce": case "ce": case "En": case "en":
				case "Tr": case "tr": case "Er": case "er":
					num = txt.substring(dot-2,dot+5); break;
			case "Ph": case "ph": case "De": case "de": case "P6": case "p6": 
					num = txt.substring(dot-2,dot+7); break;
		}
		double n = Double.valueOf(num);
		
//find the planet
		int end=9, gap=18;
		switch (p) {
			case "Ju": case "ju": case "Ne": case "ne": case "Me": case "me":
				end = 10; gap = 17; break;
			case "Su": case "su": case "P6": case "p6":
				end = 6; gap = 21; break;
			case "Ve": case "ve": case "Ea": case "ea": case "Ce": case "ce":
				case "Ti": case "ti":case "Pl": case "pl":
					end = 8; gap = 19; break;
			case "Mo": case "mo": case "Ma": case "ma": case "Er": case "er":
				end = 7; gap = 20; break;
			case "Io": case "io":
				end = 5; gap = 22; break;
			case "Ga": case "ga": case "Ca": case "ca":
				end = 11; gap = 16; break;
			case "En": case "en":
				end = 12; gap = 15; break;
		}
		String planet = txt.substring(3,end);
		
//calculate weights
		double kilo = stones*6.35029318+pounds*6.35029318/14;
		double weightAtPlanet = kilo*n;

//output
		screen.print(stones+" stones and "+pounds+" pounds is ");
			screen.print(kilo,2);
				screen.println("kg on Earth");
		screen.print("Earth");
			screen.print(kilo,4,19);
				screen.println("kg");
		screen.print(planet);
			screen.print(weightAtPlanet,4,gap);
				screen.println("kg");
		}
	}
}
