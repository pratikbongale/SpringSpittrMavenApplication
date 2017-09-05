package spittr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/*
The exclude filters will exclude those classes annotated with EnableWebMvc
while searching for beans. (its in our web.config file)
 */

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan(basePackages = {"spittr"}, excludeFilters={
        @ComponentScan.Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})
public class RootConfig {

}
