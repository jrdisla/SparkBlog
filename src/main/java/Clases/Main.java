package Clases;

import DataBaseManager.ArticuloDao;
import DataBaseManager.CommentDao;
import DataBaseManager.TagDao;
import DataBaseManager.UsuarioDao;
import TemplatesHandler.ManejadorTemp;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by jrdis on 10/6/2017.
 */
public class Main {
    public static void main(String[] args) {

        Preferences user_pre = Preferences.userRoot();
        Boolean isFirstRun = user_pre.getBoolean("first_run", true);
        System.out.println(isFirstRun);
        if (isFirstRun) {
            System.out.println("running for the first time");
            UsuarioDao usuarioDao = new UsuarioDao();
            TagDao tagDao = new TagDao();
            ArticuloDao articuloDao = new ArticuloDao();
            CommentDao commentDao = new CommentDao();
            Usuario usuario = new Usuario("admin", "admin", "admin", true, true);
            usuarioDao.inserIntoUsers(usuario);
            user_pre.putBoolean("first_run", false);
        }
        Spark.staticFileLocation("/public");
        enableDebugScreen();
        new ManejadorTemp().startApp();





    }
}
