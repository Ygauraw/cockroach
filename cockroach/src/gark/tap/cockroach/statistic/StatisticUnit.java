package gark.tap.cockroach.statistic;

public class StatisticUnit {
	private int count;
	private String name;

//	public StatisticManager(){}
	
	public StatisticUnit(int count, String name) {
		this.count = count;
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
