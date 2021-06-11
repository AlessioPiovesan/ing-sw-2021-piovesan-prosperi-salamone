package it.polimi.ingsw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class MastersOfRenaissanceGUI extends Application{

    private static final String SETUP = "src/main/resources/fxml/setup.fxml";
    private static final String MENU = "src/main/resources/fxml/mainMenu.fxml";
    private static final String LOADER = "src/main/resources/fxml/loadingScreen.fxml";

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        var root = (Parent)FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MENU)));

        var mainScene = new Scene(root);

        stage.setTitle("Masters Of Renaissance");
        stage.setScene(mainScene);
        stage.show();
    }
}
