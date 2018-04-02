package service;

import model.Artist;
import model.Concert;
import repository.ConcertRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ConcertService extends Service<Integer,Concert> {
    private ConcertRepository repository;
    public ConcertService(ConcertRepository repository) {
        super(repository);
        this.repository=repository;
    }

    public ArrayList<Concert> getConcertsByArtist(Artist artist){
        return this.repository.getConcertsByArtist(artist.getId());
    }

    public ArrayList<Concert> getConcertsByDate(String date) {
        return this.repository.getConcertsByDate(date);
    }

    public ArrayList<Concert> getConcertsByArtistAndDate(Artist artist, String date) {
        return this.repository.getConcertsByArtistAndDate(artist.getId(),date);

    }
}
