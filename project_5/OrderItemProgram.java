package project_5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OrderItemProgram {
	static Scanner sc = new Scanner(System.in);
	//상품객체배열
	static ArrayList<Product> list = new ArrayList<>();
	//리뷰객체배열
	static ArrayList<Review> reviews = new ArrayList<>();
	public static void main(String[] args) {
		ProductFunction pf = new ProductFunction(); //상품관련 함수들 클래스
		ReviewFunction rf = new ReviewFunction(); //리뷰 관련 함수들 클래스
		int menu;
		int num=0;
		boolean sw; //입력값 잘못입력했을때 반복문 제어할 변수
		boolean show = false; //리뷰 앞 숫자 보여줄지 제어할 변수
		//파일에서 상품 목록 불러와 list 배열에 넣음
		product();
		//파일에서 리뷰 목록 불러와 reviews 배열에 넣음
		reviews();
		while(true) {
			System.out.println("\t\t\t~환영합니다~");
			System.out.println("=========================================================");
			System.out.println("메뉴 :: 1.상품 조회 | 2.상품 구매 | 3.리뷰 등록 | 4.리뷰 삭제 | 5.종료");
			System.out.println("---------------------------------------------------------");
			System.out.print("메뉴를 선택하세요 > ");
			menu = sc.nextInt();
			switch(menu) {
			case 1:
				//상품 목록 출력
				pf.productList(list);
				while(true) {
					sw=true; //46라인을 위한 변수
					System.out.print("상품 상세보기를 진행할까요? (1:y / 2:n) > ");
					int input = sc.nextInt();
					//2을 입력할 경우 69라인으로 이동
					if(input == 2) {
						break;
					}else if(input == 1) {
						while(sw) {
							System.out.print("자세히 보고싶은 상품의 번호를 입력하세요 > ");
							num = sc.nextInt();
							
							/* 54라인 : 상품의 번호가 유효한지 확인하고 반환값을 sw에 넣음
							 * 반환값이 true인 경우 반복문을 빠져나가지 못하고 다시 입력을 받기 위해 46라인 이동 -> 상품번호가 존재할때까지 반복, 
							 * 상품번호가 존재하면 sw=false를 반환받고 반복문 빠져나옴 
							 */
							sw = pf.check(list,num);
						}
						//입력받은 상품번호에 맞는 상품 객체를 가져와 detail에 넣음
						Product detail = list.get(num-1);
						//상품 상세보기 출력
						pf.showList(detail);
						//상품 리뷰 출력
						rf.showReview(reviews, detail, show);
						break;
					}else {
						System.out.println("다시 입력해주세요");
						//y,n외에 다른 문자 입력했을 경우 38라인으로 이동
						continue;
					}
				}
				break;
			case 2:
				//상품 목록 출력
				pf.productList(list);
				//50라인 설명과 똑같이 동작하는 부분
				sw=true;
				while(sw) {
					System.out.print("구매하실 상품의 번호를 입력하세요 > ");
					num = sc.nextInt();
					sw = pf.check(list,num);
				}
				//입력받은 상품번호에 맞는 상품 객체를 buy에 넣음
				Product buy = list.get(num-1);
				//상품 구매 함수 호출
				pf.buyProduct(buy);
				break;
			case 3:
				//상품 목록 출력
				pf.productList(list);
				sw=true;
				while(sw) {
					System.out.print("리뷰를 등록할 상품 번호를 입력하세요 > ");
					num = sc.nextInt();
					sw = pf.check(list,num);
				}
				sc.nextLine();
				Product p = list.get(num-1);
				rf.addReview(p, reviews, num);
				break;
			case 4:
				//리뷰가 있는 상품들만 출력하는 함수, 진짜 상품번호 배열을 전달 받음
				ArrayList<Integer> rNum = rf.rpList(reviews, list);
				//진짜 상품번호 배열의 인덱스 0번의 값을 가져와 f에 넣음
				int f = rNum.get(0);
				//f 값이 104030915이면 리뷰가 등록된 상품이 없다는 뜻
				if(f == 104030915) {
					//인덱스 0번 삭제(삭제 안하면 등록된 상품이 존재해도 없다고 판단)
					rNum.remove(0);
					//case 4번 빠져나옴
					break;
				}
				//리뷰 삭제 함수 호출
				rf.deleteReview(reviews, rNum, list);
	            break;
			case 5:
				writeToFile();//종료시 지금까지 수정된 내용 파일에 덮어씀
				System.exit(0); 
				break;
			default:
				System.out.println("메뉴를 다시 입력해주세요.");
				break;
				
			}
		}

	}
	
	//상품 객체 생성 함수
	public static void product() {
		 // 파일에서 상품목록 읽어오기
        BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("res/상품목록.txt"));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            // 콤마로 구분된 문자열을 분리하여 상품 이름, 가격, 재고 불러옴
	            String[] data = line.split(",");
	           
	            String name = data[0];
	            int price = Integer.parseInt(data[1]);
	            int stock = Integer.parseInt(data[2]);
	            String desc = data[3];
	            
	            list.add(new Product(name, price, stock,desc));
	        }

		} catch (IOException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		    if (reader != null) {
		        try {
		            reader.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}


	}

	//리뷰 불러와서 객체 배열로 만드는 함수
	public static void reviews() {
		BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader("res/리뷰목록.txt"));
			String line;
			int index = 0; //파일 줄 처리를 위한 인덱스 변수
			while ((line = reader.readLine()) != null) {
				// 콤마로 구분된 문자열을 분리하여 카운트, 상품 이름, 리뷰 내용 불러옴
				String[] data = line.split(",");
				
				//첫번째는 카운트로 cnt에 저장
				int cnt = Integer.parseInt(data[0]);
				//두번째는 상품이름으로 name에 저장
				String name = data[1];
				//카운트와 상품이름을 불러왔으면 생성자를 통해 리뷰객체 생성
				reviews.add(new Review(name, cnt));
				//파일에 적힌 리뷰 개수만큼 반복문 돌려 생성된 리뷰 객체의 리뷰 목록 배열에 추가
				for (int i = 2; i < data.length; i++) {
					reviews.get(index).setReview(data[i]);
				}
				index++; //reviews.get(index) -> 파일의 첫번째 줄의 리뷰를 처리하고 index++을 해서 파일 다음줄 리뷰 처리

			}
		} catch (IOException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		    if (reader != null) {
		        try {
		            reader.close();
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }
		}
	}
	
	// 수정된 정보 파일에 쓰기 함수
	public static void writeToFile() {
		try {
			FileWriter fw = new FileWriter("res/상품목록.txt");
			FileWriter fw2 = new FileWriter("res/리뷰목록.txt");
			//상품객체 배열에 들어있는 각각의 상품 객체의 내용을 파일 저장형식에 맞춰서 저장
			for(Product p : list) {
				String line = p.getName()+","+p.getPrice()+","+p.getStock()+","+p.getDesc()+"\n";
				fw.write(line);
			}
			//리뷰 객체 배열의 리뷰 객체들을 파일 저장형식에 맞춰 저장
			for(Review r : reviews) {
				String line = r.getCnt()+","+r.getName()+",";
				ArrayList<String> re = r.getReview();
				int i=0;
				for(String review : re) { //리뷰 객체의 리뷰 목록 크기만큼 반복
					i++;
					if(i != re.size()) //i랑 리뷰 목록 크기가 다르면 뒤에 ',' 붙임
						line += review+",";
					else { //i랑 리뷰 목록 크기가 같은 경우, 즉 마지막 요소임으로 뒤에 ','대신 줄바꿈 넣음
						line += review+"\n";
					}
				}
				fw2.write(line);
			}
			fw.close();
			fw2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}