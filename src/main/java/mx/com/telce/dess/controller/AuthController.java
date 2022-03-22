package mx.com.telce.dess.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mx.com.telce.dess.model.AuthenticationRequest;
import mx.com.telce.dess.model.AuthenticationResponse;
import mx.com.telce.dess.model.Usuario;
import mx.com.telce.dess.repository.UsuarioRepository;
import mx.com.telce.dess.service.UsuarioService;
import mx.com.telce.dess.utils.JwtUtil;


@RestController
public class AuthController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@GetMapping("/dashboard")
	private String pruebaToken(){
		return "Bienvenido a nuestra APP " + SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	@PostMapping("/registrar")
	private ResponseEntity<?> registrarUsuario(@RequestBody AuthenticationRequest authenticationRequest){
		String nombre = authenticationRequest.getNombre();
		String password = authenticationRequest.getPassword();
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setPassword(password);
		
		try {
			usuarioRepository.save(usuario);
		} catch (Exception ex) {
			return ResponseEntity.ok(new AuthenticationResponse("Error durante el registro de usuario " + nombre));
		}
		return ResponseEntity.ok(new AuthenticationResponse("Usuario registrado " + nombre));
	}

	@PostMapping("/autenticar")
	private ResponseEntity<?> autenticarUsuario(@RequestBody AuthenticationRequest authenticationRequest){
		String nombre = authenticationRequest.getNombre();
		String password = authenticationRequest.getPassword();
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(nombre, password));
		} catch (Exception ex) {
			return ResponseEntity.ok(new AuthenticationResponse("Error durante la autenticacion del usuario " + nombre));
		}
		
//		UserDetails loadUser = usuarioService.loadUserByUsername(nombre);
//		
//		String generatedToken = jwtUtil.generateToken(loadUser);
		
		return ResponseEntity.ok(new AuthenticationResponse("Usuario autenticado: " + nombre  /*generatedToken*/));
		
	}
}
