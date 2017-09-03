package spittr.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/*
Starting with Spring 3.2, however, you have a way to test Spring MVC controllers as
controllers, not merely as POJOs. we now have the mechanics of Spring MVC and executing
HTTP requests against controllers
 */
public class HomeControllerTest {

    @Test
    public void testHome() throws Exception {
        HomeController homeController = new HomeController();

        MockMvc mockMvc = standaloneSetup(homeController).build();

        mockMvc.perform(get("/"))
                .andExpect(view().name("home"));

        assertEquals("home", homeController.home());
    }
}
