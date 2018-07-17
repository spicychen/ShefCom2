public class Ninja {
	
	private String name;
	private Weapon weapon;
	
	public Ninja(String name, Weapon weapon) {
		this.name = name;
		this.weapon = weapon;
	}
	
	public String getName() {
		return name;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public String toString() {
		return "Ninja called "+name+" with a "+weapon;
	}
	
}

