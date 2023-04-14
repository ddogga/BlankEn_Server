package blank.english.controller;


import blank.english.dto.QuizSetResDto;
import blank.english.dto.QuizSetSaveForm;
import blank.english.entity.quiz.Quiz;
import blank.english.entity.quiz.QuizSet;
import blank.english.service.quiz.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/quiz/new_quiz_set")
    public String createQuizSet(QuizSetSaveForm quizForm) {
        quizService.createQuizSet(quizForm);
        return "퀴즈가 등록되었습니다.";
    }

//    @GetMapping("/quizs")
//    public List<QuizSetResDto> getQuizs(@RequestParam int page, int size, String sort) {
//
//        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort).descending());
//        return quizService.findAllQuizSet(pageRequest).getContent();
//    }

    @GetMapping("/quizs")
    public Page<QuizSet> getQuizs(@RequestParam int page, int size, String sort) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sort).descending());
        return quizService.findAllQuizSet(pageRequest);
    }
}
