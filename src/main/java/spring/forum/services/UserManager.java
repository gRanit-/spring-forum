package spring.forum.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.forum.models.User;
import spring.forum.repositories.UserDAO;

public class UserManager{

    private UserDAO userDAO;

    @Autowired
    public UserManager(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserManager() {
    }

  
    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }


    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

 
    @Transactional
    public void deleteUser(long userId) {
        userDAO.deleteUser(userId);
    }

    @Transactional
    
    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }

    @Transactional
    
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    @Transactional
    
    public void updateUserPassword(String login, String newPassword) {
        userDAO.updateUserPassword(login, newPassword);
    }

    @Transactional
    
    public void updateUserNickname(String login, String newNickname) {
        userDAO.updateUserNickname(login, newNickname);
    }




}
