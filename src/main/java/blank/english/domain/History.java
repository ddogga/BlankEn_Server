package blank.english.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class History {

    @Id
    @GeneratedValue
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}
