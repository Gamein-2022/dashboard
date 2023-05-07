package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.web.dto.result.TeamOfferDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "team_offers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    private Team team;

    @ManyToOne(optional = false)
    private User user;

    public TeamOfferDTO toDTO() {
        return new TeamOfferDTO(id, team.getName(), user.getName());
    }
}
