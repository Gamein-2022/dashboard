package gamein2022.backend.dashboard.infrastructure.repository;

import gamein2022.backend.dashboard.core.sharedkernel.entity.News;
import gamein2022.backend.dashboard.core.sharedkernel.enums.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByType(NewsType type);
}
