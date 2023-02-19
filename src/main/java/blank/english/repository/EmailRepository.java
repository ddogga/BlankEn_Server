package blank.english.repository;

import blank.english.entity.EmailAuthToken;
import blank.english.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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
}
