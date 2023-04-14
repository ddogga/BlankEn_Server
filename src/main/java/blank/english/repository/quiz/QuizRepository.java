package blank.english.repository.quiz;


import blank.english.entity.Category;
import blank.english.entity.quiz.QuizSet;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<QuizSet, Long> {


    @Query("select s from QuizSet s " +
            "join s.quizList q " +
            "where s.shared = :shared " +
            "order by s.createdDate desc"
    )
    Page<QuizSet> findAllByShared(boolean shared,Pageable pageable);

//    List<QuizSet> findAllByShared(boolean shared);
}
