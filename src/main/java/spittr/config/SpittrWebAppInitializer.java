package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
AbstractAnnotationConfigDispatcherServletInitializer - In a Servlet 3.0 environment,
the container looks for any classes in the classpath that implement the javax.servlet
.ServletContainerInitializer interface; if any are found, they’re used to configure
the servlet container.
Spring supplies an implementation of that interface called SpringServlet-
ContainerInitializer that, in turn, seeks out any classes that implement Web-
ApplicationInitializer and delegates to them for configuration. Spring 3.2
introduced a convenient base implementation of WebApplicationInitializer
called AbstractAnnotationConfigDispatcherServletInitializer. Because
your SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcher-
ServletInitializer (and thus implements WebApplicationInitializer), it will
be automatically discovered when deployed in a Servlet 3.0 container and be used to
configure the servlet context.

A Servlet Container/Web Container/App Servers(Ex. Tomcat/Glassfish) are a part of any server,
this part contains your web applications or servlets.
they are helper applications to build dynamic web pages
A web container bundles the request and sends it to servlet

A serv-let - like an app-let, its a server-application.
A user (1) requests some information by filling out a form containing a link to a servlet and clicking the Submit button (2).
The server (3) locates the requested servlet (4).
The servlet then gathers the information needed to satisfy the user's request and constructs a Web page (5)
containing the information. That Web page is then displayed on the user's browser (6).
 */
public class SpittrWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /*
    when DS starts up it creates a appln context and fills it with beans
    containing web components such as controllers, view resolvers, and handler mappings
    by looking at webconfig class
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {WebConfig.class};
    }

    /*
    There is one more appln context created by ContextLoaderListener.
    it loads typically middle-tier and data-tier components that drive the back end of the application.

    Under the covers the class we extended creates both DS and CLL
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
