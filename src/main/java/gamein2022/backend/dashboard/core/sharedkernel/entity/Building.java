package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.core.sharedkernel.enums.BuildingType;
import gamein2022.backend.dashboard.web.dto.result.BuildingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "buildings")
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @ManyToOne(optional = false)
    private Team team;

    @Column(name = "type")
    private BuildingType type;

    @Column(name = "upgraded")
    private boolean upgraded;

    public BuildingDTO toDTO() {
        return new BuildingDTO(
                id,
                type,
                upgraded
        );
    }
}
