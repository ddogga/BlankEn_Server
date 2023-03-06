package blank.english.controller;


import blank.english.dto.QuizSetSaveForm;
import blank.english.service.quiz.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/quiz/new_quiz_set")
    public String createQuizSet(QuizSetSaveForm quizForm) {
        quizService.createQuizSet(quizForm);
        return "퀴즈가 등록되었습니다.";
    }
}
