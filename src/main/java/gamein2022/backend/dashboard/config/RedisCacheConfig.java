package gamein2022.backend.dashboard.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import gamein2022.backend.dashboard.data.entity.Team;
import gamein2022.backend.dashboard.data.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class RedisCacheConfig {
    @Bean(name = "requestLogCache")
    public Cache<String,Long> createCache(){
        return CacheBuilder
                .newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    @Bean(name = "teamLogCache")
    public Cache<String, Team> createTeamCache(){
        return CacheBuilder
                .newBuilder()
                .expireAfterWrite(1,TimeUnit.MINUTES)
                .build();
    }
    @Bean(name = "userLogCache")
    public Cache<String, User> createUserCache(){
        return CacheBuilder
                .newBuilder()
                .expireAfterWrite(1,TimeUnit.MINUTES)
                .build();
    }
}
