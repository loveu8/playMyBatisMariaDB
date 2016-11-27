package test.db;

import org.apache.ibatis.session.SqlSessionManager;

import com.google.inject.Guice;
import com.google.inject.Injector;

import services.WebService;

public class FooLocalDAO extends AbstractFooDAO{

    public FooLocalDAO(){
      Injector injector = Guice.createInjector(new modules.MyBatisModule());
      sqlSessionManager = injector.getInstance(SqlSessionManager.class);
      webService = injector.getInstance(WebService.class);
      fooService = injector.getInstance(FooService.class);
    }
 
    public static void main(String[] args) {
      FooLocalDAO dao = new FooLocalDAO();
      System.out.println("----------------------------------------------");
      System.out.println("Test SessionManager rollback");
      System.out.println("error message = " + dao.testErrorWithSessionManager());
      System.out.println("----------------------------------------------");
      System.out.println("Test Annation Transaction rollback , and it work.");
      try {
        dao.testErrorWithAnnotationTransation();
      } catch (Exception e){
        e.printStackTrace();
        System.out.println("error message = " + e.getMessage());
      }
    }
    
    
}