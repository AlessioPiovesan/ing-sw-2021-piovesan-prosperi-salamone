package it.polimi.ingsw;

import it.polimi.ingsw.clients.viewer.controllers.ResizeHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Objects;

/**
 * Entry point for the graphical user interface of Masters of Renaissance.
 * Displays the main menu and allows the user to choose between:
 * - Single Player
 * - Multiplayer
 * - Exit
 *
 * @author Alessio Piovesan
 * @author Pier Giorgio Prosperi
 * @author Matteo Salamone
 * @see Application
 */
public class MastersOfRenaissanceGUI extends Application {

    private static final String MENU = "/fxml/mainMenu.fxml";
    private static final String FONT_PATH = "/font/Academy Engraved LET Fonts.ttf";
    private static Scene currentScene;

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    /**
     * Starts the scene of the Main Menu and shows it to the User.
     *
     * @author Alessio Piovesan
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Load FXML
        var root = (Parent) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(MENU)));
        try (InputStream fontStream = getClass().getResourceAsStream(FONT_PATH)) {
            if (fontStream != null) {
                Font.loadFont(fontStream, 25);
            } else {
                System.err.println("Warning: Could not load font at " + FONT_PATH);
            }
        }
        // Setup the scene
        newScene(root);
        stage.setTitle("Masters Of Renaissance");
        stage.setScene(currentScene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.show();
        // Attach resize handlers
        var mainPane = (AnchorPane) currentScene.lookup("#mainPane");
        if (mainPane != null) {
            var resize = new ResizeHandler(mainPane);
            currentScene.widthProperty().addListener(resize.getWidthListener());
            currentScene.heightProperty().addListener(resize.getHeightListener());
        } else {
            System.err.println("Warning: #mainPane not found in " + MENU);
        }
    }

    private static void newScene(Parent root) {
        currentScene = new Scene(root);
    }
}
