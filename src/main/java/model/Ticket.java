package model;

public class Ticket implements HasId<Integer> {
    private int id;
    private int idConcert;
    private int placesBought;
    private String buyer;

    public Ticket(int id, int idConcert, int placesBought, String buyer) {
        this.id = id;
        this.idConcert = idConcert;
        this.placesBought = placesBought;
        this.buyer = buyer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConcert() {
        return idConcert;
    }

    public void setIdConcert(int idConcert) {
        this.idConcert = idConcert;
    }

    public int getPlacesBought() {
        return placesBought;
    }

    public void setPlacesBought(int placesBought) {
        this.placesBought = placesBought;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Nume si prenume cumparator:"+buyer+"/n Locuri cumparate:"+placesBought;
    }
}
