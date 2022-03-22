package mx.com.telce.dess.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mx.com.telce.dess.model.Usuario;


@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	
	Usuario findByNombre(String nombre);

}
