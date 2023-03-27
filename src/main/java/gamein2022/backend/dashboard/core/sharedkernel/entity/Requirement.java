package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.core.sharedkernel.entity.Product;
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
@Table(name = "requirements")
public class Requirement {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "product_id")
    private long productId;

    @OneToOne
    private Product requirement;

    @Column(name = "count")
    private int count;


}
