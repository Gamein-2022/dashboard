package gamein2022.backend.dashboard.core.sharedkernel.entity;


import gamein2022.backend.dashboard.web.dto.result.ResearchSubjectDTO;
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
@Table(name = "research_subjects")
public class ResearchSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "available_day", nullable = false)
    private int availableDay;

    @Column(name = "base_price")
    private int basePrice;

    @Column(name = "base_duration")
    private int baseDuration;

    public ResearchSubjectDTO toDTO() {
        return new ResearchSubjectDTO(name, availableDay, basePrice, baseDuration);
    }
}
