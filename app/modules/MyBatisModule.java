package modules;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.name.Names;

import play.Configuration;
import play.db.Database;
import play.db.Databases;
import services.UserService;

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
    }

	// 產出一個單例模式的連線
    @Singleton
    public static class PlayDataSourceProvider implements Provider<DataSource> {
        
    	final Database db;
       
        private Configuration configuration;

        @Inject
        public PlayDataSourceProvider(Configuration config) {
        	this.configuration = config;
        	Database database = Databases.createFrom(
        	        "play",
        	        configuration.getString("db.play.driver"),
        	        configuration.getString("db.play.url"),
        	        ImmutableMap.of(
        	                "user", configuration.getString("db.play.user"),
        	                "password", configuration.getString("db.play.password")
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
