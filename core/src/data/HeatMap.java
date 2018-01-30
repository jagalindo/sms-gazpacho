package data;

import java.util.Map;

public class HeatMap {

	String origin, destination,dimension1_name,dimension2_name;
	String[] dimension_1, dimension_2;
	Map<String, String> names;
	
	public HeatMap(String origin, String destination, String dimension1_name, String dimension2_name, String[] dimension_1, String[] dimension_2,
			Map<String, String> names) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.dimension1_name=dimension1_name;
		this.dimension2_name=dimension2_name;
		this.dimension_1 = dimension_1;
		this.dimension_2 = dimension_2;
		this.names = names;
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

	public String[] getDimension_1() {
		return dimension_1;
	}

	public void setDimension_1(String[] dimension_1) {
		this.dimension_1 = dimension_1;
	}

	public String[] getDimension_2() {
		return dimension_2;
	}

	public void setDimension_2(String[] dimension_2) {
		this.dimension_2 = dimension_2;
	}

	public Map<String, String> getNames() {
		return names;
	}

	public void setNames(Map<String, String> names) {
		this.names = names;
	}

	public String getDimension1_name() {
		return dimension1_name;
	}

	public void setDimension1_name(String dimension1_name) {
		this.dimension1_name = dimension1_name;
	}

	public String getDimension2_name() {
		return dimension2_name;
	}

	public void setDimension2_name(String dimension2_name) {
		this.dimension2_name = dimension2_name;
	}

}
