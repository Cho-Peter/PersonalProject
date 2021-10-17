package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;
 //
public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todolist.txt");
		
		boolean quit = false;
		

		Menu.displaymenu();
		do {
			Menu.prompt();
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
			
			case "dels":
				TodoUtil.deleteItems(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				TodoUtil.listAll(l, "title", 1);
				System.out.println("제목순으로 정렬했습니다.");
				break;

			case "ls_name_desc":
				TodoUtil.listAll(l, "title", 0);
				System.out.println("제목역순으로 정렬했습니다.");
				break;
				
			case "ls_date":
				TodoUtil.listAll(l, "due_date", 1);
				System.out.println("날짜순으로 정렬했습니다.");
				break;
				
			case "ls_date_desc":
				TodoUtil.listAll(l, "due_date", 0);
				System.out.println("날짜역순으로 정렬했습니다.");
				break;
				
			case "find":
				String key_word = sc.nextLine().trim();
				TodoUtil.findList(l,key_word);
				break;
				
			case "find_cate":
				String key_cate = sc.nextLine().trim();
				TodoUtil.findListCate(l, key_cate);
				break;
				
			case "find_day":
				String day = sc.nextLine().trim();
				TodoUtil.ls_day(l, day);
				break;
				
			case "ls_cate":
				TodoUtil.listCate(l);
				break;
				
			case "comp":
				int key = sc.nextInt();
				TodoUtil.comp(l, key);
				break;
				
			case "comps":
				int num = sc.nextInt();
				TodoUtil.comps(l,num);
				break;
				
			case "ls_comp":
				TodoUtil.ls_comp(l);
				break;
				
			case "ls_feed":
				TodoUtil.ls_feed(l);
				break;
			
			case "ls_day":
				TodoUtil.ls_day(l);
				break;
				
			case "help":
				Menu.displaymenu();
				break;
				
			case "exit":
				quit = true;
				break;

			default:
				System.out.println("정확한 명령를 입력하세요. (자세한 도움말 - help)");
				break;
			}
		} while (!quit);
	}
}
