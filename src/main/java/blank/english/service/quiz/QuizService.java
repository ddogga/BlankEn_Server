package blank.english.service.quiz;

import blank.english.dto.QuizSetSaveForm;
import org.springframework.web.multipart.MultipartFile;

public interface QuizService {

    void createQuizSet(QuizSetSaveForm quizForm);

}
