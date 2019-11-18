package model;

public class Plate {
	private String plate;
	private long start;
	//	构造函数;
	public Plate() {
		
	}
	//	含参数构造函数;
	public Plate(String plate, long start) {
		super();
		this.plate = plate;
		this.start = start;
	}

	public String getPlate() {
		return plate;
	}

	public long getStart() {
		return start;
	}
	
	
}
