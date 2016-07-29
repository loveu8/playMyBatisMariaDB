package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import pojo.scala.Book;
import views.html.book.*;


public class ScalaController extends Controller{
	
	public Result String() {
	    // 這邊單純回傳字串到網頁上
	    return ok("String!!");
	}
	
	public Result oneBook() {
	    Book book = new Book(); 
	    book.setName("被討厭的勇氣");
	    book.setIsbn("9789861371955");
	    return ok(oneBook.render(book));
	}
	
}
