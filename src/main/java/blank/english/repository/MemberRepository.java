package blank.english.repository;

import blank.english.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class,id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    //이름으로 회원 조회
    public List<Member> findByName(String userName) {
        return em.createQuery("select m from Member m where m.userName = :userName",
                        Member.class)
                .setParameter("userName", userName)
                .getResultList();
    }
}
