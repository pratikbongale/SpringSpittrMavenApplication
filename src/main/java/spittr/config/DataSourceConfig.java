package spittr.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.SQLException;

/*
classpath refers to src/main/resources/
 */
@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig {

    /*
    Retrieving a datasource from JNDI
     */
//    @Bean
//    public DataSource dataSource() {
//        JndiObjectFactoryBean jndiObjectFB = new JndiObjectFactoryBean();
//        jndiObjectFB.setJndiName("jdbc/SpittrDS");
//        jndiObjectFB.setResourceRef(true);
//        jndiObjectFB.setProxyInterface(javax.sql.DataSource.class);
//        return (DataSource) jndiObjectFB;
//    }

    /*
    Configuring a pooled data source using Commons Database Connection Pooling
    from http://commons.apache.org/dbcp/
     */

    @Autowired
    private Environment env;
    private Logger logger = Logger.getLogger(DataSourceConfig.class);

    @Bean
    public DataSource pooledDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(this.env.getProperty("spring.datasource.driver"));
        ds.setUrl(this.env.getProperty("spring.datasource.url"));
        ds.setUsername(this.env.getProperty("spring.datasource.username"));
        ds.setPassword(this.env.getProperty("spring.datasource.password"));
        ds.setInitialSize(5);
        ds.setMaxActive(10);

        logger.info("Datasource configured: " + this.env.getProperty("spring.datasource.url"));
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    // bean to populate the spittrdb with initial tables
    @Bean
    public DatabasePopulator databasePopulator(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(true);
        populator.addScript(new ClassPathResource("schema.sql"));
        logger.info("Data source object created : " + new ClassPathResource("schema.sql").toString());

        try {

            logger.info("Data source connection obtained : " + (dataSource.getConnection()==null?"No":"yes") );
            populator.populate(dataSource.getConnection());
        } catch (SQLException e) {
            logger.error("error in populator : " + e.getLocalizedMessage(), e);
        }
        return populator;
    }
}
