package moon.hellomoon.dto;

import java.time.LocalDate;

public class DiaryForm {
    private Long id;
    private Long memberId;
    private LocalDate EventDate;
    private String EventDescription;
    private String CreateDate;
    private String ModifyDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public LocalDate getEventDate() {
        return EventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        EventDate = eventDate;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
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
}
