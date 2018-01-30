package data;

public class Barplot {

	String origin, destination;
	String[] years;
	
	public Barplot(String origin, String destination, String[] years) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.years = years;
	}
	
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String[] getYears() {
		return years;
	}
	public void setYears(String[] years) {
		this.years = years;
	}
	
}
