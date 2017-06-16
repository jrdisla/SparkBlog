package DataBaseManager;

import Clases.Comentario;
import Clases.Usuario;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

/**
 * Created by jrdis on 10/6/2017.
 */
public class UsuarioDao {
    private Sql2o sql2o;

    public UsuarioDao() {
        this.sql2o = new Sql2o("jdbc:h2:~/test7","sa","");
        createtables();
    }

    public void createtables ()
    {

          String sql="CREATE TABLE IF NOT EXISTS usuarios(username VARCHAR (50) PRIMARY KEY, nombre VARCHAR (1000),password VARCHAR (50),adm BOOLEAN ,autor BOOLEAN);";

        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }
    }
    public List<Usuario> getAllUsers(){
        String sql="select * from usuarios";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql).executeAndFetch(Usuario.class);
        }
    }

    public void inserIntoUsers(Usuario user){
        String sql = "insert into usuarios (username,nombre,password,adm,autor) values(:username,:nombre,:password,:adm,:autor)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)

                    .addParameter("username",user.getUsername() )
                    .addParameter("nombre", user.getNombre())
                    .addParameter("password",user.getPassword())
                    .addParameter("adm",user.isAdm())
                    .addParameter("autor",user.isAutor())
                    .executeUpdate();
        }
    }

   public void updateName (Usuario user)
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

    public void deleteUser(String username) {
        try(Connection con = sql2o.open()) {
            String sql = "DELETE FROM usuarios WHERE username = :username;";
            con.createQuery(sql)
                    .addParameter("username", username)
                    .executeUpdate();
        }
    }
}
