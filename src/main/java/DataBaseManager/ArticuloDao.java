package DataBaseManager;

import Clases.Articulo;
import Clases.Comentario;
import Clases.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Created by jrdis on 10/6/2017.
 */
public class ArticuloDao {

    private Sql2o sql2o;

    public ArticuloDao() {
        this.sql2o = new Sql2o("jdbc:h2:~/test","sa","");
        createtables();
    }

    public void createtables ()
    {

        String sql="CREATE TABLE IF NOT EXISTS articulos(id LONG PRIMARY KEY, titulo VARCHAR (1000),cuerpo VARCHAR (5000),username VARCHAR (50) REFERENCES usuarios(username),fecha VARCHAR(50),contenido VARCHAR(500000));";

        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
   public List<Articulo> getAllArticulos(){
        String sql="select * from articulos";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Articulo.class);
        }
    }

    public void inserIntoArticulos(Articulo articulo){
       String contenido = "";
        for (Comentario item : articulo.getComment())
        {
            contenido += item.getComment();
       }


        String sql = "insert into articulos (id,titulo,cuerpo,username,fecha,contenido) values(:id,:titulo,:cuerpo,:username,:fecha,:contenido)";
        try (Connection con = sql2o.open()) {

            con.createQuery(sql)

                    .addParameter("id",articulo.getId() )
                    .addParameter("titulo",articulo.getTitulo() )
                    .addParameter("cuerpo",articulo.getCuerpo())
                    .addParameter("username",articulo.getAutor())
                    .addParameter("fecha",articulo.getFecha())
                    .addParameter("contenido",contenido)
                    .executeUpdate();
        }
    }

    /* public void updateName (Usuario user)
    {
        String updateSql = "update usuarios set nombre = :nombre where username = :username";

        try (Connection con = sql2o.open()) {
            System.out.println(user.getPassword());
            con.createQuery(updateSql)

                    .addParameter("nombre", user.getNombre())
                    .addParameter("username",user.getUsername() )

                    .executeUpdate();
        }
    }

    public void updatePassword (Usuario user)
    {
        String updateSql = "update usuarios set password = :password where username = :username";

        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)


                    .addParameter("password",user.getPassword())
                    .addParameter("username",user.getUsername() )
                    .executeUpdate();
        }
    }

    public void deleteComment(String username) {
        try(Connection con = sql2o.open()) {
            String sql = "DELETE FROM usuarios WHERE username = :username;";
            con.createQuery(sql)
                    .addParameter("username", username)
                    .executeUpdate();
        }
    }*/
}
