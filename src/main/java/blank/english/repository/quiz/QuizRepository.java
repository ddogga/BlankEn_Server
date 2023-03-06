package blank.english.repository.quiz;


import blank.english.entity.quiz.QuizSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class QuizRepository {

    private final EntityManager em;

    public QuizSet save(QuizSet quizSet) {
        if(quizSet.getId() == null) {
            em.persist(quizSet);
        } else {
            em.merge(quizSet);
        }
        return quizSet;
    }
}
