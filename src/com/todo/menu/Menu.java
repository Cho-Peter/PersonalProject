package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println("TodoList 기본 메뉴");
        System.out.println("할일 추가 ( add )");
        System.out.println("할일 삭제 ( del )");
        System.out.println("할일 삭제(동시) ( dels )");
        System.out.println("할일 수정  ( edit )");
        System.out.println("완료 체크 ( comp )");
        System.out.println("완료 체크(동시) ( comps )");
        System.out.println("할일 제목과 내용으로 찾기 ( find <키워드> )");
        System.out.println("할일 카테고리로 찾기 ( find_cate <키워드> )");
        System.out.println("할일 요일로 찾기 ( find_day <키워드> )");
        System.out.println("할일 보기 ( ls )");
        System.out.println("제목 순으로 정렬 ( ls_name_asc )");
        System.out.println("제목 역순으로 정렬 ( ls_name_desc )");
        System.out.println("날짜 순으로 정렬 ( ls_date )");
        System.out.println("날짜 역순으로 정렬 ( ls_date_desc )");
        System.out.println("카테고리 목록 보기 ( ls_cate )");
        System.out.println("요일별로 보기 ( ls_day )");
        System.out.println("피드백한 할일 보기 ( ls_feed )");
        System.out.println("완료된 할일 보기 ( ls_comp )");
        System.out.println("종료 (exit)");
    }
    public static void prompt() {
        System.out.println("사용할 메뉴를 입력하시오 >");
    }
}
