package br.com.altamira.monitoramento;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableJms
@EnableWebSocket
@EnableTransactionManagement
@ComponentScan("br.com.altamira.monitoramento")
//@EnableConfigurationProperties
@EntityScan(basePackages = "br.com.altamira.monitoramento.model")
@EnableJpaRepositories(transactionManagerRef = "TransactionManager", entityManagerFactoryRef = "EntityManagerFactory", basePackages = "br.com.altamira.monitoramento.repository")
public class Application {

	@Bean
	ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory(new ActiveMQConnectionFactory(
				"tcp://192.168.0.211:61616"));
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
	/*@Primary
	@Bean(name = "DataSource")
	@ConfigurationProperties(prefix = "datasource")
	public DataSource DataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean EntityManagerFactory(
			final EntityManagerFactoryBuilder builder) {
		return builder.dataSource(DataSource()).build();
	}

	@Primary
	@Bean(name = "TransactionManager")
	public JpaTransactionManager TransactionManager(
			@Qualifier("entityManagerFactory") final EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}*/
}
