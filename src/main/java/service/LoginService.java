package service;

import model.User;
import repository.Repository;
import repository.UserRepository;

import java.util.ArrayList;

public class LoginService {
    private UserRepository repository;

    public LoginService(UserRepository repository) {
        this.repository = repository;
    }

    public boolean login(String userName, String password) {
        return repository.login(userName,password);
    }
}
