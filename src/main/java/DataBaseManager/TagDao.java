package DataBaseManager;

import Clases.Tag;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.security.PrivateKey;
import java.util.List;

/**
 * Created by jrdis on 10/6/2017.
 */
public class TagDao {
    private Sql2o sql2o;

    public TagDao() {
        this.sql2o = new Sql2o("jdbc:h2:~/test7","sa","");
        createtables();
    }
    public void createtables ()
    {
        String sql="CREATE TABLE IF NOT EXISTS tags(id INT PRIMARY KEY, tag VARCHAR(255));";
        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
    public List<Tag> getAllTags(){
        String sql="select * from tags";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql).executeAndFetch(Tag.class);
        }
    }

    public void inserIntoTags(Tag tag){
        String sql = "insert into tags (id, tag) values(:id,:tag)";
        try (Connection con = sql2o.open()) {
                con.createQuery(sql)
                        .addParameter("id", tag.getId())
                        .addParameter("tag", tag.getEtiqueta())
                        .executeUpdate();
        }
    }
    public void updateTags (Tag tag)
    {
        String updateSql = "update tags set tag = :tag where id = :id";

        try (Connection con = sql2o.open()) {
            con.createQuery(updateSql)
                    .addParameter("tag",tag.getEtiqueta())
                    .addParameter("id", tag.getId())
                    .executeUpdate();
        }
    }
    public void deleteTag(int id) {
        try(Connection con = sql2o.open()) {
            String sql = "DELETE FROM tags WHERE id = :id;";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }
    }

}
