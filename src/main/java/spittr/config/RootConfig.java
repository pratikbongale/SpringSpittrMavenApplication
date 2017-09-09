package spittr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.regex.Pattern;

/*
The exclude filters will exclude those classes annotated with EnableWebMvc
while searching for beans. (its in our web.config file)
 */

@Configuration
@Import(DataSourceConfig.class)
@ComponentScan(basePackages = {"spittr"}, excludeFilters={
        @ComponentScan.Filter(type=FilterType.CUSTOM, value=RootConfig.WebPackage.class)})
public class RootConfig {

    // exclude the entire spittr.web package, along with the WebConfig file
    public static class WebPackage extends RegexPatternTypeFilter {
        public WebPackage() {
            super(Pattern.compile("spittr\\.web"));
        }
    }
}
