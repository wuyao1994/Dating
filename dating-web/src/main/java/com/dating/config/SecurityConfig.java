package com.dating.config;

import com.dating.util.MD5PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dating.exception.JWTAccessDeniedHandler;
import com.dating.exception.JWTAuthenticationEntryPoint;
import com.dating.filter.JWTAuthenticationFilter;
import com.dating.filter.JWTAuthorizationFilter;
import com.dating.service.impl.UserDetailsServiceImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author elvis
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	/**
	 * 密码编码器
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService createUserDetailsService() {
		return userDetailsServiceImpl;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 设置自定义的userDetailsService以及密码编码器
		auth.userDetailsService(userDetailsServiceImpl)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new MD5PasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				// 禁用 CSRF
				.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/auth/login").permitAll()
				// 指定路径下的资源需要验证了的用户才能访问
				.antMatchers("/api/**").authenticated()
				// 其他都放行了
				.anyRequest().permitAll().and()
				// 添加自定义Filter
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilter(new JWTAuthorizationFilter(authenticationManager()))
				// 不需要session（不创建会话）
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				// 授权异常处理
				.exceptionHandling()
				.authenticationEntryPoint(new JWTAuthenticationEntryPoint())
				.accessDeniedHandler(new JWTAccessDeniedHandler());
		// 防止H2 web 页面的Frame 被拦截
		http.headers().frameOptions().disable();
	}

}
