package gamein2022.backend.dashboard.core.sharedkernel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "factories")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Factory extends BaseEntity {
    @OneToOne
    private Team team;

    @OneToMany
    private List<StorageProduct> storageProducts;
}