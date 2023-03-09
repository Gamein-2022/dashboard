package gamein2022.backend.dashboard.core.sharedkernel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "teams")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Team extends BaseEntity {
//    @OneToOne
//    private Factory factory;

    @OneToMany
    private List<User> users;

    @Column(name = "balance")
    private long balance;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private int region;
}