package blank.english.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본생성자의 접근 제어자를 PROTECTED로 설정
public class EmailAuthToken {

    private static final long EMAIL_TOKEN_EXPIRATION_TIME = 5L;//토큰 만료 제한시간 5분

    @Id @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2") // uuid : Hibernate에서 기본적으로 제공하는 식별자
    @Column(name = "email_auth_token_id", length = 36)
    private String id;

    private String email;
    private String authToken;
    private Boolean expired;
    private LocalDateTime expireDate;

    public static EmailAuthToken createEmailAuthToken(String email){
        EmailAuthToken emailAuthToken = new EmailAuthToken();
        emailAuthToken.expireDate = LocalDateTime.now().plusMinutes(EMAIL_TOKEN_EXPIRATION_TIME);
        emailAuthToken.email = email;
        emailAuthToken.expired = false;
        return emailAuthToken;
    }

    public void useToken() {
        this.expired = true;
    }
}
