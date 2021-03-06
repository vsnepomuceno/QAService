package br.ufpe.cin.eseg.qaservice.model.repositories.util;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import br.ufpe.cin.eseg.qaservice.model.entities.Paper;
import br.ufpe.cin.eseg.qaservice.util.LoggerQAS;

@Configuration
@Import(PropertyPlaceholderConfig.class)
@ComponentScan({ "br.ufpe.cin.eseg.qaservice", 
				 "br.ufpe.cin.eseg.qaservice.model",
				 "br.ufpe.cin.eseg.qaservice.model.service"})
@EnableJpaRepositories("br.ufpe.cin.eseg.qaservice.model.repositories")
@EnableTransactionManagement
public class PersistenceContext {

	@Value("${db.JdbcUrl}")
	private String databaseUrl;
	@Value("${db.user}")
	private String databaseUser;
	@Value("${db.password}")
	private String databasePassword;
	@Value("${db.driver}")
	private String driver;
	
	@Bean
	public DataSource dataSource() {

		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			// Connect Configuration
			Class.forName(driver);
			dataSource.setDriverClass(driver);
			dataSource.setJdbcUrl(databaseUrl);
			dataSource.setUser(databaseUser);
			dataSource.setPassword(databasePassword);

			// Test Configuration
			dataSource.setIdleConnectionTestPeriod(300);
			dataSource.setPreferredTestQuery("SELECT 1;");
			dataSource.setTestConnectionOnCheckout(true);
			dataSource.setMaxIdleTime(1800);
		} catch (PropertyVetoException | ClassNotFoundException e) {
			LoggerQAS.getLoggerInstance().logError(e.getMessage());
		}
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.MYSQL);
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(false);

		HibernateJpaDialect jpaDialect = new HibernateJpaDialect();

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan(Paper.class.getPackage().getName());
		factory.setDataSource(dataSource());
		factory.setJpaDialect(jpaDialect);
		factory.setBeanName("entityManagerFactory");

		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}
}
