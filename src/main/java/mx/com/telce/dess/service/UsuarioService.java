package mx.com.telce.dess.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.com.telce.dess.model.Usuario;
import mx.com.telce.dess.repository.UsuarioRepository;


@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByNombre(nombre);
		
		if (usuario == null) return null;
		
		String userName = usuario.getNombre();
		String password = usuario.getPassword();
		
		return new User(userName, password, new ArrayList<>());
	}//SOBREESCRIBIR CLASE
	
}
