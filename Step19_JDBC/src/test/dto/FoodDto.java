package test.dto;

public class FoodDto {
	private int num;
	private String name;
	private String country;
	
	public FoodDto() {}

	public FoodDto(int num, String name, String country) {
		super();
		this.num = num;
		this.name = name;
		this.country = country;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
