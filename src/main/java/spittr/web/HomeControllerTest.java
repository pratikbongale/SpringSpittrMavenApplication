package spittr.web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import spittr.Spittle;
import spittr.data.SpittleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

    @Test
    public void shouldShowRecentSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittleList(20);

        // use mockito's mock method to mock the repo
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findSpittles(Long.MAX_VALUE,20))
                .thenReturn(expectedSpittles);
        // inject mock repository in controller
        SpittleController controller =
                new SpittleController(mockRepository);
        /*
        this test calls setSingleView() on the MockMvc builder.
        This is so the mock framework won’t try to resolve the view name
        coming from the controller on its own. In many cases, this is unnecessary. But for this
        controller method, the view name will be similar to the request’s path; left to its
        default view resolution, MockMvc will fail because the view path will be confused with
        the controller’s path. The actual path given when constructing the Internal-
        ResourceView is unimportant in this test, but you set it to be consistent with how
        you’ve configured InternalResourceViewResolver.
         */
        MockMvc mockMvc = standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();

        mockMvc.perform(get("/spittles"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList", hasItems(expectedSpittles.toArray())));

    }

    private List<Spittle> createSpittleList(int count) {
        List<Spittle> res = new ArrayList<Spittle>();
        for (int i = 1; i <= count; i++) {
            res.add(new Spittle("Spittle : " + i, new Date()));
        }
        return res;
    }

    @Test
    public void shouldShowPagedSpittles() throws Exception {
        List<Spittle> expectedSpittles = createSpittleList(50);

        // use mockito's mock method to mock the repo
        SpittleRepository mockRepository = mock(SpittleRepository.class);
        when(mockRepository.findSpittles(238900,50))
                .thenReturn(expectedSpittles);
        // inject mock repository in controller
        SpittleController controller =
                new SpittleController(mockRepository);

        MockMvc mockMvc = standaloneSetup(controller).setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp")).build();

        mockMvc.perform(get("/spittles?max=238900&count=50"))
                .andExpect(view().name("spittles"))
                .andExpect(model().attributeExists("spittleList"))
                .andExpect(model().attribute("spittleList",
                        hasItems(expectedSpittles.toArray())));
    }
}
