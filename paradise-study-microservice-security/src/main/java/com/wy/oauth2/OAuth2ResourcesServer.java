package com.wy.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import com.wy.properties.UserProperties;
import com.wy.security.LoginFailureHandler;
import com.wy.security.LoginSuccessHandler;

/**
 * @apiNote OAuth2资源服务器,和认证服务器一样,只需要一个注解即可,他们可以放在一个类上
 * @apiNote 当使用授权码模式时,开启资源服务器,/oauth/authorize将不能访问
 * @author ParadiseWY
 * @date 2019年9月26日
 */
//@Configuration
@EnableResourceServer
public class OAuth2ResourcesServer extends ResourceServerConfigurerAdapter {

	@Autowired
	private UserProperties userProperties;

	@Autowired
	private LoginSuccessHandler loginSuccessHandler;

	@Autowired
	private LoginFailureHandler loginFailHandler;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(userProperties.getSecurity().getPermitSources())
				.permitAll().anyRequest().authenticated().and().formLogin()
				.loginProcessingUrl("/user/login").usernameParameter("username")
				.passwordParameter("password").successHandler(loginSuccessHandler)
				.failureHandler(loginFailHandler).and().csrf().disable();
	}
}