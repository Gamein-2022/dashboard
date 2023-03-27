package gamein2022.backend.dashboard.core.sharedkernel.entity;

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
@Table(name = "process")
public class Process {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "team_id")
    private long teamId;

    @Column(name = "line_id")
    private long lineId;

    @OneToOne
    private Product product;

    @Column(name = "product_count")
    private long productCount;

    @Column(name = "start_time")
    private Date start;

    @Column(name = "end_time")
    private Date end_time;
}
