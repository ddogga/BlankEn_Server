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
        smm.setText("http://localhost:8080/blanken/api/members/confirm-email?email="+email+"&uuid="+uuid);

        javaMailSender.send(smm);
    }
}
