package mx.com.telce.dess.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import mx.com.telce.dess.service.JwtFiltro;
import mx.com.telce.dess.service.UsuarioService;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JwtFiltro jwtFilterRequest;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/registrar", "/autenticar").permitAll().anyRequest().authenticated(); /*.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);*/
	//	http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
}
