package services;

import common.Credentials;
import db.managers.UserManager;
import entities.Role;
import entities.User;

public class UserService {
  public static void createUser(Credentials credentials, String name) throws Exception {
    User user = new User();

    String login = credentials.getLogin();
    String password = credentials.getPassword();

    user.setName(name);
    user.setLogin(login);
    user.setPassword(HashService.hash(password));
    user.setRole(Role.OPERATOR);

    UserManager.getInstance().create(user);
  }

  public static void updateUser(User user) throws Exception {
    UserManager.getInstance().update(user);
  }
}
