package gamein2022.backend.dashboard.core.sharedkernel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseEntity {
    @ManyToOne
    private Team team;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "persian_name")
    private String persianName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "password")
    private String password;

    @Column(name = "last_login")
    private Date lastLogin;
}