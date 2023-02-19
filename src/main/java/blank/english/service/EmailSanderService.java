package blank.english.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSanderService {
    private final JavaMailSender javaMailSender;

    @Async
    public void sender(String email, String emailAuthTokenId) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(email + "@gmail.com");
        smm.setSubject("회원가입 이메일 인증");
        smm.setText("http://localhost:8080/sign/confirm-email?email="+email+"&authToken="+emailAuthTokenId);

        javaMailSender.send(smm);
    }
}
