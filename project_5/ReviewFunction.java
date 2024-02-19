package project_5;

import java.util.ArrayList;
import java.util.Scanner;


public class ReviewFunction {
	static Scanner sc = new Scanner(System.in);
	//리뷰 보여주기 함수
	public void showReview(ArrayList<Review> reviews, Product list,boolean show) {
		boolean result=true; //리뷰가 존재하는지 확인하기 위한 변수
		for(int i=0;i<reviews.size();i++) { //리뷰객체 배열 크기만큼 반복
			//리뷰 객체에서 리뷰를 등록할 상품의 이름과 동일한 이름이 있는지 확인
			if(list.getName().equals(reviews.get(i).getName())) { 
				//동일한 이름이 있으면 그 리뷰 객체의 리뷰목록 가져와 r 변수에 넣음
				ArrayList<String> r = reviews.get(i).getReview();
				//if(r.size() == 0) break; //상품명과 동일한 리뷰 객체는 있지만 그 객체에 리뷰목록 크기가 0일경우 break;
				//r에 들어있는 리뷰 목록 배열을 출력
				for (String review : r) { 
					//show가 true일 경우 앞에 숫자가 붙어있는 리뷰 그대로 출력
					if(show) {
						System.out.println(review);
					//true가 아니면 숫자 빼고 출력
					}else {
						System.out.println(review.substring(2));	
					}
				}
				//상품에 리뷰가 있으니 result를 false로 만들어 밑에 if(result) 출력 방지
				result=false;
			}
		}
		//상품의 이름과 동일한 리뷰 객체가 없는 경우 출력
		if(result) {
			System.out.println("등록된 리뷰가 없습니다.");
		}
		
	}
	
	
	//리뷰 등록 함수
	public void addReview(Product list, ArrayList<Review> reviews, int num) {
		
		String name = list.getName();
		System.out.println("["+name+"]"+"에 등록된 리뷰 ▼");
		boolean show = false; //리뷰 앞 숫자 보여주기 싫어서 false, showReview() 함수에 if(show)부분을 만족하고 싶으면 true 전달 -> 리뷰 앞에 숫자 보여짐 
		//리뷰 보여주는 함수 호출
		showReview(reviews, list, show);
		System.out.print("아이디를 입력하세요 > ");
		String id = sc.next();
		sc.nextLine();
		System.out.print("리뷰 작성 > ");
		String desc = sc.nextLine();
		Review rObj; //리뷰 클래스 타입의 변수 rObj
		if(reviews.size() > 0) { //리뷰객체 배열에 객체가 하나라도 들어있는 경우
			boolean newAdd = true; //리뷰 객체에 상품명과 동일한 이름이 없는 경우 리뷰 추가를 위한 변수
			for (int i = 0; i < reviews.size(); i++) { //리뷰 객체 배열 크기만큼 반복
				if (name.equals(reviews.get(i).getName())) { //상품명과 리뷰객체의 이름이 같을 경우(이미 작성된 리뷰가 있는 상품일 경우)
					rObj = reviews.get(i); //그 리뷰 객체를 rObj변수에 넣음
					rObj.countCnt(); //리뷰 객체의 카운트값 증가시킴
					rObj.setReview(rObj.getCnt()+" "+id+" : "+desc); //리뷰 객체에 숫자와 입력받은 아이디,리뷰 를 배열에 저장, cnt.getCnt() -> 각 리뷰 객체의 카운트 값 가져옴
					newAdd=false;//이미 리뷰를 가지고 있는 상품이었기 때문에 newAdd는 false로 만들어 실행 못하게 함
				}
			}
			//리뷰가 등록된 적이 없는 상품이었을 경우
			if(newAdd) {
				newAddReview(reviews, name, id, desc);
			}
		//리뷰 객체 배열에 객체가 하나도 없을 경우
		}else {
			newAddReview(reviews, name, id, desc);
		}
		System.out.println("리뷰가 등록되었습니다!");
	}
	
	//리뷰 객체 추가 함수
	public void newAddReview(ArrayList<Review> reviews, String name, String id, String desc) {
		int cnt=1;
		reviews.add(new Review(name,cnt)); //리뷰 객체 배열에 새로 추가
		Review r = reviews.get(reviews.size()-1); //방금 새로 추가한 리뷰 객체를 r에 넣음
		r.setReview(cnt+" "+id+" : "+desc);//리뷰 객체의 리뷰 목록에 저장
	}
	
	
	//리뷰가 있는 상품들만 보여주는 함수
	public ArrayList<Integer> rpList(ArrayList<Review> reviews, ArrayList<Product> list) {
		ArrayList<Integer> realNum = new ArrayList<Integer>(); //진짜 상품 번호를 저장할 배열
		String formattedNum = String.format("%-8s", "상품번호"); 
		String formattedName = String.format("%-20s", "상품명"); 
		String formattedPrice = String.format("%-10s", "가격"); 
		String formattedStock = String.format("%-10s", "재고");
		System.out.println("======================☆★리뷰가 있는 상품 목록★☆======================");
		System.out.println(formattedNum + "\t" + formattedName + "\t" + formattedPrice + "\t" + formattedStock);
		System.out.println("---------------------------------------------------------------");
		int i=0; //가짜 상품번호
		//리뷰 객체 배열의 크기가 0일 경우
		if(reviews.size() == 0) {
			System.out.println("리뷰가 등록된 상품이 없습니다.");
			//등록된 리뷰가 없다는 뜻으로 104030915를 진짜 상품 배열에 넣음
			realNum.add(104030915);
		}else {
			//리뷰 객체 배열에 객체가 들어잇는 경우
			for (Review r : reviews) {
				i++; //출력해서 보여줄 가짜 상품번호 +1
				String name = r.getName();
				int ri = 0;//realNum에 넣어 전달할 진짜 상품 번호
				
				//리뷰가 들어있는 상품 객체를 찾기 위한 과정
				for (Product p : list) {//상품 객체 배열 순회
					ri++;//진짜 상품번호 +1
					if (name.equals(p.getName())) {  //리뷰 객체의 상품명과 상품 객체의 상품명이 같으면 
						System.out.println(i + "\t   " + p.toString()); //가짜 상품번호 출력 후 상품 객체의 정보 출력
						realNum.add(ri - 1); //realNum에는 진짜 상품번호 넣음
						break;
					} else
						continue; //상품명이 같지 않으면 아무것도 하지 않고 다음 객체로 넘어감
				}
			}
		}
		System.out.println("---------------------------------------------------------------");
		return realNum; //진짜 상품 번호가 들어있는 배열 리턴
	}
	
	//리뷰 삭제 함수
	public void deleteReview(ArrayList<Review> reviews, ArrayList<Integer> rNum, ArrayList<Product> list) {
		boolean show;
		int num;
		while(true) {
			System.out.print("삭제할 상품 번호를 입력하세요 > ");
			num = sc.nextInt();
			//상품번호가 리뷰 객체 배열의 크기보다 크거나 0인 경우
			if(num > reviews.size() || num == 0) {
				System.out.println("존재하지 않는 상품번호 입니다. 다시 입력해주세요");
			}else break; //제대로 입력하면 while문 빠져나옴
		}
		int rNumIndex = rNum.get(num-1); //진짜 상품번호 배열에서 값을 가져와 rNumIndex에 저장
        //삭제할 상품번호의 상품이름 가져옴
        String name2 = list.get(rNumIndex).getName();
        System.out.println("["+name2+"]"+"에 등록된 리뷰 ▼");
        //상품의 리뷰 출력
        show = true;
        showReview(reviews, list.get(rNumIndex), show);
        int reviewNum;
        while(true) {
            System.out.print("삭제할 리뷰의 번호를 입력하세요 > ");
            reviewNum = sc.nextInt();
            //리뷰 객체 배열에서 삭제할 리뷰 객체를 선택, 그 객체의 리뷰 목록 크기보다 삭제할 리뷰 번호가 크거나 0인 경우
            if(reviewNum > reviews.get(num-1).getReview().size() || reviewNum == 0) {
            	System.out.println("존재하지 않는 리뷰 번호 입니다. 다시 입력해주세요");
            }else break;
        }
        while(true) {
			System.out.print("정말 삭제하시겠습니까? (1:y / 2:n) > ");
			int con = sc.nextInt();
			if (con == 1) {
				//리뷰 삭제 과정
				// 삭제할 리뷰 객체의 리뷰 목록 배열을 r에 가져옴
				ArrayList<String> r = reviews.get(num - 1).getReview();
				// 삭제할 리뷰 번호 String으로 형변환 후 num2에 넣음
				String num2 = reviewNum + "";
				// 리뷰 목록 배열 안 각각의 요소들을 re에 담아서 확인하는걸 반복
				for (String re : r) {
					// 각 요소의 숫자 반환( 예) 2 강아지 : 하이 에서 2을 반환해서 delNum에 넣음 )
					String delNum = re.substring(0, 1);
					if (num2.equals(delNum)) { // 삭제할 리뷰 번호와 각 요소의 숫자가 같을 경우(=삭제해야하는 요소)
						r.remove(re); // 요소 삭제
						System.out.println("리뷰가 삭제되었습니다!");
						
						// 번호 앞당기기
						int del = Integer.parseInt(delNum); // delNum(삭제한 요소 숫자)을 int로 형변환하고 del에 넣음
						if (del <= r.size()) { // 삭제한 요소의 숫자가 리뷰 객체의 리뷰 목록 배열의 크기보다 작거나 같을 경우
							/*
							 * ▼ (예시) 리뷰 객체의 리뷰 목록 배열에 들어있는 요소들 
							 * { 1 고양이 : 안녕, 2 강아지 : 하이, 3 병아리 : 헬로, 4 여우 :오하요 } 
							 * 여기서 2번을 삭제하면 
							 * { 1 고양이 : 안녕, 3 병아리 : 헬로, 4 여우 : 오하요 } 
							 * 삭제한 요소의 숫자는 2,리뷰 목록 크기는 3 
							 * del <= r.size() <= 참, 3번과 4번의 숫자를 앞당겨서 1 2 3 순서를 맞춰야됨 (만약 저기서 마지막
							 * 4번을 삭제하면 삭제한 요소의 숫자는 4, 리뷰 목록 크기는 3 으로 조건이 거짓이됨, 밑으로 내려가서 카운트값만 -1해주면 됨)
							 * 
							 */
							for (int j = del; j <= r.size(); j++) { // 삭제한 요소의 숫자부터 시작해서 리뷰 목록 배열 크기만큼 반복
								int index = j - 1; // 삭제한 요소의 인덱스 번호로 옮겨진 요소를 가져오기 위해 -1해줌
								String newValue = r.get(index);
								newValue = newValue.substring(2);
								String c = j + "";
								/*
								 *                       인덱스 
								 *     [0]               [1]         [2] 
								 * { 1 고양이 : 안녕, 3 병아리 : 헬로, 4 여우 : 오하요 } 
								 * 삭제한 요소의 숫자=2, 리뷰목록 크기=3 
								 * 183라인, r.get(index); => 인덱스 1번의 값 반환 => 3 병아리 : 헬로 
								 * 184라인, newValue.substring(2); -> 문자열의 인덱스 2번부터 끝까지 반환 => 병아리 : 헬로 
								 * newValue = "병아리 : 헬로" <- 이상태가 됨
								 * 185라인, 기존 3 병아리 : 헬로 의 3부분을 2로 변경하기 위해 j를 String으로 형변환 후 c에 넣음
								 * 
								 */
								// 덮어쓰기
								// r.set(인덱스 번호, 덮어쓸 내용)
								// 리뷰 목록 배열의 1번째 인덱스의 내용을 2 병아리 : 헬로 로 변경
								r.set(index, c + " " + newValue);
							}
							reviews.get(num - 1).decCnt(); // 리뷰 하나를 삭제했으니 카운트 값도 -1 해줌

						} else {
							reviews.get(num - 1).decCnt();
						}
						break;
					}
				}
				/*
				 * 리뷰가 들어있었는데 삭제를 반복해 결국엔 그 리뷰 객체의 카운트가 0이 되면 리뷰가 하나도 없는 상태이기 때문에 그 리뷰 객체를 삭제
				 */
				if (reviews.get(num - 1).getCnt() == 0) {
					reviews.remove(num - 1);
				}
				break;
			} else if (con == 2) {
				System.out.println("리뷰 삭제를 취소합니다.");
				break;
			//1,2 외에 다른 숫자 입력했을경우
			} else {
				System.out.println("다시 입력해주세요");
				continue;
			}
        }
	}
}