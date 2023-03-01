package blank.english.repository.member;

import blank.english.entity.EmailAuthToken;
import blank.english.entity.ResetPasswordToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TokenRepository {

    private final EntityManager em;

    public EmailAuthToken save(EmailAuthToken emailAuthToken) {
        em.merge(emailAuthToken);
        return emailAuthToken;
    }

    public ResetPasswordToken save(ResetPasswordToken resetPasswordToken) {
        em.merge(resetPasswordToken);
        return resetPasswordToken;
    }

    public Optional<EmailAuthToken> findValidTokenByEmail(String email, String tokenId, LocalDateTime currentTime) {
        return em.createQuery("select t from EmailAuthToken t where t.email = :email and t.uuid = :uuid and t.expireDate > :date and t.expired = :expired", EmailAuthToken.class)
                .setParameter("email", email)
                .setParameter("uuid", tokenId)
                .setParameter("date", currentTime)
                .setParameter("expired", false)
                .getResultList()
                .stream().findAny();
    }


    public Optional<ResetPasswordToken> findPassTokenByEmail(String email, String tokenId, LocalDateTime currentTime) {
        return em.createQuery("select t from ResetPasswordToken t where t.email = :email and t.uuid = :uuid and t.expireDate > :date and t.expired = :expired", ResetPasswordToken.class)
                .setParameter("email", email)
                .setParameter("uuid", tokenId)
                .setParameter("date", currentTime)
                .setParameter("expired", false)
                .getResultList()
                .stream().findAny();
    }
}
