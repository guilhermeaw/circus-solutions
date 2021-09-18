package services;

import common.Credentials;
import db.managers.UserManager;
import entities.User;
import utils.ApplicationUtilities;
import validators.EmptyValidator;

public class LoginService {
  public static void doLogin(Credentials credentials) throws Exception {
    String login = credentials.getLogin();
    String password = credentials.getPassword();

    if (!EmptyValidator.validate(login) || !EmptyValidator.validate(password)) {
        throw new Exception("É necessário preencher os campos de login e senha");
    }

    User user = UserManager.getInstance().getByLoginAndPassword(login, HashService.hash(password));

    if (user != null) {
        ApplicationUtilities.getInstance().setActiveUser(user);
    } else {
        throw new Exception("Usuário ou senha incorretos");
    }
}

  public static void doLogout() {
    ApplicationUtilities.getInstance().setActiveUser(null);
    new SceneChangeService().changeSceneTo("/views/login.fxml");
  }
}
