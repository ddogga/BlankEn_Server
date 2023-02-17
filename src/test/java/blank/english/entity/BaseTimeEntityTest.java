package blank.english.entity;

import blank.english.repository.MemberRepository;
import blank.english.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
class BaseTimeEntityTest {


    @Autowired
    EntityManager em;
    @Autowired
    MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void BaseTimeEntity_등록() {

        //given
        LocalDateTime now = LocalDateTime.of(2023,1,1,0,0,0);//

        memberRepository.save(Member.builder()
                .userName("username")
                .nickname("nickname")
                .email("email")
                .password("password")
                .build()
            );

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        Member member = memberList.get(0);
        System.out.println(">>>>>>>>> createDate=" + member.getCreatedDate() + ", modifiedDate=" + member.getModifiedDate());
        assertThat(member.getCreatedDate()).isAfter(now); //설정한 now값 보다 현재 날짜가 이후이므로 test 통과
        assertThat(member.getModifiedDate()).isAfter(now);
    }

}