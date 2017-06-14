package Clases;

import DataBaseManager.ArticuloDao;
import DataBaseManager.CommentDao;
import DataBaseManager.TagDao;
import DataBaseManager.UsuarioDao;
import TemplatesHandler.ManejadorTemp;
import spark.Spark;

import java.util.ArrayList;
import java.util.List;

import static spark.debug.DebugScreen.enableDebugScreen;

/**
 * Created by jrdis on 10/6/2017.
 */
public class Main {
    public static void main(String[] args) {

        UsuarioDao usuarioDao = new UsuarioDao();
        TagDao tagDao = new TagDao();
        ArticuloDao articuloDao = new ArticuloDao();
        CommentDao commentDao = new CommentDao();

        Usuario usuario = new Usuario("admin", "admin", "admin", true, true);
        Tag tag = new Tag(1,"holaa");

        ArrayList<Tag> tags = new ArrayList<>();
        ArrayList<Comentario> comentarios = new ArrayList<>();

        Articulo articulo = new Articulo(1, "Hola", "Si", usuario.getUsername(),"02", comentarios,tags);
        Articulo articulo1 = new Articulo(2, "Hola", "Si", usuario.getUsername(),"02", comentarios,tags);
        Articulo articulo2 = new Articulo(3, "Hola", "Si", usuario.getUsername(),"02", comentarios,tags);
        Articulo articulo3 = new Articulo(4, "Hola", "Si", usuario.getUsername(),"02", comentarios,tags);
        Articulo articulo4 = new Articulo(5, "Hola", "Si", usuario.getUsername(),"02", comentarios,tags);

        Comentario comentario = new Comentario(1, "hola que tal", usuario, articulo);

       comentarios.add(comentario);

       tags.add(tag);

      usuarioDao.inserIntoUsers(usuario);
      tagDao.inserIntoTags(tag);
       articuloDao.inserIntoArticulos(articulo);
       articuloDao.inserIntoArticulos(articulo1);
        articuloDao.inserIntoArticulos(articulo2);
        articuloDao.inserIntoArticulos(articulo3);
        articuloDao.inserIntoArticulos(articulo4);
     commentDao.inserIntoTags(comentario);


        //Spark.staticFileLocation("/public");
       // enableDebugScreen();

      // new ManejadorTemp().startApp();

    }
}
