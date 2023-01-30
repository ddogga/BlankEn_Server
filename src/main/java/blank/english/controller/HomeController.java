package blank.english.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class HomeController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String home() {
        log.info("home controller");
        return "hello";
    }
}
