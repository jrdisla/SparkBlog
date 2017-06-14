package Clases;

/**
 * Created by jrdis on 10/6/2017.
 */
public class Comentario {
    private long id;
    private String comment;
    private Usuario autor;
    private Articulo articulo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Comentario(long id, String comment, Usuario autor, Articulo articulo) {

        this.id = id;
        this.comment = comment;
        this.autor = autor;
        this.articulo = articulo;
    }

    public Comentario() {

    }
}
