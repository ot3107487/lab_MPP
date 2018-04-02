package model;

public class Concert implements HasId<Integer>{
    private int id;
    private int idArtist;
    private String date;
    private int idLocation;
    private int numberOfTickets;
    private int soldTickets;

    public Concert(int id, int idArtist, String date, int idLocation, int numberOfTickets, int soldTickets) {
        this.id = id;
        this.idArtist = idArtist;
        this.date = date;
        this.idLocation = idLocation;
        this.numberOfTickets = numberOfTickets;
        this.soldTickets = soldTickets;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }
}
