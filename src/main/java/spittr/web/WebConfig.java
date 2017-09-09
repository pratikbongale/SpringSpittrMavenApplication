package spittr.web;

/*
this is the class that will contain configuration of beans
which will be used by dispatcher servlet for handling requests
The beans containing web components such as controllers, view resolvers, and handler mappings
 */

/*
@Configuration
Indicates that a class declares one or more @Bean methods and may be processed by the Spring container
to generate bean definitions and service requests for those beans at runtime
 */

/*
@EnableWebMvc
This is the main class providing the configuration behind the MVC Java config
it registers HandlerMappings, HandlerAdapters, HandlerExceptionResolvers
Ex. RequestMappingHandlerMapping - for mapping requests to annotated controller methods, it
Creates RequestMappingInfo instances from type and method-level @RequestMapping annotations in @Controller classes
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebMvc
@ComponentScan("spittr.web")   // scan components from this package
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    // now we modify the default config
    // we need ViewResolvers and need to handle static resources

    // Commenting this block because now practicing thymeleaf
//    @Bean
//    public ViewResolver viewResolver() {
//        /*
//         it's good practice to put JSP files that just serve as views under WEB-INF,
//         to hide them from direct access (e.g. via a manually entered URL).
//         Only controllers will be able to access them then.
//         */
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/views/");
//        resolver.setSuffix(".jsp");
//
//        /*
//        Set whether to make all Spring beans in the application context accessible as request attributes
//        This will make all such beans accessible in plain ${...} expressions in a JSP 2.0 page
//         */
//        resolver.setExposeContextBeansAsAttributes(true);
//        return resolver;
//    }

    /*
    now configure how should server handle static pages.
    by default it will ask our dispatcherservlet to resolve it, but thats unnecessary load
     */

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        /*
        Configures a request handler for serving static resources
        by forwarding the request to the Servlet container's "default" Servlet.
        This is intended to be used when the Spring MVC DispatcherServlet is mapped to "/"
        thus overriding the Servlet container's default handling of static resources.

        you’re asking DispatcherServlet to forward requests for static resources to
        the servlet container’s default servlet and not to try to handle them itself.
         */
        configurer.enable();
    }

    // used by templateResolver for thymeleaf templates
    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    /*
    ThymeleafViewResolver <- SpringTemplateEngine <- SpringResourceTemplateResolver
     */
    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    /*
    SpringTemplateEngine establishes an instance of SpringStandard Dialect.
    SpringStandard Dialect. This is the class containing the implementation of Thymeleaf Standard Dialect,
    including all th:* processors, expression objects(@,$ ..), etc. for Spring-enabled environments.
    */
    @Bean
    public TemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public ITemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);    // html5
        return resolver;
    }


}
