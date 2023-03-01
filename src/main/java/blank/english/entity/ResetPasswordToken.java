package blank.english.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResetPasswordToken {

    private static final long TOKEN_EXPIRATION_TIME = 30L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;
    private String email; //사용자 이메일
    private Boolean expired; //토큰 만료 여부
    private LocalDateTime expireDate; //만료일

    public static ResetPasswordToken createRestPasswordToken(String email){
        ResetPasswordToken restPasswordToken = new ResetPasswordToken();
        restPasswordToken.uuid = UUID.randomUUID().toString();
        restPasswordToken.expireDate = LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_TIME);
        restPasswordToken.email = email;
        restPasswordToken.expired = false;
        return restPasswordToken;
    }

    public void useToken() {
        this.expired = true;
    }
}
