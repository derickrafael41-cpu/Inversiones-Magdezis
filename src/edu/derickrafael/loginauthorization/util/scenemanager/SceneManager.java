package edu.derickrafael.loginauthorization.util.scenemanager;

import edu.derickrafael.loginauthorization.repository.auth.UsuarioRepository;
import edu.derickrafael.loginauthorization.service.auth.UsuarioService;
import edu.derickrafael.loginauthorization.controller.auth.UsuarioController;
import edu.derickrafael.loginauthorization.view.auth.LoginView;
import edu.derickrafael.loginauthorization.view.auth.RegisterView;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SceneManager {

    private final Stage stage;

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public void mostrarRegisterView() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(usuarioService, this);
        RegisterView registro = new RegisterView(usuarioController, this, this.stage);

        // Configura el estilo antes de mostrar la escena
        Scene scene = new Scene(registro.getRoot(), 600, 600);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void mostrarLoginView() {
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        UsuarioService usuarioService = new UsuarioService(usuarioRepository);
        UsuarioController usuarioController = new UsuarioController(usuarioService, this);
        LoginView login = new LoginView(usuarioController, this, this.stage);
        Scene scene = new Scene(login.getRoot(), 600, 600);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void mostrarAlerta(String titulo, String encabezado, String contenido) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(encabezado);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

}
