package com.example.App.security;

import com.example.App.service.AngajatService;
import com.example.App.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final CompanyService companyService;

        @Autowired
        public App1ConfigurationAdapter(CompanyService companyService) {
            super();
            this.companyService = companyService;
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
//                    .authorizeRequests()
//                    .antMatchers("/home*").permitAll()
//                    .antMatchers("/register/**").permitAll()
//                    .antMatchers("/company*").hasRole("COMPANY")
//                    .antMatchers("/company/**").hasRole("COMPANY")

                    .antMatcher("/company*")
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("COMPANY")

                    .and()
                    .formLogin()
                    .loginPage("/loginCompany")
                    .loginProcessingUrl("/company_login")
                    .failureUrl("/loginCompany?error=loginError")
                    .defaultSuccessUrl("/companyHome")

                    .and()
                    .logout()
                    .logoutUrl("/company_logout")
                    .logoutSuccessUrl("/loginCompany")
                    .deleteCookies("JSESSIONID")

                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403")

                    .and()
                    .csrf().disable();
        }

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
            auth.setUserDetailsService(companyService);
            auth.setPasswordEncoder(adminEncoder());
            return auth;
        }

        @Bean
        public BCryptPasswordEncoder adminEncoder() {
            return new BCryptPasswordEncoder();
        }

    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        private final AngajatService angajatService;
        private final PasswordEncoder userEncoder;
        @Autowired
        public App2ConfigurationAdapter(@Qualifier("passwordEncoder") PasswordEncoder userEncoder, AngajatService angajatService) {
            super();
            this.userEncoder = userEncoder;
            this.angajatService = angajatService;

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(angajatAuthenticationProvider());
        }


        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/angajat*")
                    .authorizeRequests()
                    .anyRequest()
                    .hasRole("ANGAJAT")

                    .and()
                    .formLogin()
                    .loginPage("/loginAngajat")
                    .loginProcessingUrl("/angajat_login")
                    .failureUrl("/loginAngajat?error=loginError")
                    .defaultSuccessUrl("/angajatHome")

                    .and()
                    .logout()
                    .logoutUrl("/angajat_logout")
                    .logoutSuccessUrl("/loginAngajat")
                    .deleteCookies("JSESSIONID")

                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403")

                    .and()
                    .csrf().disable();
        }

        @Bean
        public DaoAuthenticationProvider angajatAuthenticationProvider() {
            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
            auth.setUserDetailsService(angajatService);
            auth.setPasswordEncoder(userEncoder);
            return auth;
        }

    }
}
