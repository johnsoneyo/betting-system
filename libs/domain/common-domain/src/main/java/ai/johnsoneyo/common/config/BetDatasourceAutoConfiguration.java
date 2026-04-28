package ai.johnsoneyo.common.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = "ai.johnsoneyo.common.repository")
@EntityScan(basePackages = "ai.johnsoneyo.common.entity")
@Configuration
public class BetDatasourceAutoConfiguration {
}
