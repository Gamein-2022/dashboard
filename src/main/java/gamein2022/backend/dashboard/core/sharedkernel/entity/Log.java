package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.core.sharedkernel.enums.LogType;
import gamein2022.backend.dashboard.web.dto.result.LogDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Table(name = "logs")
@Entity()
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "total_cost")
    private Long totalCost;

    @Column(name = "product_count")
    private Long productCount;

    @ManyToOne
    private Product product;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private LogType type;

    @ManyToOne()
    private Team team;

    @Column(name = "timestamp", nullable = false, columnDefinition = "timestamp default (now() at time zone 'utc')")
    private LocalDateTime timestamp = LocalDateTime.now(ZoneOffset.UTC);

    public LogDTO toDto(){
        return new LogDTO(
                type,
                totalCost,
                productCount,
                product.getName(),
                timestamp
        );
    }


}
