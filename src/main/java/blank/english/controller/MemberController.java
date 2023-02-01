package blank.english.controller;


import blank.english.domain.Member;
import blank.english.domain.Role;
import blank.english.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @RequestMapping(value = "/members/new", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody MemberForm form, BindingResult result) {

        //문제가 생기면 hasErrors가 True가 됨.
        if(result.hasErrors()) { return ""; }
        Member member = new Member(form.getUserName(),form.getPassword(), form.getEmail(), Role.USER);

        return memberService.join(member);
    }
}
