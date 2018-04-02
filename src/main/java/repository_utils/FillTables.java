package repository_utils;

import model.*;
import repository.*;

public class FillTables {
    public static void fillRoles(RoleRepository repository){
        repository.save(new Role(0,"Employee"));
    }

    public static void fillUsers(UserRepository repository){
        repository.save(new User(0,"Ioan","Bogdan","ibir2121","ibir2121",1));
        repository.save(new User(0,"Ivanov","Alex","iair2124","iair2124",1));
        repository.save(new User(0,"Iova","Rares","irir2123","ibir2123",1));
        repository.save(new User(0,"Ionut","Andreea","iair2122","ibir2122",1));
    }

    public static void fillArtists(ArtistRepository repository){
        repository.save(new Artist(0,"Baniciu","Mircea"));
        repository.save(new Artist(0,"Alifantis","Nicu"));
        repository.save(new Artist(0,"Andries","Alexandru"));
        repository.save(new Artist(0,"Vintila","Mircea"));
    }

    public static void fillLocations(LocationRepository repository){
        repository.save(new Location(0,"Sala palatului",1000));
        repository.save(new Location(0,"Circul Globus",800));
        repository.save(new Location(0,"Cluj Arena",30000));
    }
    public static void fillConcerts(ConcertRepository repository){
        repository.save(new Concert(0,1,"2018-03-23",1,800,0));
        repository.save(new Concert(0,2,"2018-03-23",2,500,0));
        repository.save(new Concert(0,1,"2018-03-23",3,20000,0));
    }
    public static void fillTickets(TicketRepository repository){
        repository.save(new Ticket(0,1,3,"Bogdan"));
        repository.save(new Ticket(0,1,10,"Alex"));
        repository.save(new Ticket(0,2,1,"Paul"));
        repository.save(new Ticket(0,3,3,"Ioana"));
        repository.save(new Ticket(0,2,200,"Grigoreta"));
    }
}
