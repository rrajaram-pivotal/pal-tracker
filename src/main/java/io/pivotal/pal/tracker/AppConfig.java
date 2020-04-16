package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class AppConfig {


    @ConditionalOnProperty (name="repo",havingValue="jdbc", matchIfMissing = true)
    @Bean
    public TimeEntryRepository jdbcRepository ( DataSource dataSource)
    {
            return new JdbcTimeEntryRepository(dataSource);
    }

    @ConditionalOnProperty (name="repo",havingValue="inmem", matchIfMissing = false)
    @Bean
    public TimeEntryRepository inMemoryRepository ( DataSource dataSource)
    {
        return new InMemoryTimeEntryRepository();
    }
}
