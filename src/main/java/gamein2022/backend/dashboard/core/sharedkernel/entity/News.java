package gamein2022.backend.dashboard.core.sharedkernel.entity;

import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import gamein2022.backend.dashboard.web.dto.result.NewsDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "news")
@Entity()
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, length = 1048576)
    private String desc;

    @Column(name = "image_address")
    private String image;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NewsType type;

    public NewsDTO toDTO() {
        return new NewsDTO(title, desc, image);
    }
}
