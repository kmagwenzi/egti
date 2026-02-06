package zw.co.egti.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DataSourceConfig {

    private final Environment env;

    public DataSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
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
                String jdbcUrl = String.format("jdbc:postgresql://%s:%d/%s?sslmode=require", host, port, db);
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
        // Fallback to default Spring Boot auto-configuration
        HikariDataSource dataSource = new HikariDataSource();
        return dataSource;
    }
}