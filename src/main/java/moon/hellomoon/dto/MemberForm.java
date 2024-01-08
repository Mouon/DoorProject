package moon.hellomoon.dto;

import moon.hellomoon.domain.Member;
import org.springframework.web.bind.annotation.PostMapping;

public class MemberForm {
    private String name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String password;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
