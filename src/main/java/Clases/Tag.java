package Clases;

/**
 * Created by jrdis on 10/6/2017.
 */
public class Tag {
    private long id;
    private String tag;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return tag;
    }

    public void setEtiqueta(String etiqueta) {
        this.tag = etiqueta;
    }

    public Tag(long id, String etiqueta) {

        this.id = id;
        this.tag = etiqueta;
    }

    public Tag() {

    }

}
