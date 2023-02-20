package blank.english.repository.member;

import blank.english.entity.EmailAuthToken;
import blank.english.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmailRepository {

    private final EntityManager em;

    public EmailAuthToken save(EmailAuthToken emailAuthToken) {
        log.info(emailAuthToken.getId());

        em.merge(emailAuthToken);
        return emailAuthToken;
    }

    public Optional<EmailAuthToken> findValidTokenByEmail(String email, String tokenId, LocalDateTime currentTime) {
        return em.createQuery("select t from EmailAuthToken t where t.email = :email and t.id = :id and t.expireDate > :date and t.expired = :expired", EmailAuthToken.class)
                .setParameter("email", email)
                .setParameter("id", tokenId)
                .setParameter("date", currentTime)
                .setParameter("expired", false)
                .getResultList()
                .stream().findAny();
    }
}
