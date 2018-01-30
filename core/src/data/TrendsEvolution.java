package data;

import java.util.Collection;
import java.util.Map;

public class TrendsEvolution {

	String origin, destination,y_name;
	Map<String, String> names;
	String[] years;
	public TrendsEvolution(String origin, String destination, String y_name, Map<String, String> names,String[] years) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.y_name = y_name;
		this.names = names;
		this.years=years;
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
	public String getY_name() {
		return y_name;
	}
	public void setY_name(String y_name) {
		this.y_name = y_name;
	}
	public Map<String, String> getNames() {
		return names;
	}
	public void setNames(Map<String, String> names) {
		this.names = names;
	}

	public String[] getYears() {
		return years;
	}

	public void setYears(String[] years) {
		this.years = years;
	}

	
	
}
