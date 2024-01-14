package moon.hellomoon.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberCreateRequest {
    private String name;
    private String password;
    private String email;
}
