package gamein2022.backend.dashboard.core.sharedkernel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "teams")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToMany
    private List<User> users;

    @OneToMany
    private List<Shipping> shippings;

    @OneToOne(optional = false)
    private User owner;

    @Column(name = "balance", nullable = false)
    private long balance;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "region")
    private int region;

    @OneToMany(mappedBy = "team")
    private List<StorageProduct> storageProducts;

    @OneToMany
    private List<Building> buildings;

    @OneToMany
    List<Product> availableProductIds;

    @OneToMany
    private List<TeamResearch> researches;

    @Column(name = "is_storage_upgraded",columnDefinition = "boolean default false")
    private Boolean isStorageUpgraded;

    @Column(name = "is_region_upgraded", columnDefinition = "boolean default false")
    private Boolean isRegionUpgraded = false;
}