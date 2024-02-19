package project_5;

import java.util.ArrayList;

public class Review {
	private String name; //상품명
	private ArrayList<String> reviews = new ArrayList<>(); //리뷰 목록
	private int cnt; //카운트
	
	public Review(String name, int cnt) {
		this.name = name;
		this.cnt = cnt;
	}
	public ArrayList<String> getReview(){
		return reviews;
	}
	public void setReview(String review) {
		reviews.add(review);
	}
	public String getName() {
		return name;
	}
	public int getCnt() {
		return cnt;
	}
	public void countCnt() {
		cnt++;
	}
	public void decCnt() {
		cnt--;
	}
	@Override
	public String toString() {
		return "Review [name=" + name + ", reviews=" + reviews + "]";
	}

	
	
}