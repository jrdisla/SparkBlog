package Clases;

/**
 * Created by jrdis on 10/6/2017.
 */
public class Comentario {
    private long id;
    private String comment;
    private String Usuario;
    private long articulo;

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

    public String getAutor() {
        return Usuario;
    }

    public void setAutor(String autor) {
        this.Usuario= autor;
    }

    public long getArticulo() {
        return articulo;
    }

    public void setArticulo(long articulo) {
        this.articulo = articulo;
    }

    public Comentario(long id, String comment,String autor, long articulo) {

        this.id = id;
        this.comment = comment;
        this.Usuario= autor;
        this.articulo = articulo;
    }

    public Comentario() {

    }
}
