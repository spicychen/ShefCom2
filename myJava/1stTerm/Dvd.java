public class Dvd {
	private String title;
	private double price;
	private int id;
	private Genre genre;
	
	private static int counter = 0;
	
	public Dvd() {
		this("Unknown", 0.0, 0, null);
	}
	public static int getCounter() {return counter;}
	public Dvd(String t, double p, int i, Genre g) {
		setTitle(t);
		setPrice(p);
		id = i;
		genre = g;
		counter++;
	}
	
	public String getTitle() {
		return title;
	}
	
	public double getPrice() {
		return price;
	}
	
	public int getId() {
		return id;
	}
	
	public void setPrice(double p) {
		price = p;
	}
	
	public void setTitle(String t) {
		title = t;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return "DVD title = " + title + ", Price = " + price + " and ID = "
		+ id + " and Genre = " + genre.toString();
	}
	
	public void setGenre(Genre g) { genre=g; }
	public Genre getGenre() {return genre;}
	
	public static void main(String[]args) {
		System.out.println(Dvd.getCounter());
		Dvd fantasy = new Dvd("Fantasy", 7.7, 6666, Genre.ACTION);
		Dvd fantasy3 = new Dvd();
		System.out.println(Dvd.getCounter());
		System.out.println(fantasy3.getCounter());
		
		//String str = fantasy.toStr();
	
		System.out.print(fantasy);
	}
}