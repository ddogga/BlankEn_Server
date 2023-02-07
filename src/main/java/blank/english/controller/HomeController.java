package blank.english.controller;

import blank.english.config.auth.dto.SessionUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
public class HomeController {

    @RequestMapping(value = "/getSession", method = RequestMethod.GET)
    @ResponseBody
    public SessionUser getSession(HttpSession session) {
        SessionUser user = (SessionUser) session.getAttribute("user");
        return user;
    }
}
