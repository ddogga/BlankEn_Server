package blank.english.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본생성자의 접근 제어자를 PROTECTED로 설정
public class EmailAuthToken {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME = 5L;//토큰 만료 제한시간 5분

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String email; //사용자 이메일
    private Boolean expired; //토큰 만료 여부
    private LocalDateTime expireDate; //만료일

    public static EmailAuthToken createEmailAuthToken(String email){
        EmailAuthToken emailAuthToken = new EmailAuthToken();
        emailAuthToken.uuid = UUID.randomUUID().toString();
        emailAuthToken.expireDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME);
        emailAuthToken.email = email;
        emailAuthToken.expired = false;
        return emailAuthToken;
    }

    public void useToken() {
        this.expired = true;
    }
}
