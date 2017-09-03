package spittr.web;

/*
a controller class that handles requests for / and renders
the application’s home page.
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/*
The controller annotation is just so that component scanning can scan this class
and create a bean. it serves no purpose to spring-mvc
 */
@Controller
public class HomeController {

    @RequestMapping(value="/", method=GET)
    public String home() {
        return "home";
    }
}
