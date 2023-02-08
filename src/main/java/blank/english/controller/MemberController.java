package blank.english.controller;


import blank.english.domain.Member;
import blank.english.domain.Role;
import blank.english.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @RequestMapping(value = "/members/new", method = RequestMethod.POST)
    public String create(@RequestBody MemberForm form, BindingResult result) {

        //문제가 생기면 hasErrors가 True가 됨.
        if(result.hasErrors()) { return ""; }
        Member member = new Member(form.getUserName(),form.getNickname(),form.getPassword(), form.getEmail(), Role.USER);

        return memberService.join(member);
    }

    @RequestMapping(value = "/members/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) throws IOException {

        log.info("로그아웃");
        session.invalidate();

        return "logout";

    }
}
