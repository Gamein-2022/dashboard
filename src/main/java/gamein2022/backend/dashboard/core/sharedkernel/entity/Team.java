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
public class Team {
//    @OneToOne
//    private Factory factory;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToMany
    private List<User> users;

    @OneToOne
    private User owner;

    @Column(name = "balance")
    private long balance;

    @Column(name = "name")
    private String name;

    @Column(name = "region")
    private int region;
}