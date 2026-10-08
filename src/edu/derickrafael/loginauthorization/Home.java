package edu.derickrafael.loginauthorization;

import edu.derickrafael.loginauthorization.util.scenemanager.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class Home extends Application {

    private static Stage scenePrimary;

    @Override

    public void start(Stage scenePrimary) {
        this.scenePrimary = scenePrimary;
        scenePrimary.initStyle(javafx.stage.StageStyle.TRANSPARENT);
        scenePrimary.setTitle("Registrate");

        SceneManager manager = new SceneManager(scenePrimary);
        manager.mostrarRegisterView();
        scenePrimary.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
