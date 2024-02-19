package project_5;

import java.util.ArrayList;
import java.util.Scanner;

public class ProductFunction {
	static Scanner sc = new Scanner(System.in);

	// 상품 목록 함수
	public void productList(ArrayList<Product> list) {
		//문자열 정렬
		String formattedNum = String.format("%-8s", "상품번호"); 
		String formattedName = String.format("%-20s", "상품명"); 
		String formattedPrice = String.format("%-10s", "가격"); 
		String formattedStock = String.format("%-10s", "재고");
		System.out.println("==========================☆★상품 목록★☆==========================");
		System.out.println(formattedNum + "\t" + formattedName + "\t" + formattedPrice + "\t" + formattedStock);
		System.out.println("---------------------------------------------------------------");
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "\t   " + list.get(i));
		}
		System.out.println("---------------------------------------------------------------");
	}

	// 상품 상세보기 함수
	public void showList(Product list) {
		System.out.println("\n================☆★상세 보기★☆=================");
		String formattedName2 = String.format("%-20s", list.getName()); 
		String formattedPrice2 = String.format("%-10s", list.getPrice() + "원"); 
		String formattedStock2;
		//재고가 0일경우 품절 출력
		if(list.getStock() == 0) {
			formattedStock2 = String.format("%-10s", "품절");
		}else {
			formattedStock2 = String.format("%-10s", list.getStock() + "개");
		}
		System.out.println(String.format("%-20s", "상품명") + "\t" + String.format("%-10s", "가격") + "\t"
				+ String.format("%-10s", "재고"));
		System.out.println("--------------------------------------------");
		System.out.println(formattedName2 + "\t" + formattedPrice2 + "\t" + formattedStock2);
		System.out.println("============================================");
		System.out.print("상품설명 ▶ ");
		System.out.print(list.getDesc());
		System.out.println("\n--------------------------------------------");
		System.out.print("상품리뷰 ▼");
		System.out.println("\n--------------------------------------------");
	}

	// 상품 구매 함수
	public void buyProduct(Product list) {
		int stock;
		String st; //재고 변수
		if (list.getStock() == 0) {
			st="품절";
			System.out.println("[" + list.getName() + "]  /  " + list.getPrice() + "원" + "  /  " + st);
			System.out.println("품절된 상품으로 구매하실 수 없습니다.");
		} else {
			st = list.getStock()+"개";
			System.out.println("[" + list.getName() + "]  /  " + list.getPrice() + "원" + "  /  " + st);
			while (true) {
				System.out.print("구매수량을 입력해주세요 > ");
				stock = sc.nextInt();
				//입력한 구매수량이 상품의 재고 개수를 초과할 경우 60라인으로 이동
				if (stock > list.getStock()) {
					System.out.println("재고가 부족합니다. 다시 입력해주세요");
					continue;
				} else {
					while(true) {
						System.out.print("정말 구매하시겠습니까? (1:y / 2:n) > ");
						int input = sc.nextInt();
						//n을 입력한 경우 구매 취소로 반복문 빠져나옴
						if(input==2) {
							System.out.println("구매가 취소되었습니다.");
							break;
						}else if(input==1) {
							//상품의 재고 개수를 구매수량만큼 차감
							list.stockCheck(stock);
							//구매수량만큼 원래 가격에 곱해서 값 전달
							int op = list.orderPrice(stock); //총 가격
							System.out.println(list.getName() + " " + stock + "개 주문 -> 총 " + op + "원");
							System.out.println("구매가 완료되었습니다!");
							break;
						//y,n 외에 다른걸 입력했을경우 68라인으로 이동
						} else {
							System.out.println("다시 입력해주세요.");
							continue;
						}
					}
					
					
				}
				break;
			}

		}

	}
	
	// 상품 번호 확인
	public boolean check(ArrayList<Product> list, int num) {
		boolean sw = true;
		if (num > list.size() || num == 0) {
			System.out.println("존재하지 않는 상품번호 입니다. 다시 입력해주세요");
		} else {
			//상품의 번호가 유효하면 sw=false
			sw = false;
		}
		return sw;
	}
}