/*package com.collaborate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception
	{
		builder.inMemoryAuthentication().withUser("userId").password("password").roles("user");
		builder.inMemoryAuthentication().withUser("admin").password("password").roles("ADMIN");
	}

	protected void configure(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.authorizeRequests().antMatchers("/resources/**","/Login").permitAll()
										.antMatchers("/admin/**").hasRole("ADMIN")
										.anyRequest().authenticated()
				.and()
					.formLogin()
								.loginPage("#/Login").permitAll()
								.loginProcessingUrl("/perform_login")
								.usernameParameter("userId")
								.passwordParameter("password")
								.successForwardUrl("/Login")
								.failureForwardUrl("#/Home")
				.and()
					.httpBasic()
				.and()
					.logout()
							.logoutUrl("/Logout")
							.logoutSuccessUrl("#/Home")
							.invalidateHttpSession(true);
							
					
	}
}
*/