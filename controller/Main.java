package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main is the program's starting point, Javadoc folder directory is "\FryeMelissaC482\Javadocs" A FUTURE ENHANCEMENT
 * would connect the inventory value of a part to a Product's associated parts list to let the end user know how many
 * Products could be made from the Part quantities on-hand and could also be used to suggest Product's retail price based on total part cost.
 */
public class Main extends Application {

    /**
     * This method sets the primary stage used to display the application's GUI.
     * @param primaryStage This stage is used to display the different scenes of the application.
     * @throws Exception When specified fxml file can't be found.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        primaryStage.setTitle("FryeCo. Software         Inventory Management System         Main Menu");
        primaryStage.setScene(new Scene(root, 1400, 675));
        primaryStage.show();
    }

    /**
     * This method launches the application.
     * @param args Arguments.
     */
    public static void main(String[] args) {

        launch(args);
    }
}
