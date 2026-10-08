package edu.derickrafael.loginauthorization.controller.auth;

import edu.derickrafael.loginauthorization.model.auth.Usuarios;
import edu.derickrafael.loginauthorization.service.auth.UsuarioService;
import edu.derickrafael.loginauthorization.util.scenemanager.SceneManager;

public class UsuarioController {

    private UsuarioService userService;
    private SceneManager stage;

    public UsuarioController(UsuarioService userService, SceneManager stage) {
        this.userService = userService;
        this.stage = stage;
    }

    public boolean handleRegisterUser(String idUser, String username, String email, String password) {
        return userService.register(idUser, username, email, password);
    }
    
    public void handleRegistroView() {
        stage.mostrarRegisterView();
    }

    public String handleLogin(String email) {
        return userService.login(email);
    }
    
    public void handleLoginView() {
        stage.mostrarLoginView();
    }
    
    public void handleMostrarAlerta(String titulo, String encabezado, String contenido) {
        stage.mostrarAlerta(titulo, encabezado, contenido);
    }
    
    public String handleGetNameByEmail(String email) {
        return userService.getUsernameByEmail(email);
    }
    
}
