package project_5;

public class Product {
	private String name;
	private int price;
	private int stock;
	private String desc;
	private String soildOut = "품절";
	
	public Product(String name, int price, int stock,String desc) {
		super();
		this.name = name;
		this.price = price;
		this.stock = stock;
		this.desc = desc;
	}


	public void stockCheck(int num) {
		stock -= num;
	}


	@Override
	public String toString() {
		 String formattedName = String.format("%-25s", name); // 이름을 왼쪽 정렬하여 25자리로 맞춤
		 String formattedPrice = String.format("%-10s", price+"원"); // 가격을 왼쪽 정렬하여 10자리로 맞춤
		 String formattedStock;
		 if(stock == 0) {
			 formattedStock = String.format("%-10s", "품절");
		 }else {
			 formattedStock = String.format("%-10s", stock+"개"); // 재고를 왼쪽 정렬하여 10자리로 맞춤
		 }
		 return formattedName + "\t" + formattedPrice + "\t" + formattedStock;
	}
	
	public int orderPrice(int num) {
		int op = price * num;
		return op;
	}


	public String getName() {
		return name;
	}


	public int getPrice() {
		return price;
	}


	public int getStock() {
		return stock;
	}
	public String getDesc() {
		return desc;
	}
	public String getSolidOut() {
		return soildOut;
	}
	
	
	
}