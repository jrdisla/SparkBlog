package Clases;

/**
 * Created by jrdis on 10/6/2017.
 */
public class Usuario {
    private String username;;
    private String nombre;
    private String password;
    private boolean adm;
    private boolean autor;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean autor) {
        this.autor = autor;
    }

    public Usuario(String username, String nombre, String password, boolean adm, boolean autor) {

        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.adm = adm;
        this.autor = autor;
    }
    public Usuario() {


    }
}
