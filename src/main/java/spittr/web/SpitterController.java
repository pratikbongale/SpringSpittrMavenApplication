package spittr.web;

/*
Controller to handle GET request sent by the users who wish to register
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spittr.Spitter;
import spittr.data.SpitterRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

    SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegistrationForm() {
        return "registerForm";
    }

    // ideally to avoid resubmission by page refreshes, we redirect the user to his/her profile page
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String postRegistrationForm(@Valid Spitter spitter, Errors errors) {

        if(errors.hasErrors())
            return "/registerForm";

        spitterRepository.save(spitter);
        return "redirect:/spitter/" + spitter.getUsername();
    }

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public String showSpitterProfile(@PathVariable("username") String name, Model model) {
        Spitter spitter = spitterRepository.findByUsername(name);
        model.addAttribute(spitter);    // this obj can be used by jsp using {spitter.username}
        return "profile";
    }
}
