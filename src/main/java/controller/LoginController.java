package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Artist;
import model.Concert;
import model.Location;
import model.Ticket;
import repository.*;
import repository_utils.PropertiesForJDBC;
import service.ConcertService;
import service.LoginService;
import service.Service;

import java.util.Properties;

public class LoginController {
    @FXML
    TextField txtUser;
    @FXML
    PasswordField txtPassword;
    @FXML
    Label labelCredentials;

    Stage loginStage;

    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    private LoginService service;

    public void setService(LoginService service) {
        this.service = service;
        this.labelCredentials.setVisible(false);
    }

    public void login(MouseEvent event) {
        if (service.login(txtUser.getText(), txtPassword.getText())) {
            goToMainAppView();
            labelCredentials.setVisible(false);
        } else
            labelCredentials.setVisible(true);

    }

    private void goToMainAppView() {
        try {
            Properties serverProps = PropertiesForJDBC.getProperties();
            ArtistRepository artistRepository=new ArtistRepository(serverProps);
            ConcertRepository concertRepository=new ConcertRepository(serverProps);
            LocationRepository locationRepository=new LocationRepository(serverProps);
            TicketRepository ticketRepository=new TicketRepository(serverProps);

            Service<Integer,Artist> artistService=new Service<>(artistRepository);
            ConcertService concertService=new ConcertService(concertRepository);
            Service<Integer,Location> locationService=new Service<>(locationRepository);
            Service<Integer,Ticket> ticketService=new Service<>(ticketRepository);


            FXMLLoader loader = new FXMLLoader();
            String resource = "/views/mainAppView.fxml";
            loader.setLocation(getClass().getResource(resource));
            AnchorPane pane = (AnchorPane) loader.load();
            MainAppController controller = loader.getController();
            controller.setArtistService(artistService);
            controller.setConcertService(concertService);
            controller.setLocationService(locationService);
            controller.setLoginStage(loginStage);
            controller.setTicketService(ticketService);
            Stage stage=new Stage();
            controller.setThisStage(stage);
            stage.setScene(new Scene(pane));
            stage.setTitle("Bilete");
            loginStage.close();
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
