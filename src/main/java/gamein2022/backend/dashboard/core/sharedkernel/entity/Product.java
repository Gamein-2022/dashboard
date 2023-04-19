package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.web.dto.result.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long id;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection
    private List<Integer> regions;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "available_day", nullable = false)
    private int availableDay;

    @ManyToOne
    private ResearchSubject RAndD;

    @Column(name = "production_rate")
    private Long productionRate;

    @Column(name = "unit_volume", nullable = false)
    private int unitVolume;

    @Column(name = "demand_coefficient")
    private Double demandCoefficient;

    public ProductDTO toDTO() {
        return new ProductDTO(id, name, price, level, productionRate);
    }
}
