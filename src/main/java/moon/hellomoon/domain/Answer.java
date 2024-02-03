package moon.hellomoon.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "like_count")
    private int likeCount;
}
