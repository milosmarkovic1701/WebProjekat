package beans;

import enums.Type;

public class CustomerType {
	
	private Type type;
	private int precentOff;
	private int points;
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getPrecentOff() {
		return precentOff;
	}
	public void setPrecentOff(int precentOff) {
		this.precentOff = precentOff;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	public CustomerType(Type type, int precentOff, int points) {
		super();
		this.type = type;
		this.precentOff = precentOff;
		this.points = points;
	}
	
	@Override
	public String toString() {
		switch(type) {
		case SILVER:
			return "Srebrni";
		case GOLD:
			return "Zlatni";
		default:
			return "Bronzani";
		}
	}
}