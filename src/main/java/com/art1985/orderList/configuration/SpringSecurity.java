package com.art1985.orderList.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private SimpleAuthenticationSuccessHandler successHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email, password, enabled from users where email = ?")
                .authoritiesByUsernameQuery("select s.first_name, a.authority from authorities a\n" +
                        "inner join users s on a.user_id = s.id\n" +
                        "where s.email = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/home")).and()
//                .authorizeRequests()
//                .antMatchers("/api/v1/products").permitAll()
//                .antMatchers("/api/v1/user/**", "/api/v1/order/**").hasRole("USER")
//                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
//                .and().formLogin().successHandler(successHandler)
//                .loginPage("/api/v1/home").and().logout().permitAll();
        http.csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("**/home")).and()
                .authorizeRequests()
                .antMatchers("/api/v1/products", "**/home").permitAll()
                .antMatchers("/api/v1/user/**", "/api/v1/order/**").hasRole("USER")
                .antMatchers("/api/v1/orders/**", "/api/v1/users/**", "/api/v1/products/**").hasRole("ADMIN")
                .and().formLogin().successHandler(successHandler);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
