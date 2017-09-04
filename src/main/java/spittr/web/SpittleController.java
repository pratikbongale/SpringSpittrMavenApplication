package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import spittr.Spittle;
import spittr.data.SpittleRepository;

import java.util.List;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

    private SpittleRepository spittleRepository;

    @Autowired
    public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    /*
    Java-5-specific interface that defines a holder for model attributes.
    Primarily designed for adding attributes to the model.
    Allows for accessing the overall model as a java.util.Map.
     */
//    @RequestMapping(method = RequestMethod.GET)
//    public String getSpittles(Model model) {
//
//        /*
//        Here we we havent given the key, it's inferred from the type of object being set as the value.
//        In this case, because it’s a List<Spittle>, the key will be inferred as spittleList.
//         */
//        model.addAttribute(spittleRepository.findSpittles(Long.MAX_VALUE,20));
//        return "spittles";
//    }

    /*
    we can also return the any collection directly(change the return type accordingly) as:
    @RequestMapping(method=RequestMethod.GET)
    public List<Spittle> spittles() {
        return spittleRepository.findSpittles(Long.MAX_VALUE, 20));
    }
    In this case the view that will handle this request will be
    infered from the request (/spittles -> "spittles")
     */


    private static final String MAX_VALUE_AS_STRING = Long.MAX_VALUE+"";
    // Now we add pagination by query parameters
    // 2 parameters :
    // 1. before: ID of the spittle upto which all spittles are present
    // @RequestParam("max") Long maxId will work for test shouldShowPagedSpittles()
    // but will fail for shouldShowRecentSpittles() test
    // 2. count: no. of spittles wanted
    @RequestMapping(method = RequestMethod.GET)
    public List<Spittle> getSpittles(@RequestParam(value = "max", defaultValue = MAX_VALUE_AS_STRING ) Long maxId,
                                     @RequestParam(value = "count", defaultValue = "20") int count) {

        return spittleRepository.findSpittles(maxId,count);
    }

    // Now if we are accepting requests by path parameters, we need a variable part to our @Requestmapping

    @RequestMapping(value="/{spittleId}", method = RequestMethod.GET)
    public String getSpittle(@PathVariable("spittleId") Long spittleId, Model model) {
        model.addAttribute(spittleRepository.findOne(spittleId));
        return "spittle";
    }

    // for handling data submitted by forms, neither path variables nor querry parameters are helpful
    // we need our a new controller(SpitterController) to handle a post request

}
