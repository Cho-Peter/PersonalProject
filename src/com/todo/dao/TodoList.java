package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.ArrayList;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByName;
import com.todo.service.DbConnect;


public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn = DbConnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date, day)" + " values (?,?,?,?,?,?);";
		PreparedStatement pstmt;
		int count = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getDay());
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}
	

	public int getCount(){
		Statement stmt;
		int count = 0;
		try{
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title = ?, memo = ?, category = ?, current_date = ?, due_date = ?, is_completed = ?, is_feedback = ?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setInt(6, 0);
			pstmt.setString(7, null);
			pstmt.setInt(8, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return count;

	}
	
	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try{
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				String feedback = rs.getString("is_feedback");
				String day = rs.getString("day");
				TodoItem t = new TodoItem(title, category, description, due_date, day);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_Comp(is_comp);
				t.setIs_Feedback(feedback);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try{
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				String is_feedback = rs.getString("is_feedback");
				String day = rs.getString("day");
				TodoItem t = new TodoItem(title, description, category, due_date, day);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_Comp(is_comp);
				t.setIs_Feedback(is_feedback);
				list.add(t);
			}
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try{
			String sql = "SELECT * FROM list WHERE category =  ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				String is_feedback = rs.getString("is_feedback");
				String day = rs.getString("day");
				TodoItem t = new TodoItem(title, description, category, due_date, day);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_Comp(is_comp);
				t.setIs_Feedback(is_feedback);
				list.add(t);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try{
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String category = rs.getString("category");
				list.add(category);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try{
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering == 0) {
				sql += " desc";
			}
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				String feedback = rs.getString("is_feedback");
				String day = rs.getString("day");
				TodoItem t = new TodoItem(title, description, category, due_date, day);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_Comp(is_comp);
				t.setIs_Feedback(feedback);
				list.add(t);
			}
			stmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getCompletedItems(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try{
			stmt = conn.createStatement();
			String sql = "SELECT * from list WHERE is_completed = 1";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				String feedback = rs.getString("is_feedback");
				String day = rs.getString("day");
				TodoItem t = new TodoItem(title, description, category, due_date, day);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_Comp(is_comp);
				t.setIs_Feedback(feedback);
				list.add(t);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public int completeItem(int index){
		String sql = "update list set is_completed = ?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try{
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return count;
	}
	
	public int feedbackItem(String feedback, int index) {
		String sql = "update list set is_feedback = ?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, feedback);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getFeedbackItems(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try{
			stmt = conn.createStatement();
			String sql = "SELECT * from list WHERE is_feedback <> 'null'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				String feedback = rs.getString("is_feedback");
				String day = rs.getString("day");
				TodoItem t = new TodoItem(title, description, category, due_date, day);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_Comp(is_comp);
				t.setIs_Feedback(feedback);
				list.add(t);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getDay(String day){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try{
			String sql = "SELECT * FROM list WHERE day =  ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, day);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_comp = rs.getInt("is_completed");
				String is_feedback = rs.getString("is_feedback");
				String this_day = rs.getString("day");
				TodoItem t = new TodoItem(title, description, category, due_date, this_day);
				t.setId(id);
				t.setCurrent_date(current_date);
				t.setIs_Comp(is_comp);
				t.setIs_Feedback(is_feedback);
				list.add(t);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return list;
	}

	public boolean isDuplicate(String title) {

		boolean re = true;
		PreparedStatement pstmt;
		try{
			String sql = "SELECT * FROM list WHERE title =  ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,title);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next()) re = false;
			pstmt.close();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return re;
	}
	
	public void importData(String filename){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)" + " values (?, ?, ?, ?, ?);";
			
			int records = 0;
			while((line = br.readLine()) != null){
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement ptsmt = conn.prepareStatement(sql);
				ptsmt.setString(1, title);
				ptsmt.setString(2, description);
				ptsmt.setString(3, category);
				ptsmt.setString(4, current_date);
				ptsmt.setString(5, due_date);
				int count = ptsmt.executeUpdate();
				if(count > 0) records++;
				ptsmt.close();
			}
			System.out.println(records + " records read");
			br.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
