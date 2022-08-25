package eCommerce.AmazonUserMySql.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .csrf().disable().
                authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .antMatchers(
                        "/v3/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    ProductDetailsProxy pdp() {
//        return new ProductDetailsProxy() {
//            @Override
//            @GetMapping("/amazon/products/getByProductId/{productId}")
//            public ProductEntity getByUniqueId(UUID productId) {
//                return null;
//            }
//        }
//    }



}
