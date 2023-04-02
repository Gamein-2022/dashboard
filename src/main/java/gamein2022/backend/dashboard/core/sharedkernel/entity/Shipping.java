package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.core.sharedkernel.enums.ShippingMethod;
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
@Table(name = "shipping")
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "method", nullable = false)
    private ShippingMethod method;

    @Column(name = "source_region", nullable = false)
    private Integer sourceRegion;

    @Column(name = "departure_time", nullable = false)
    private Date departureTime;

    @Column(name = "arrival_time", nullable = false)
    private Date arrivalTime;

    @ManyToOne(optional = false)
    private Team team;
}