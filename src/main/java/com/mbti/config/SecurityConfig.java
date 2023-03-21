package com.mbti.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;




@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/")
                .and()
                .logout()                                        
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
        ;

//        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/**")).permitAll() // 모든 URL을 허용
//		.and() // http 객체의 설정을 이어서 할 수 있게 하는 메서드이다
//		// H2 콘솔은 이와 같은 CSRF 토큰을 발행하는 기능이 없기 때문에 위와 같은 403 오류가 발생하는 것이다.
//		// ignoringRequestMatchers:특정요청 에만 대상을 제외
//		.csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
//		.and()
//		.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
//				//frame 에 포함된 페이지가 페이지를제공하는 사이트와 동일한 경우에는 계속 사용할 수 있다.
//				XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
//		;
        
        http.authorizeHttpRequests()
        		.requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .requestMatchers("/").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
                .and()
        		.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
        				//frame 에 포함된 페이지가 페이지를제공하는 사이트와 동일한 경우에는 계속 사용할 수 있다.
        				XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
        		;
        ;


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

