package test;

import data.User;
import data.UserDAO;
import data.UserDAOImp;

import java.util.Collection;

public class TestHib {
    public static void main(String[] args){
        UserDAO userdao = new UserDAOImp();
        Collection<User> collection = userdao.getUsersAll();
        System.out.println(collection.toString());
    }
}
