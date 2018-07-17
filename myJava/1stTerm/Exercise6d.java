public class Exercise6d{
	public static void main(String[]args){
		final double LEMONS_PRICED = 0.43;
		final double PROFIT_PERCENTAGE = 0.21;
		final double PARTICULAR_PROFIT = 14;
		double lemons = PARTICULAR_PROFIT/PROFIT_PERCENTAGE/LEMONS_PRICED;
		System.out.print("He sells " + (int)lemons + " lemons");
	}
}