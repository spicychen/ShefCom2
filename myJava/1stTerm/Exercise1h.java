public class Exercise1h{
	public static void main(String[] args) {
		double p=12;
		double c=9000;
		double n=12;
		double m=c*p/1200*Math.pow(1+p/1200,n)/(Math.pow(1+p/1200,n)-1);
		System.out.print(m);
	}
}