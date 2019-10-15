package ttc.ifish.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"file:application.properties"})
@EnableJpaRepositories(
  basePackages = "ttc.ifish.repositories.fishgame",
  entityManagerFactoryRef = "fishgameEntityManager",
  transactionManagerRef = "fishgameTransactionManager")
public class FishGameDataSourceConfiguration {

    @Primary
    @Bean(name = "fishgameDatasource")
    @ConfigurationProperties(prefix = "spring.fishgame.datasource")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Primary
    @Bean(name = "fishgameEntityManager")
    public LocalContainerEntityManagerFactoryBean productEntityManager() {
        LocalContainerEntityManagerFactoryBean em
          = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(userDataSource());
        em.setPackagesToScan(
          new String[]{"ttc.ifish.models.entities.fishgame"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }


    @Primary
    @Bean(name = "fishgameTransactionManager")
    public PlatformTransactionManager transactionManager(
      @Qualifier("fishgameEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
