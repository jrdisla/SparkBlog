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
        this.sql2o = new Sql2o("jdbc:h2:~/test7","sa","");
        createtables();
    }

    public void createtables ()
    {

        String sql="CREATE TABLE IF NOT EXISTS articulos(id LONG PRIMARY KEY, titulo VARCHAR (1000),cuerpo VARCHAR (5000),username VARCHAR (50) REFERENCES usuarios(username),fecha VARCHAR(50),contenido VARCHAR(500000), tag VARCHAR(500));";

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


        String sql = "insert into articulos (id,titulo,cuerpo,username,fecha,contenido,tag) values(:id,:titulo,:cuerpo,:username,:fecha,:contenido,:tag)";
        try (Connection con = sql2o.open()) {

            con.createQuery(sql)

                    .addParameter("id",articulo.getId() )
                    .addParameter("titulo",articulo.getTitulo() )
                    .addParameter("cuerpo",articulo.getCuerpo())
                    .addParameter("username",articulo.getAutor())
                    .addParameter("fecha",articulo.getFecha())
                    .addParameter("contenido",contenido)
                    .addParameter("tag",articulo.getTag())
                    .executeUpdate();
        }
    }

    public void updateTag (Articulo articulo)
    {
        String updateSql = "update articulos set tag = :tag where id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)

                    .addParameter("tag",articulo.getTag())
                    .addParameter("id",articulo.getId())

                    .executeUpdate();
        }
    }
     public void updateCuerpo (Articulo articulo)
    {
        String updateSql = "update articulos set cuerpo = :cuerpo where id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)

                    .addParameter("cuerpo",articulo.getCuerpo())
                    .addParameter("id",articulo.getId())

                    .executeUpdate();
        }
    }

    public void updateTitulo (Articulo articulo)
    {
        String updateSql = "update articulos set titulo = :titulo where id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)


                    .addParameter("titulo",articulo.getTitulo())
                    .addParameter("id",articulo.getId())
                    .executeUpdate();
        }
    }

    public void deleteArticulo(long id) {
        try(Connection con = sql2o.open()) {
            String sql = "DELETE FROM articulos WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
