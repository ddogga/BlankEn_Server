package blank.english.controller;


import blank.english.dto.*;
import blank.english.entity.Member;
import blank.english.entity.Role;
import blank.english.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


    @RequestMapping(value = "/members/new", method = RequestMethod.POST)
    public JoinResponseDTO create(@RequestBody JoinForm form, BindingResult result) {
        //문제가 생기면 hasErrors가 True가 됨.
//        if(result.hasErrors()) { return ""; }//예외 생성하기
        Member member = Member.builder()
                .userName(form.getUserName())
                .nickname(form.getNickname())
                .email(form.getEmail())
                .password(form.getPassword())
                .role(Role.USER)
                .emailAuth(false)
                .build();

        return memberService.join(member);
    }

    @RequestMapping(value = "/members/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginForm form) {
        return memberService.login(form);
    }

    @RequestMapping(value = "/members/logout", method = RequestMethod.POST)
    public String logout(HttpSession session) throws IOException {

        log.info("로그아웃");
        session.invalidate();

        return "logout";
    }

    @RequestMapping(value = "/members/confirm_email", method = RequestMethod.GET)
    public String confirmEmail(@ModelAttribute EmailAuthRequestDto requestDto) {
        memberService.confirmEmail(requestDto);
        return "인증이 완료되었습니다.";
    }

    @RequestMapping(value = "/members/send_pass_mail", method = RequestMethod.POST)
    public String sendPasswordChangeEmail(@RequestBody Map<String,String> emailMap) throws IOException {
        System.out.println("email = " + emailMap.get("email"));
        return memberService.sendPasswordChangeEmail(emailMap.get("email"));
    }

    @RequestMapping(value = "/members/new_password", method = RequestMethod.PUT)
    public String updatePassword(@RequestParam String email, String uuid, @RequestBody ChangeMemberInfoForm changeMemberInfoForm) {
        System.out.println("email = " + email);
        System.out.println("uuid = " + uuid);
        memberService.updatePassword(email,uuid, changeMemberInfoForm.getPassword());
        return "비밀번호 변경 완료";
    }
}
