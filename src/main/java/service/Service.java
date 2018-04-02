package service;

import model.HasId;
import repository.Repository;
import utils.ListEvent;
import utils.ListEventType;
import utils.Observable;
import utils.Observer;

import java.util.ArrayList;

public class Service<Id, T extends HasId<Id>> implements Observable<T> {
    protected Repository<Id, T> repository;
    ArrayList<Observer<T>> observers = new ArrayList<>();

    public Service(Repository<Id, T> repository) {
        this.repository = repository;
    }

    public void save(T elem) {
        repository.save(elem);
        ListEvent<T> ev = createEvent(ListEventType.ADD, elem, getAll());
        notifyObservers(ev);
    }

    public int size() {
        return repository.size();
    }


    public void delete(T elem) {
        repository.delete(elem);
        ListEvent<T> ev = createEvent(ListEventType.REMOVE, elem, getAll());
        notifyObservers(ev);
    }

    public void put(T elem) {
        repository.put(elem);
        ListEvent<T> ev = createEvent(ListEventType.UPDATE, elem, getAll());
        notifyObservers(ev);
    }

    public ArrayList<T> getAll() {
        return (ArrayList<T>) repository.getAll();
    }

    public T findById(Id id) {
        return repository.findById(id);
    }

    @Override
    public void addObserver(Observer<T> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<T> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(ListEvent<T> event) {
        observers.forEach(x -> x.notifyEvent(event));
    }

    private ListEvent<T> createEvent(ListEventType type, final T elem, final Iterable<T> l) {
        return new ListEvent<T>(type) {
            @Override
            public Iterable<T> getList() {
                return l;
            }

            @Override
            public T getElement() {
                return elem;
            }
        };
    }
}
