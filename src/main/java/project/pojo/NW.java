package project.pojo;

public class NW {
	
	private String name;
	private int rowOrColumnNumber;
	private int numberOfZeros;
	
	public NW(String name, int rowOrColumnNumber, int numberOfZeros) {
		super();
		this.name = name;
		this.rowOrColumnNumber = rowOrColumnNumber;
		this.numberOfZeros = numberOfZeros;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRowOrColumnNumber() {
		return rowOrColumnNumber;
	}

	public void setRowOrColumnNumber(int rowOrColumnNumber) {
		this.rowOrColumnNumber = rowOrColumnNumber;
	}

	public int getNumberOfZeros() {
		return numberOfZeros;
	}

	public void setNumberOfZeros(int numberOfZeros) {
		this.numberOfZeros = numberOfZeros;
	}

	@Override
	public String toString() {
		return "NW [name=" + name + ", rowOrColumnNumber=" + rowOrColumnNumber + ", numberOfZeros=" + numberOfZeros
				+ "]";
	}
	
}
