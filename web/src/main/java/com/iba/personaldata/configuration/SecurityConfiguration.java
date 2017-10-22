package com.iba.personaldata.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}")
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.
                jdbcAuthentication()
                .usersByUsernameQuery(usersQuery)
                .authoritiesByUsernameQuery(rolesQuery)
                .dataSource(dataSource);
//                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "person/login").permitAll()
                .antMatchers("/assest/**").permitAll()
                .antMatchers("/user/**").access("hasAuthority('USER')")
                .antMatchers("/admin/**").access("hasAuthority('ADMIN')")
                .antMatchers("/person/logout").access("hasAnyAuthority('USER','ADMIN')")
                .and().logout().clearAuthentication(true).deleteCookies().logoutSuccessUrl("/logout").logoutUrl("/logout").invalidateHttpSession(true)
                .and().formLogin().loginPage("/person/login").defaultSuccessUrl("/person/afterLogin", true)
                .usernameParameter("login").passwordParameter("password").failureUrl("/person/login?status=error").permitAll()
                .and().exceptionHandling().accessDeniedPage("/error")
                .and().csrf().disable();
    }
}
