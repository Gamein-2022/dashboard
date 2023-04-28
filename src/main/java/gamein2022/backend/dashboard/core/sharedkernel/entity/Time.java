package gamein2022.backend.dashboard.core.sharedkernel.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "time")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "begin_time")
    private LocalDateTime beginTime;

    @Column(name = "stopped_time")
    private Long stoppedTimeSeconds;

    @Column(name = "choose_region_duration")
    private Long chooseRegionDuration;

    @Column(name = "last_stop")
    private LocalDateTime lastStopTime;

    @Column(name = "is_game_paused", columnDefinition = "boolean default false")
    private Boolean isGamePaused;

    @Column(name = "is_region_payed", columnDefinition = "boolean default false")
    private Boolean isRegionPayed;

    @Column(name = "scale", nullable = false, columnDefinition = "bigint default 0.25")
    private Long scale = 1000000L;

}
