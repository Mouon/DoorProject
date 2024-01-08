package moon.hellomoon.dto;

public class BoardForm {

    private Long id;
    private Long memberId;
    private String title;
    private String content;
    private String date;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BoardForm(Long id, Long memberId, String title, String content) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "BoardForm{" +
                "memberId=" + memberId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
