package gamein2022.backend.dashboard.core.sharedkernel.entity;

import lombok.Getter;

import javax.persistence.*;


@Entity
@Getter
@Table(name = "storage_products")
public class StorageProduct extends BaseEntity {
    String factoryId;

    @Column(name = "productId")
    String productId;

    @Column(name = "status")
    String status;

    @Column(name = "amount")
    long amount;

    @ManyToOne
    private Factory factory;
}