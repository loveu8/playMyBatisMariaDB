package modules;

import java.io.InputStream;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import play.Configuration;
import play.db.Database;
import play.db.Databases;
import services.UserService;
import services.WebService;

public class MyBatisModule extends org.mybatis.guice.MyBatisModule  {

    @Override
    protected void initialize() {
        environmentId("default");
        bindConstant().annotatedWith(
                Names.named("mybatis.configuration.failFast")).
                to(true);
        bindDataSourceProviderType(PlayDataSourceProvider.class);
        bindTransactionFactoryType(JdbcTransactionFactory.class);
        // 把我們要呼叫DB的類別，增加到MapperClass
        addMapperClass(UserService.class);
        addMapperClass(WebService.class);
    }

	// 產出一個單例模式的連線
    @Singleton
    public static class PlayDataSourceProvider implements Provider<DataSource> {
        
    	final Database db;
       
        private Configuration configuration;

        /*
        public PlayDataSourceProvider(){
    		
        	String dataBaseName = "";
        	String driver		= "";
        	String url			= "";
        	String user			= "";
        	String password		= "";

        	try {
        		ClassLoader classLoader = modules.MyBatisModule.class.getClassLoader();
				Config config = ConfigFactory.load(classLoader);
				configuration = new Configuration(config);

		
	        	dataBaseName = "play";
	        	driver		= configuration.getString("db.play.driver");
	        	url			= configuration.getString("db.play.url");
	        	user		= configuration.getString("db.play.user");
	        	password	= configuration.getString("db.play.password");
	        	
	        	System.out.println("driver = " + driver);
	        	System.out.println("url = " + url);
	        	System.out.println("user = " + user);
	        	System.out.println("password = " + password);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	// 建立Database物件
        	Database database = Databases.createFrom(
        			dataBaseName,
        			driver,
        			url,
        	        ImmutableMap.of(
        	        		"user", user,
        	                "password", password
        	        )
        	);
            this.db = database;
        }
        */
        
        
        @Inject
        public PlayDataSourceProvider(Configuration config) {
        	        	
        	this.configuration = config;
        	     	
        	// 取得conf檔案 資料庫設定
        	String dataBaseName = "play";
        	String driver		= configuration.getString("db.play.driver");
        	String url			= configuration.getString("db.play.url");
        	String user			= configuration.getString("db.play.user");
        	String password		= configuration.getString("db.play.password");

        	// 建立Database物件
        	Database database = Databases.createFrom(
        			dataBaseName,
        			driver,
        			url,
        	        ImmutableMap.of(
        	        		"user", user,
        	                "password", password
        	        )
        	);
            this.db = database;
        }
        
        
        @Override
        public DataSource get() {
            return db.getDataSource();
        }
        
    }
	
    
}
