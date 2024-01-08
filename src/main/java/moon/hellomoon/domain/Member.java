package moon.hellomoon.domain;

import jakarta.persistence.*;

@Entity
public class Member {

    /**
    IDENTITY 전략 : DB가 ID를 알아서 생성
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "name",unique = true)
    private String name;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name="rog_date")
    private String rog_date;
    @Column(name="password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName(){
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
    public String getRog_date() {
        return rog_date;
    }

    public void setRog_date(String rog_date) {
        this.rog_date = rog_date;
    }


}
