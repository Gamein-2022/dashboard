package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.web.dto.result.TeamResearchDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "team_researches")
public class TeamResearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @ManyToOne(optional = false)
    private Team team;

    @ManyToOne(optional = false)
    private ResearchSubject subject;

    @Column(name = "begin_time")
    private Date beginTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "paid_amount")
    private int paidAmount;

    public TeamResearchDTO toDTO(long balance, int price, int duration) {
        return new TeamResearchDTO(subject.toDTO(), paidAmount, beginTime, endTime, endTime == null ? "not-started" :
                endTime.before(new Date()) ?
                        "done" :
                        "doing", balance, price, duration);
    }
}
