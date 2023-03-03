package blank.english.service.quiz;

import blank.english.dto.QuizSetSaveForm;
import blank.english.entity.Member;
import blank.english.entity.QuizSet;
import blank.english.exception.MemberNotFoundException;
import blank.english.repository.member.MemberRepository;
import blank.english.repository.quiz.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final MemberRepository memberRepository;
    private final QuizRepository quizRepository;

    @Transactional
    @Override
    public void createQuizSet(QuizSetSaveForm quizForm) {
        QuizSet quizSet = makeQuizSet(quizForm);
        quizRepository.save(quizSet);

    }

    private QuizSet makeQuizSet(QuizSetSaveForm quizForm) {
        return QuizSet.builder()
                .member(findMemberByEmail(quizForm.getEmail()))
                .quizList(quizForm.getQuizList())
                .title(quizForm.getTitle())
                .contents(quizForm.getContents())
                .build();
    }

    private Member findMemberByEmail(String email) {
        return memberRepository.findOneByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }
}
