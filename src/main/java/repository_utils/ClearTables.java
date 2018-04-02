package repository_utils;

import model.*;
import repository.*;

import java.util.ArrayList;

public class ClearTables {
    public static void clearRoles(RoleRepository repository) {
        repository.save(new Role(0, "Employee"));
    }

    public static void clearUsers(UserRepository repository) {
        ArrayList<User> allUsers = (ArrayList<User>) repository.getAll();
        for (User user : allUsers) {
            repository.delete(user);
        }
    }

    public static void clearArtists(ArtistRepository repository) {
        ArrayList<Artist> allArtist = (ArrayList<Artist>) repository.getAll();
        for (Artist artist : allArtist) {
            repository.delete(artist);
        }
    }

    public static void clearLocations(LocationRepository repository) {
        ArrayList<Location> allLocations = (ArrayList<Location>) repository.getAll();
        for (Location location : allLocations)
            repository.delete(location);
    }

    public static void clearConcerts(ConcertRepository repository) {
        ArrayList<Concert> allConcerts = (ArrayList<Concert>) repository.getAll();
        for (Concert concert : allConcerts)
            repository.delete(concert);
    }

    public static void clearTickets(TicketRepository repository) {
        ArrayList<Ticket> allTickets = (ArrayList<Ticket>) repository.getAll();
        for (Ticket ticket : allTickets)
            repository.delete(ticket);
    }
}
