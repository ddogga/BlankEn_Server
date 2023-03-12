package blank.english.service.quiz;

import blank.english.dto.QuizDto;
import blank.english.dto.QuizSetSaveForm;
import blank.english.entity.Category;
import blank.english.entity.Member;
import blank.english.entity.quiz.Quiz;
import blank.english.entity.quiz.QuizSet;
import blank.english.exception.CategoryNotFountException;
import blank.english.exception.MemberNotFoundException;
import blank.english.repository.member.MemberRepository;
import blank.english.repository.quiz.CategoryRepository;
import blank.english.repository.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final FileUploadService fileUploadService;
    private final MemberRepository memberRepository;
    private final QuizRepository quizRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void createQuizSet(QuizSetSaveForm quizForm) {
        QuizSet quizSet = makeQuizSet(quizForm);
        quizRepository.save(quizSet);

    }

    private QuizSet makeQuizSet(QuizSetSaveForm quizForm) {
        System.out.println(quizForm.getEmail());
        return QuizSet.builder()
                .member(findMemberByEmail(quizForm.getEmail()))
                .titleImg(uploadImage(quizForm.getTitleImg()))
                .quizList(makeQuizListByQuizDtoList(quizForm.getQuizList()))
                .title(quizForm.getTitle())
                .contents(quizForm.getContents())
                .category(findCategoryByName(quizForm.getCategory()))
                .shared(quizForm.isShared())
                .build();
    }


    private String uploadImage(MultipartFile uploadFile){
        return fileUploadService.uploadFile(uploadFile);
    }

    private List<Quiz> makeQuizListByQuizDtoList(List<QuizDto> quizList) {
        List<Quiz> newQuizList = new ArrayList<>();

        quizList.stream()
        .filter(v -> v != null)
        .forEach(v -> newQuizList.add(Quiz.builder()
                .quizImgName(uploadImage(v.getQuizImg()))
                .firstSentence(v.getFirstSentence())
                .blank(v.getBlank())
                .lastSentence(v.getLastSentence())
                .expression(v.getExpression())
                .num(v.getNum())
                .build())
        );
        return newQuizList;
    }

    private Member findMemberByEmail(String email) {
        System.out.println("email = " + email);
        return memberRepository.findOneByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    private Category findCategoryByName(String name) {
        System.out.println("name = " + name);
        return categoryRepository.findOneByName(name)
                .orElseThrow(CategoryNotFountException::new);
    }
}
