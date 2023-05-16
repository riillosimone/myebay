package it.prova.myebay.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecSecurityConfig  {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
    private CustomAuthenticationSuccessHandlerImpl successHandler;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
//	@Bean
//	public AuthenticationSuccessHandler successHandler() {
//		 SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
//		    handler.setUseReferer(true);
//		    return handler;
//	}
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
         .userDetailsService(customUserDetailsService);
         //.passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	 http
    	 .authorizeRequests()
         .antMatchers("/assets/**").permitAll()
         .antMatchers("/public/**").permitAll()
         .antMatchers("/login","/signup/**").permitAll()
         .antMatchers("/utente/**","/annuncio/**","/acquisto/**","/ricarica/**").hasAnyRole("ADMIN", "CLASSIC_USER")
         .antMatchers("/secured/**").hasAnyRole("ADMIN", "CLASSIC_USER")
         .antMatchers("/admin/**").hasRole("ADMIN")
//         .antMatchers("/anonymous*").anonymous()
         .anyRequest().authenticated()
         .and().exceptionHandling().accessDeniedPage("/accessDenied")
         .and()
         	.formLogin()
         		
         	.loginPage("/login")
         	
//         	.defaultSuccessUrl("/home",true)
//         	.defaultSuccessUrl("/")
//         	//uso un custom handler perché voglio mettere delle user info in session
         	.successHandler(successHandler)
//         	.successHandler(successHandler())
         	
         	.failureUrl("/login?error=true")
         	.permitAll()
         .and()
         	.logout()
         	.logoutSuccessUrl("/executeLogout")
            .invalidateHttpSession(true)
            .permitAll()
         .and()
            .csrf()
            .disable();
         return http.build();
    }
    
    
}
