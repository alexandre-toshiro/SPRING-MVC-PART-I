package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // Habilita a transação no banco.
public class JPAConfiguration {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		// Responsável por criar o Entity Manage Factory.

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		// Adapatador do Hibernate.

		factoryBean.setJpaVendorAdapter(vendorAdapter);
		// Informa que o adaptador será para o Hibernate.

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// Gerencia as configurações de conexão com o banco.

		dataSource.setUsername("root");// User do banco.
		dataSource.setPassword("");// Senha do banco.
		dataSource.setUrl("jdbc:mysql://localhost:3306/casadocodigo?useTimezone=true&serverTimezone=UTC");// URL de
																											// conexão
																											// com o
																											// banco.
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");// Driver de conexão com o banco.

		factoryBean.setDataSource(dataSource);// Joga as configurações no factory bean.

		Properties props = new Properties();// Usamos o properties para setar configurações específicas do hibernate.
		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");// Dialeto que irá conversar com o
																						// banco de dados(MySQL)
		props.setProperty("hibernate.show_sql", "true"); // Nos permite ver o SQL gerado.
		props.setProperty("hibernate.hbm2ddl.auto", "update"); // Toda vez que for alterado o model, o hibernate já irá
																// mudar o banco.

		factoryBean.setJpaProperties(props);// Joga as props no factory bean.

		factoryBean.setPackagesToScan("br.com.casadocodigo.loja.models");
		// Informa aonde estarão as entidades.

		return factoryBean;

	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		// Método responsável por criar/cuidar da transação.
		return new JpaTransactionManager(emf);
	}
}
