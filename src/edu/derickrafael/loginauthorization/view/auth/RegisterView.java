package edu.derickrafael.loginauthorization.view.auth;

import edu.derickrafael.loginauthorization.model.auth.Usuarios;
import edu.derickrafael.loginauthorization.util.scenemanager.SceneManager;
import edu.derickrafael.loginauthorization.controller.auth.UsuarioController;
import java.util.UUID;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class RegisterView {

    private final UsuarioController autorizacionController;
    private final StackPane stackPaneRoot;
    private SceneManager sceneManager;

    private TextField txtIUsername;
    private TextField txtEmail;
    private PasswordField txtPassword;
    private Button btnRegistrar;
    private Button btnLogIn;
    private Stage stage;
    private double xOffset = 0;
    private double yOffset = 0;

    public RegisterView(UsuarioController autorizacionController, SceneManager sceneManager, Stage stage) {
        this.autorizacionController = autorizacionController;
        this.stage = stage;
        this.stackPaneRoot = new StackPane();
        initComponents();
        buildUi();
        configurarDrag();
        cargarEstilos();
    }

    private void resetStyles() {
        txtIUsername.getStyleClass().remove("text-field-error");
        txtEmail.getStyleClass().remove("text-field-error");
        txtPassword.getStyleClass().remove("text-field-error");
    }

    private void configurarDrag() {
        stackPaneRoot.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        stackPaneRoot.setOnMouseDragged(event -> {
            // Ahora stage no será null porque se asignó en el constructor
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

    private void initComponents() {
        txtIUsername = new TextField();
        txtIUsername.setPromptText("Ej. HideOnBush");
        txtIUsername.getStyleClass().add("text-field-custom");

        txtEmail = new TextField();
        txtEmail.setPromptText("ejemplo@correo.com");
        txtEmail.getStyleClass().add("text-field-custom");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.getStyleClass().add("text-field-custom");

        btnRegistrar = new Button("Registrarse");
        btnRegistrar.getStyleClass().add("button-primary");
        btnRegistrar.setOnAction(e -> registrarUsuario());

        btnLogIn = new Button("¿Ya tienes cuenta? Inicia Sesión");
        btnLogIn.getStyleClass().add("button-link"); // Estilo gestionado desde CSS
        btnLogIn.setOnAction(e -> autorizacionController.handleLoginView());
    }

    private void buildUi() {
        stackPaneRoot.getStyleClass().add("root-container");

        GridPane grid = new GridPane();
        grid.setHgap(15); // Un poco más de espacio horizontal
        grid.setVgap(15);
        grid.setPadding(new Insets(10, 20, 10, 20));
        grid.setAlignment(Pos.CENTER);

        Label lblUsername = new Label("Username:");
        lblUsername.getStyleClass().add("label-form");
        grid.add(lblUsername, 0, 0);
        grid.add(txtIUsername, 1, 0);

        Label lblEmail = new Label("Email:");
        lblEmail.getStyleClass().add("label-form");
        grid.add(lblEmail, 0, 2);
        grid.add(txtEmail, 1, 2);

        Label lblPassword = new Label("Contraseña:");
        lblPassword.getStyleClass().add("label-form");
        grid.add(lblPassword, 0, 3);
        grid.add(txtPassword, 1, 3);

        VBox mainLayout = new VBox(25); // Mayor espaciado interno
        mainLayout.getStyleClass().add("main-card");
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40, 35, 40, 35));

        // ---- AJUSTES CLAVE PARA GLASSMORPHISM ----
        mainLayout.setMaxWidth(420);  // Evita que la tarjeta se deforme horizontalmente
        mainLayout.setMinWidth(380);
        mainLayout.setMaxHeight(500); // Limita la altura máxima de la tarjeta
        // ------------------------------------------

        Label title = new Label("Registro de Nuevo Usuario");
        title.getStyleClass().add("view-title");

        VBox buttonContainer = new VBox(12);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(btnRegistrar, btnLogIn);

        mainLayout.getChildren().addAll(title, grid, buttonContainer);
        stackPaneRoot.getChildren().add(mainLayout);

        Button btnClose = new Button("X");
        btnClose.getStyleClass().add("button-close");
        btnClose.setOnAction(e -> System.exit(0));

// Agrégalo a un contenedor, por ejemplo:
        stackPaneRoot.getChildren().add(btnClose);
        StackPane.setAlignment(btnClose, javafx.geometry.Pos.TOP_RIGHT);

    }

    private void registrarUsuario() {
        resetStyles();

        if (txtIUsername.getText().trim().isEmpty()) {
            txtIUsername.getStyleClass().add("text-field-error");
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El nombre de usuario es obligatorio.");
            return;
        } else {
            txtIUsername.getStyleClass().add("text-field-custom");
        }
        if (txtPassword.getText().trim().isEmpty()) {
            txtPassword.getStyleClass().add("text-field-error");
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "La contraseña obligatoria.");
            return;
        } else {
            txtPassword.getStyleClass().add("text-field-custom");
        }
        if (txtEmail.getText().trim().isEmpty()) {
            txtEmail.getStyleClass().add("text-field-error");
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "El correo electrónico es obligatorio.");
            return;
        } else {
            txtEmail.getStyleClass().add("text-field-custom");
        }

        String username = txtIUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        // 1. Alerta si faltan datos
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, rellene todos los campos.");
            return;
        }

        Usuarios user = new Usuarios();
        user.setIdUsuario(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        boolean exito = autorizacionController.handleRegisterUser(
                user.getIdUsuario(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );

        if (exito) {
            // 2. Alerta de éxito
            mostrarAlerta(Alert.AlertType.INFORMATION, "Registro Completado", "Te has registrado con éxito!");
            limpiarCampos();

            // Opcional: Redirigir al login automáticamente después de registrarse con éxito
            autorizacionController.handleLoginView();
        } else {
            // 3. Alerta de error
            mostrarAlerta(Alert.AlertType.ERROR, "El registro ha fallado.", "Verifica los datos introducidos o intenta con otro usuario.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void cargarEstilos() {
        try {
            String cssPath = getClass().getResource("/edu/derickrafael/loginauthorization/assets/style.css").toExternalForm();
            stackPaneRoot.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.err.println("No se pudo encontrar el archivo style.css en la ruta '/edu/derickrafael/loginauthorization/assets/style.css'. Asegúrate de que esté en tu carpeta de recursos (resources).");
        }
    }

    private void limpiarCampos() {
        txtIUsername.clear();
        txtEmail.clear();
        txtPassword.clear();
    }

    public Parent getRoot() {
        return stackPaneRoot;
    }
}
