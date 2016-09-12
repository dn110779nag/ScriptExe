package com.mycompany.script.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурация безопасности.
 *
 * @author nova
 */
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

    @Configuration
    static class DataSourceConfiguration {

        @Bean(name = "securityDataSource")
        @ConfigurationProperties(prefix = "security.datasource")
        public DataSource mysqlDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean(name="securityJdbcTemplate")
        @Autowired
        public JdbcTemplate configureJdbcTempalate(@Qualifier("securityDataSource") DataSource dataSource) {
            return new JdbcTemplate(dataSource);
        }
    }

    @Autowired
    @Qualifier("securityDataSource")
    private DataSource dataSource;
    @Autowired
    @Qualifier("appPwdEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder);
    }
}
