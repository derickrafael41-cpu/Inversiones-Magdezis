package edu.derickrafael.loginauthorization.view.auth;

import edu.derickrafael.loginauthorization.util.scenemanager.SceneManager;
import edu.derickrafael.loginauthorization.controller.auth.UsuarioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LoginView {

    private final UsuarioController autorizacionController;
    private final StackPane stackPaneRoot;
    private double xOffset = 0;
    private double yOffset = 0;
    private TextField txtEmail;
    private PasswordField txtPassword;
    private Button btnLogin;
    private Button btnIrARegistro;
    private final Stage stage;

    public LoginView(UsuarioController autorizacionController, SceneManager sceneManager, Stage stage) {
        this.autorizacionController = autorizacionController;
        this.stage = stage;
        this.stackPaneRoot = new StackPane();
        initComponents();
        buildUi();
        cargarEstilos(); // Carga de forma segura el archivo style.css compartido
        configurarDrag();

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
        txtEmail = new TextField();
        txtEmail.setPromptText("ejemplo@correo.com");
        txtEmail.getStyleClass().add("text-field-custom");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");
        txtPassword.getStyleClass().add("text-field-custom");

        btnLogin = new Button("Iniciar Sesión");
        btnLogin.getStyleClass().add("button-primary");
        // Conectado al flujo lógico de autenticación real
        btnLogin.setOnAction(e -> iniciarSesion());

        btnIrARegistro = new Button("¿No tienes cuenta? Regístrate");
        btnIrARegistro.getStyleClass().add("button-link"); // Estilo gestionado desde el CSS
        btnIrARegistro.setOnAction(e -> navegarARegistro());
    }

    private void buildUi() {
        stackPaneRoot.getStyleClass().add("root-container");
        Button btnClose = new Button("X");
        btnClose.getStyleClass().add("button-close");
        btnClose.setOnAction(e -> System.exit(0));

// Agrégalo a un contenedor, por ejemplo:   
        stackPaneRoot.getChildren().add(btnClose);
        StackPane.setAlignment(btnClose, javafx.geometry.Pos.TOP_RIGHT);
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setPadding(new Insets(10, 20, 10, 20));
        grid.setAlignment(Pos.CENTER);

        Label lblEmail = new Label("Email:");
        lblEmail.getStyleClass().add("label-form");
        grid.add(lblEmail, 0, 0);
        grid.add(txtEmail, 1, 0);

        Label lblPassword = new Label("Contraseña:");
        lblPassword.getStyleClass().add("label-form");
        grid.add(lblPassword, 0, 1);
        grid.add(txtPassword, 1, 1);

        VBox mainLayout = new VBox(25);
        mainLayout.getStyleClass().add("main-card");
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(40, 35, 40, 35));

        // ---- AJUSTES DE TAMAÑO PARA GLASSMORPHISM ----
        mainLayout.setMaxWidth(420);
        mainLayout.setMinWidth(380);
        mainLayout.setMaxHeight(450); // Ligeramente más compacto que la vista de registro
        // ----------------------------------------------

        Label title = new Label("Inicio de Sesión");
        title.getStyleClass().add("view-title");

        VBox buttonContainer = new VBox(12);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(btnLogin, btnIrARegistro);

        mainLayout.getChildren().addAll(title, grid, buttonContainer);
        stackPaneRoot.getChildren().add(mainLayout);
    }

    private void resetStyles() {
        txtEmail.getStyleClass().remove("text-field-error");
        txtPassword.getStyleClass().remove("text-field-error");
    }

    private void iniciarSesion() {
        resetStyles();
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

        String email = txtEmail.getText().trim();
        String passwordPlain = txtPassword.getText().trim();

        if (email.isEmpty() || passwordPlain.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Por favor, rellene todos los campos.");
            return;
        }

        String hashedPassword = autorizacionController.handleLogin(email);

        if (hashedPassword != null && verificarPassword(passwordPlain, hashedPassword)) {
            // Obtenemos el nombre ANTES de cerrar la ventana
            String nombreUsuario = autorizacionController.handleGetNameByEmail(email);

            // Pasamos el nombre al método de bienvenida
            mostrarBienvenida(nombreUsuario);
            limpiarCampos();
        } else {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Email o contraseña incorrectos.");
        }
    }

    private boolean verificarPassword(String passwordPlain, String hashedPassword) {
        return passwordPlain.equals(hashedPassword);
    }

    private void navegarARegistro() {
        autorizacionController.handleRegistroView();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtEmail.clear();
        txtPassword.clear();
    }

    private void cargarEstilos() {
        try {
            String cssPath = getClass().getResource("/edu/derickrafael/loginauthorization/assets/style.css").toExternalForm();
            stackPaneRoot.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.err.println("No se pudo encontrar el archivo style.css en la ruta '/edu/derickrafael/loginauthorization/assets/style.css'. Asegúrate de que esté en tu carpeta de recursos (resources).");
        }
    }

    public void mostrarBienvenida(String nombre) {
        Stage bienvenidaStage = new Stage();
        bienvenidaStage.initStyle(javafx.stage.StageStyle.UNDECORATED);

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #071f0d; -fx-border-color: #39ff14; -fx-border-width: 2;");
        Label lbl = new Label("Bienvenido, " + nombre);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

        root.getChildren().add(lbl);

        bienvenidaStage.setScene(new Scene(root, 300, 150));
        bienvenidaStage.show();

        // Cerrar el login actual
        stage.close();
    }

    public Parent getRoot() {
        return stackPaneRoot;
    }
    
}
