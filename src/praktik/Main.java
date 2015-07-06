package praktik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private FXMLLoader loader;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        loader = new FXMLLoader(getClass().getResource("view/main.fxml"));
        GridPane root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/praktik/resources/style.css");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("resources/icon.png")));
        primaryStage.setTitle("Praktik - Manage your search for job");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        ((MainController)loader.getController()).terminate();
    }
}
