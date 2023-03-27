package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.web.dto.result.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.List;
import java.util.Map;


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

    @OneToMany
    private List<Product> components;

    @Column(name = "region", nullable = false)
    private int region;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "available_year", nullable = false)
    private long availableYear;

    @OneToOne
    private ResearchSubject requires;



    public ProductDTO toDTO() {
        return new ProductDTO(id, name, price);
    }
}
