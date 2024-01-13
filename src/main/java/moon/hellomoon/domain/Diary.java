package moon.hellomoon.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * jpa는 클래스명과 일치하는 테이블 찾는데 다르면 @Table(name = "events") 이런식으로 어노테이션으로 명시해주세요
 * */
@Entity
@Table(name = "events")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member; // Member 엔티티에 대한 참조
    @Column(name = "event_date")
    private LocalDate eventDate;
    @Column(name = "event_description")
    private String eventDescription;
    @Column(name = "create_date")
    private String CreateDate;
    @Column(name = "modify_date")
    private String ModifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        eventDescription = eventDescription;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getModifyDate() {
        return ModifyDate;
    }

    public void setModifyDate(String modifyDate) {
        ModifyDate = modifyDate;
    }

    public Long getMemberId() {
        return member != null ? member.getId() : null;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
