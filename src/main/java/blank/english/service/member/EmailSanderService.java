package blank.english.service.member;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailSanderService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sender(String email, String uuid) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom("");//보내는 사람 이메일
        smm.setTo(email); //받는 사람 이메일
        smm.setSubject("회원가입 이메일 인증");
        smm.setText("http://localhost:8080/blanken/api/members/confirm_email?email="+email+"&uuid="+uuid);

        javaMailSender.send(smm);
    }

    @Async
    public void sendPassLink(String uuid,String email) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setFrom("fpdl1gkgk@naver.com");//보내는 사람 이메일
        smm.setTo(email); //받는 사람 이메일
        smm.setSubject("회원가입 이메일 인증");
        smm.setText("http://localhost:8080/blanken/update_pass?email="+email+"uuid="+uuid);

        javaMailSender.send(smm);
    }
}
