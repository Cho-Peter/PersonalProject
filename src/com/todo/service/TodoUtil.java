package com.todo.service;

import java.util.*;
import java.util.HashSet;
import java.util.Iterator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;



import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, category, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[ 할일 추가 ]\n"
				+ "할일의 제목: ");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복되었습니다. 제목이 중복될 수 없습니다.");
			return;
		}
		
		sc.nextLine();
		System.out.print("카테고리: ");
		category = sc.nextLine();
		
		sc.nextLine();
		System.out.print("세부내용: ");
		desc = sc.nextLine();
		
		sc.nextLine();
		System.out.print("제출기한: ");
		due_date = sc.nextLine().trim();
		
		System.out.print("제출기한의 요일: ");
		String day = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, category, desc, due_date, day);
		if(list.addItem(t) > 0) {
			System.out.println("할일이 추가되었습니다.");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("\n"
				+ "[ 할일 삭제 ]\n"
				+ "삭제할 할일의 번호: ");
		
		int num = sc.nextInt();
		if(l.deleteItem(num) > 0) {
					System.out.println("삭제되었습니다.");
		}
	}
	
	public static void deleteItems(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		
		System.out.print("\n"
				+ "[ 할일 삭제(동시) ]\n"
				+ "삭제할 횟수: ");
		
		int num = sc.nextInt();
		
		for(int i=0; i<num; i++) {
			System.out.print("삭제할 번호: ");
			int index = sc.nextInt();
			if(l.deleteItem(index) > 0) {
				System.out.println("삭제되었습니다.");
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "[ 할일 수정 ]\n"
				+ "수정할 할일의 번호: ");
		
		int num = sc.nextInt();
		
		sc.nextLine();
		System.out.print("새로운 제목: ");
		String new_title = sc.nextLine();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복되었습니다. 제목이 중복될 수 없습니다.");
			return;
		}
		
		sc.nextLine();
		System.out.print("새로운 카테고리: ");
		String new_category = sc.nextLine();
		
		
		sc.nextLine();
		System.out.print("새로운 세부내용: ");
		String new_description = sc.nextLine();
		
		sc.nextLine();
		System.out.print("새로운 제출기한: ");
		String new_due_date = sc.nextLine().trim();
		
		System.out.print("새로운 제출기한 요일: ");
		String new_day = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_title, new_category, new_description, new_due_date, new_day);
		t.setId(num);
		
		if(l.updateItem(t)>0) {
			System.out.println("할일이 수정되었습니다.");
		}
	}
	
	public static void findList(TodoList l, String key_word) {
		int count = 0;
		for(TodoItem item : l.getList(key_word)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.", count);
		System.out.println("");
	}

	public static void findListCate(TodoList l, String key_cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(key_cate)){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.", count);
		System.out.println("");
	}
	
	public static void listCate(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()){
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("총 %d개의 카테고리가 등록되어 있습니다.", count);
		System.out.println("");
		
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록], 총 %d개\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering){
		System.out.printf("[전체 목록], 총 %d개\n", l.getCount());
		for(TodoItem item : l.getOrderedList(orderby, ordering)){
			System.out.println(item.toString());
		}
	}
	
	public static void comp(TodoList l, int key){
		Scanner sc = new Scanner(System.in);
		char cheak;
		if(l.completeItem(key) > 0) {
			System.out.println("완료체크 했습니다.");
			System.out.print("완료한 항목에 대해 피드백 하시겠습니까? (Y/N): ");
			cheak = sc.next().charAt(0);
			if (cheak == 'Y') {
				feedback(l, key);
			}
		}
	}
	
	public static void comps(TodoList l, int num){
		Scanner sc = new Scanner(System.in);
		char cheak;
		for(int i=0; i<num; i++) {
			System.out.println("완료체크 번호: ");
			int key = sc.nextInt();
			if(l.completeItem(key) > 0) {
				System.out.println("완료체크 했습니다.");
				System.out.print("완료한 항목에 대해 피드백 하시겠습니까? (Y/N): ");
				cheak = sc.next().charAt(0);
				if (cheak == 'Y') {
					feedback(l, key);
				}
			}
		}
	}
	
	public static void feedback(TodoList l, int key) {
		Scanner sc = new Scanner(System.in);

		String feedback;

		System.out.println("피드백 내용: ");
		feedback = sc.nextLine();

		if(l.feedbackItem(feedback, key) > 0) {
			System.out.println("피드백 항목에 입력을 완료했습니다.");
		}
	}
	

	public static void ls_feed(TodoList l){
		int count = 0;
		for(TodoItem item : l.getFeedbackItems()){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개를 피드백이 있습니다.", count);
		System.out.println("");
	}

	public static void ls_comp(TodoList l){
		int count = 0;
		for(TodoItem item : l.getCompletedItems()){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개를 완료했습니다.", count);
		System.out.println("");
	}
	
	public static void ls_day(TodoList l) {
		String day;
		int count;
		
		count = 0;
		day = "sun";
		System.out.println("[일요일 리스트]");
		for(TodoItem item : l.getDay(day)){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("일요일 할일이 총 %d개 있습니다.\n", count);
		System.out.println("");
		
		count = 0;
		day = "mon";
		System.out.println("[월요일 리스트]");
		for(TodoItem item : l.getDay(day)){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("월요일 할일이 총 %d개 있습니다.\n", count);
		System.out.println("");
		
		count = 0;
		day = "tue";
		System.out.println("[화요일 리스트]");
		for(TodoItem item : l.getDay(day)){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("화요일 할일이 총 %d개 있습니다.\n", count);
		System.out.println("");
		
		count = 0;
		day = "wed";
		System.out.println("[수요일 리스트]");
		for(TodoItem item : l.getDay(day)){
			System.out.println(item.toString());
			count++;
		}
		
		System.out.printf("수요일 할일이 총 %d개 있습니다.\n", count);
		System.out.println("");
		count = 0;
		day = "thu";
		System.out.println("[목요일 리스트]");
		for(TodoItem item : l.getDay(day)){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("목요일 할일이 총 %d개 있습니다.\n", count);
		System.out.println("");
		
		count = 0;
		day = "fri";
		System.out.println("[금요일 리스트]");
		for(TodoItem item : l.getDay(day)){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("금요일 할일이 총 %d개 있습니다.\n", count);
		System.out.println("");
		
		count = 0;
		day = "sat";
		System.out.println("[토요일 리스트]");
		for(TodoItem item : l.getDay(day)){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("토요일 할일이 총 %d개 있습니다.\n", count);
		System.out.println("");
	}
	
	public static void ls_day(TodoList l, String day) {
		int count = 0;
		char ch = ' ';
		switch(day) {
		case "sun":
			ch = '일';
			break;
			
		case "mon":
			ch = '월';
			break;
			
		case "tue":
			ch = '화';
			break;

		case "wed":
			ch = '수';
			break;
		
		case "thu":
			ch = '목';
			break;

		case "fri":
			ch = '금';
			break;

		case "sat":
			ch = '토';
			break;

		}
		System.out.printf("[%c요일 리스트]\n", ch);
		for(TodoItem item : l.getDay(day)){
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("%c요일 할일이 총 %d개 있습니다.\n", ch, count);
		System.out.println("");
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			String oneline;
			int i = 0;
			while((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline,"##");
				String category = st.nextToken();
				String title = st.nextToken();
				String desc = st.nextToken();
				String due_date = st.nextToken();
				String date = st.nextToken();
				String day = st.nextToken();
				
				TodoItem t = new TodoItem(title, category, desc, due_date, day);
				t.setCurrent_date(date);
				l.addItem(t);
				i++;
			}
			br.close();
			System.out.println(i + "개의 항목을 읽었습니다.");
			
		} catch (FileNotFoundException e) {
			System.out.println("todolist.txt 파일이 없습니다.");
			//e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
