package zw.co.egti.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@Profile("!dev")  // Only active when 'dev' profile is NOT active
public class DataSourceConfig {

    private final Environment env;

    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        String databaseUrl = env.getProperty("DATABASE_URL");
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            // Convert to JDBC URL
            try {
                URI dbUri = new URI(databaseUrl);
                String[] userInfo = dbUri.getUserInfo().split(":");
                String username = userInfo[0];
                String password = userInfo.length > 1 ? userInfo[1] : "";
                String host = dbUri.getHost();
                int port = dbUri.getPort() != -1 ? dbUri.getPort() : 5432;
                String db = dbUri.getPath().replaceFirst("/", "");
                // Remove sslmode=require for local development
                String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s", host, port, db);
                HikariDataSource dataSource = new HikariDataSource();
                dataSource.setJdbcUrl(jdbcUrl);
                dataSource.setUsername(username);
                dataSource.setPassword(password);
                // Hikari pool settings will be overridden by @ConfigurationProperties
                return dataSource;
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid DATABASE_URL", e);
            }
        }
        // Fallback: Let Spring Boot auto-configuration handle it
        // Return null to let Spring Boot create the DataSource via auto-configuration
        // Actually, we need to return a DataSource, so create one that will be configured by @ConfigurationProperties
        HikariDataSource dataSource = new HikariDataSource();
        // Don't set URL/username/password here - let @ConfigurationProperties bind them
        return dataSource;
    }
}