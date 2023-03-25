package gamein2022.backend.dashboard.core.sharedkernel.entity;

import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
@Table(name = "storage_products")
public class StorageProduct  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "productId")
    String productId;

    @Column(name = "status")
    String status;

    @Column(name = "amount")
    long amount;

}