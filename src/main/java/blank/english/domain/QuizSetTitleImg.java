package blank.english.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class QuizSetTitleImg {

    @Id
    @GeneratedValue
    @Column(name = "title_img_id")
    private Long id;

    private String imgName;


}
