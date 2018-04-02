package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Artist;
import model.Concert;
import model.Location;
import model.Ticket;
import service.ConcertService;
import service.Service;
import utils.ListEvent;
import utils.Observer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainAppController implements Observer<Concert> {
    @FXML
    TableView tableArtists;
    @FXML
    TableColumn columnArtistFirstName;
    @FXML
    TableColumn columnArtistLastName;

    @FXML
    TableView tableConcerts;
    @FXML
    TableColumn columnConcertDate;
    @FXML
    TableColumn columnConcertLocation;
    @FXML
    TableColumn columnConcertNumberOfTickets;
    @FXML
    TableColumn columnConcertSoldTickets;

    @FXML
    TableView tableLocations;
    @FXML
    TableColumn columnLocationId;
    @FXML
    TableColumn columnLocationName;

    @FXML
    TextField filterDate;
    @FXML
    CheckBox checkFilter;

    @FXML
    TextField textNume;
    @FXML
    TextField textPrenume;
    @FXML
    TextField textNrBilete;
    @FXML
    Label labelNrBilete;

    Stage loginStage;
    Stage thisStage;

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    Service<Integer, Artist> artistService;
    ConcertService concertService;
    Service<Integer, Location> locationService;
    Service<Integer, Ticket> ticketService;

    private ObservableList<Artist> modelArtists;
    private ObservableList<Concert> modelConcerts;
    private ObservableList<Location> modelLocations;

    public void setArtistService(Service<Integer, Artist> artistService) {
        this.artistService = artistService;
        ArrayList<Artist> artists = artistService.getAll();
        modelArtists = FXCollections.observableArrayList(artists);
        tableArtists.setItems(modelArtists);
    }

    public void setConcertService(ConcertService concertService) {
        this.concertService = concertService;
        this.concertService.addObserver(this);
        modelConcerts=FXCollections.observableArrayList(concertService.getAll());
        tableConcerts.setItems(modelConcerts);
    }

    public void setLocationService(Service<Integer, Location> locationService) {
        this.locationService = locationService;
        ArrayList<Location> locations = locationService.getAll();
        modelLocations = FXCollections.observableArrayList(locations);
        tableLocations.setItems(modelLocations);
    }

    public void setTicketService(Service<Integer, Ticket> ticketService) {
        this.ticketService = ticketService;
    }


    public void setLoginStage(Stage loginStage) {
        this.loginStage = loginStage;
    }

    @FXML
    public void initialize() {
        //artisti
        columnArtistFirstName.setCellValueFactory(new PropertyValueFactory<Artist, String>("firstName"));
        columnArtistLastName.setCellValueFactory(new PropertyValueFactory<Artist, String>("lastName"));
        //concerte
        columnConcertDate.setCellValueFactory(new PropertyValueFactory<Concert, String>("date"));
        columnConcertLocation.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("idLocation"));
        columnConcertNumberOfTickets.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("numberOfTickets"));
        columnConcertSoldTickets.setCellValueFactory(new PropertyValueFactory<Concert, Integer>("soldTickets"));
        //legenda locatii
        columnLocationId.setCellValueFactory(new PropertyValueFactory<Location, Integer>("id"));
        columnLocationName.setCellValueFactory(new PropertyValueFactory<Location, String>("name"));


        tableConcerts.setRowFactory(tv -> new TableRow<Concert>() {
            @Override
            public void updateItem(Concert item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (item.getNumberOfTickets() == item.getSoldTickets()) {
                    setStyle("-fx-background-color: tomato;");
                } else {
                    setStyle("");
                }
            }
        });
        labelNrBilete.setVisible(false);
    }

    public void getConcertsOfArtist(MouseEvent event) {
        Artist artist = (Artist) tableArtists.getSelectionModel().getSelectedItem();
        if (checkFilter.isSelected()) {
            modelConcerts = FXCollections.observableArrayList(concertService.getConcertsByArtistAndDate(artist, filterDate.getText()));
        } else
            modelConcerts = FXCollections.observableArrayList(concertService.getConcertsByArtist(artist));
        tableConcerts.setItems(modelConcerts);
    }

    public void getArtistByDate(MouseEvent event) {
        String date = filterDate.getText();
        ArrayList<Concert> concertInThatDate = concertService.getConcertsByDate(date);
        Set<Artist> artists = new HashSet<>();
        for (Concert concert : concertInThatDate)
            artists.add(artistService.findById(concert.getIdArtist()));
        ArrayList<Artist> finalArtists = new ArrayList<>();
        finalArtists.addAll(artists);
        modelArtists = FXCollections.observableArrayList(finalArtists);
        tableArtists.setItems(modelArtists);


    }

    public void printTicket(MouseEvent event) {
        Concert concert = (Concert) tableConcerts.getSelectionModel().getSelectedItem();
        if (concert != null) {
            labelNrBilete.setVisible(false);
            String buyer = textNume.getText() + ' ' + textPrenume.getText();
            int nrLocuri = Integer.parseInt(textNrBilete.getText());
            int locuriInTotal = concert.getNumberOfTickets();
            int locuriCumparate = concert.getSoldTickets();
            if (locuriInTotal - locuriCumparate < nrLocuri)
                labelNrBilete.setVisible(true);
            else {
                Ticket ticket = new Ticket(0, concert.getId(), nrLocuri, buyer);
                ticketService.save(ticket);
                concert.setSoldTickets(locuriCumparate + nrLocuri);
                concertService.put(concert);
                System.out.println("Ticket cumparat cu succes");
                System.out.println(ticket);
            }
        }
    }

    @Override
    public void notifyEvent(ListEvent<Concert> e) {
        modelConcerts.setAll(StreamSupport.stream(e.getList().spliterator(), false)
                .collect(Collectors.toList()));
    }

    public void logout(MouseEvent event) {
        thisStage.close();
        loginStage.show();
    }
}
