package ttc.ifish.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"file:application.properties"})
@EnableJpaRepositories(
  basePackages = "ttc.ifish.repositories.report",
  entityManagerFactoryRef = "fishreportEntityManager",
  transactionManagerRef = "fishreportTransactionManager"
)
public class FishReportDataSourceConfiguration {
  @Bean(name = "fishreportDatasource")
  @ConfigurationProperties(prefix = "spring.report.datasource")
  public DataSource reportDatasource(){
    return DataSourceBuilder.create().build();
  }

  @Bean(name ="fishreportEntityManager" )
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                     @Qualifier("fishreportDatasource") DataSource dataSource) {

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("hibernate.hbm2ddl.auto", "none");
    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

    return builder.dataSource(dataSource)
      .properties(properties)
      .packages("ttc.ifish.models.entities.report")
      .build();
  }


  @Bean(name = "fishreportTransactionManager")
  public PlatformTransactionManager transactionManager(
    @Qualifier("fishreportEntityManager") EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }


}
