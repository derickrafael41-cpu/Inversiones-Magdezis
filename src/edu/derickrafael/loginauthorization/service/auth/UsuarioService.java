package edu.derickrafael.loginauthorization.service.auth;

import edu.derickrafael.loginauthorization.model.auth.Usuarios;
import edu.derickrafael.loginauthorization.repository.auth.UsuarioRepository;

public class UsuarioService {
    
    UsuarioRepository userRepo;
    
    public UsuarioService(UsuarioRepository userRespo) {
        this.userRepo = userRespo;
    }
    
    public boolean register(String idUser, String username, String email, String password) {
        return userRepo.registerUser(idUser, username, email, password);
    }
    
    public String login(String email) {
        return userRepo.findUserByEmail(email);
    }
    
    public String getUsernameByEmail(String email) {
        Usuarios usuario =userRepo.findByEmail(email);
        return (usuario != null) ? usuario.getUsername() : null;
    }
    
}
