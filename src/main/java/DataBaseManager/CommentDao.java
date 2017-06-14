package DataBaseManager;

import Clases.Comentario;
import Clases.Tag;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Created by jrdis on 10/6/2017.
 */
public class CommentDao {

    private Sql2o sql2o;

    public CommentDao() {
        this.sql2o = new Sql2o("jdbc:h2:~/test","sa","");
        createtables();
    }
    public void createtables ()
    {
        String sql="CREATE TABLE IF NOT EXISTS comments(id INT PRIMARY KEY, comment VARCHAR (1000) NOT NULL,Usuario VARCHAR (50),ARTICULO INT);";

        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
    public List<Comentario> getAllComments(){
        String sql="select * from comments";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Comentario.class);
        }
    }

    public void inserIntoTags(Comentario comment){
        String sql = "insert into comments (id, comment,Usuario,Articulo) values(:id,:comment,:Usuario,:Articulo)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", comment.getId())
                    .addParameter("comment", comment.getComment())
                    .addParameter("Usuario",comment.getAutor().getUsername())
                    .addParameter("Articulo",comment.getArticulo().getId())
                    .executeUpdate();
        }
    }
    public void updateComment (Comentario comment)
    {
        String updateSql = "update comments set comment = :comment where id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)

                    .addParameter("comment", comment.getComment())
                    .addParameter("id", comment.getId())

                    .executeUpdate();
        }
    }
    public void deleteComment(int id) {
        try(Connection con = sql2o.open()) {
            String sql = "DELETE FROM comments WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }
}
