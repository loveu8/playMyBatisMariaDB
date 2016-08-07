package controllers;

import java.util.ArrayList;
import java.util.HashMap;

import play.mvc.Controller;
import play.mvc.Result;
import pojo.scala.Book;
import views.html.book.*;


public class ScalaController extends Controller{
	
	// 這邊單純回傳字串到網頁上
	public Result String() {
	    return ok("String!!");
	}
	
	// 畫面上寫變數
	public Result varPage(){
		return ok(varPage.render());
	}
	
	// 畫面上寫方法
	public Result method(){
		return ok(method.render());
	}
	
	
	// 99乘法表，計算出結果後放入陣列
	public Result arrayInt(){
		int[][] numbers = new int[10][10];
		for(int row = 1 ; row < 10 ; row ++){
			for(int col = 1 ; col < 10 ; col++){
				numbers[row][col] = row * col;
			}
		}
		return ok(arrayInt.render(numbers));
	}
	
	
	
	// 1本書
	public Result oneBook() {
	    Book book = new Book(); 
	    book.setName("被討厭的勇氣");
	    book.setIsbn("9789861371955");
	    return ok(oneBook.render(book));
	}
	
	// 多本書
	public Result listBook() {
		ArrayList<Book> books = new ArrayList<Book>();
		
	    Book book1 	= new Book(); 
	    Book book2 	= new Book(); 
	    Book book3 	= new Book(); 
	    
	    book1.setName("Docker錦囊妙計");
	    book1.setIsbn("9789864760800");
	    
	    book2.setName("Python 3.5 技術手冊");
	    book2.setIsbn("9789864761265");
	    
	    book3.setName("使用者故事對照｜User Story Mapping");
	    book3.setIsbn("9789863479468");
	    
	    books.add(book1);
	    books.add(book2);
	    books.add(book3);
	    
	    return ok(listBook.render(books));
	}
	
	
	// Map書
	public Result mapBook(){
		HashMap <String , String> books = new HashMap<String , String>();
		books.put("one", "Docker錦囊妙計 - 9789864760800");
		books.put("two", "Python 3.5 技術手冊 - 9789864761265");
		books.put("three", "使用者故事對照｜User Story Mapping - 9789863479468");
		return ok(mapBook.render(books));
	}
	
	
	// 多本書，含有空資料
	public Result listEmptyBook() {
		ArrayList<Book> books = new ArrayList<Book>();
		
	    Book book1 	= new Book(); 
	    Book book2 	= new Book(); 
	    Book book3 	= new Book(); 
	    
	    book1.setName("Docker錦囊妙計");
	    book1.setIsbn("9789864760800");
	    	    
	    book3.setName("使用者故事對照｜User Story Mapping");
	    book3.setIsbn("9789863479468");
	    
	    books.add(book1);
	    books.add(book2);
	    books.add(book3);
	    
	    return ok(listEmptyBook.render(books));
	}
	
	
	// 傳送網頁文字
	public Result showHtml() {
		// 使用H1文字格式，指定內容是Html頁面，編碼使用UTF-8
		return ok("<h1>測試網頁文字!</h1>", "UTF-8").as("text/html; charset=UTF-8");
	}
	
	
	// 在頁面上寫Switch
	public Result testSwitch() {
		return ok(testSwitch.render());
	}
	
	
	// 測試引用靜態檔案
	public Result testStatic() {
		return ok(testStatic.render());
	}
	
	
	// 測試傳送參數到子頁面
	public Result includePage() {
		return ok(mainPage.render("我是子頁面"));
	}
	
	
	// JS主要呼叫頁面
	public Result jsCaller() {
		return ok(jsCaller.render());
	}
	
	// JS呼叫Play的內容
	public Result playCaller() {
		return ok("我被呼叫到了!!");
	}
	
}
