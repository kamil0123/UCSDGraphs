package roadgraph;

public class Road {

	public final String roadName;
	public final String roadType;
	public final Double length;

	public Road(String roadName, String roadType, Double length) {
		this.roadName = roadName;
		this.roadType = roadType;
		this.length = length;
	}

	public String getRoadName() {
		return roadName;
	}

	public String getRoadType() {
		return roadType;
	}

	public Double getLength() {
		return length;
	}
}
