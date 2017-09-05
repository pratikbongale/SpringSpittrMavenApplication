package spittr.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
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

    @Bean
    public DataSource pooledDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(this.env.getProperty("spring.datasource.driver"));
        ds.setUrl(this.env.getProperty("spring.datasource.url"));
        ds.setUsername(this.env.getProperty("spring.datasource.username"));
        ds.setPassword(this.env.getProperty("spring.datasource.password"));
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
