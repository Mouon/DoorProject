package moon.hellomoon.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Setter
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "like_count")
    private int likeCount;

}