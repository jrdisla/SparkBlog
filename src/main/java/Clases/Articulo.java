package Clases;

import java.util.ArrayList;

/**
 * Created by jrdis on 10/6/2017.
 */
public class Articulo {
    private long id;
    private String titulo;
    private String cuerpo;
    private String username;
    private String fecha;
    private ArrayList<Comentario> comment;
    private ArrayList<Tag> tag;

    public Articulo() {


    }

    public Articulo(long id, String titulo, String cuerpo, String autor, String fecha, ArrayList<Comentario> comment, ArrayList<Tag> tag) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.username = autor;
        this.fecha = fecha;
        this.comment = comment;
        this.tag = tag;
    }

    public long getId() {

        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String getAutor() {
        return username;
    }

    public void setAutor(String autor) {
        this.username = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public ArrayList<Comentario> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comentario> comment) {
        this.comment = comment;
    }

    public ArrayList<Tag> getTag() {
        return tag;
    }

    public void setTag(ArrayList<Tag> tag) {
        this.tag = tag;
    }

}
