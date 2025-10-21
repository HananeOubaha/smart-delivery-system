package com.smartlogi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "com.smartlogi") // Scanne tous nos packages
@EnableJpaRepositories(basePackages = "com.smartlogi.repository") // Active Spring Data JPA
@EnableTransactionManagement // Active la gestion des transactions (@Transactional)
public class AppConfig {

    // 1. Bean: Configuration de la connexion à la base de données (DataSource)
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        // === MODIFIEZ CES LIGNES ===
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/smartlogi_db"); // Nom de votre BDD
        dataSource.setUsername("postgres"); // Votre utilisateur PG
        dataSource.setPassword("admin");    // Votre mot de passe PG
        // ==========================
        return dataSource;
    }

    // 2. Bean: Configuration de l'EntityManagerFactory (le cœur de JPA/Hibernate)
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.smartlogi.entity"); // Où trouver nos classes @Entity

        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(hibernateProperties()); // Propriétés d'Hibernate (ci-dessous)

        return em;
    }

    // 3. Bean: Configuration du TransactionManager (pour gérer les @Transactional)
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    // 4. Propriétés spécifiques à Hibernate
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        // Affiche le SQL généré dans la console (pratique pour le débogage)
        properties.setProperty("hibernate.show_sql", "true");
        // Formatage du SQL affiché
        properties.setProperty("hibernate.format_sql", "true");
        // "hibernate.hbm2ddl.auto" :
        //    "create" : supprime et recrée les tables à chaque démarrage
        //    "update" : tente de mettre à jour les tables si les entités changent
        //    "validate" : valide juste que le schéma correspond
        //    "none" : ne fait rien
        // Utilisez "create" ou "update" pour le développement
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }
}