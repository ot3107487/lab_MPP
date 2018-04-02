import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;
import repository.*;
import repository_utils.ClearTables;
import repository_utils.FillTables;
import repository_utils.PropertiesForJDBC;
import service.LoginService;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties serverProps= PropertiesForJDBC.getProperties();
        UserRepository userRepository = new UserRepository(serverProps);
        LoginService loginService = new LoginService(userRepository);
        FXMLLoader loader = new FXMLLoader();
        String resource = "/views/loginView.fxml";
        loader.setLocation(getClass().getResource(resource));
        AnchorPane pane = (AnchorPane) loader.load();
        LoginController controller = loader.getController();
        controller.setService(loginService);
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(pane));
        controller.setLoginStage(stage);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
