package TemplatesHandler;

import Clases.Articulo;
import Clases.Comentario;
import Clases.Tag;
import Clases.Usuario;
import DataBaseManager.ArticuloDao;
import DataBaseManager.CommentDao;
import DataBaseManager.TagDao;
import DataBaseManager.UsuarioDao;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;
import java.util.Calendar;
import java.util.*;

import static spark.Spark.*;

/**
 * Created by jrdis on 10/6/2017.
 */
public class ManejadorTemp {
    ArticuloDao articuloDao = new ArticuloDao();
    UsuarioDao usuarioDao = new UsuarioDao();
    TagDao tagDao = new TagDao();
    CommentDao commentDao = new CommentDao();
    long actual_id = 0;
    java.util.Date date = new Date();
    public void startApp() {

        Configuration configuration= new Configuration(Configuration.VERSION_2_3_26);
        configuration.setClassForTemplateLoading(ManejadorTemp.class, "/template");
        FreeMarkerEngine FreeMarkerengine = new FreeMarkerEngine(configuration);

        startPage(FreeMarkerengine);
        infoArticulo(FreeMarkerengine);
        addUser (FreeMarkerengine);
        login(FreeMarkerengine);
        invalidarSession(FreeMarkerengine);
        added(FreeMarkerengine);
        addArticule(FreeMarkerengine);
        addedArti(FreeMarkerengine);
        validateUser(FreeMarkerengine);
        listArtyby(FreeMarkerengine);
        deleteArticulo(FreeMarkerengine);
        updateArtit(FreeMarkerengine);
        IndividualShow(FreeMarkerengine);
    }
    /***
     * http://localhost:4567/addUser/
     * @param engine
     */
    public void addUser (FreeMarkerEngine engine)
    {
        before("/addUser/",(request, response) -> {
            Usuario usuario = request.session().attribute("username");
            if(!usuario.isAdm()){
                //parada del request, enviando un codigo.
                halt(401, "No tiene permisos para acceder -- Lo dice el filtro....");
            }
        });
        get("/addUser/", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Start Page");
          //  attributes.put("code",htmlCode);

            return new ModelAndView(attributes, "addUsers.ftl");
        }, engine);
    }
    /***
     * http://localhost:4567/addUser/added
     * @param engine
     */
    public void added (FreeMarkerEngine engine)
    {
        post("/addUser/added", (request, response) -> {
            boolean adm = false;
            boolean autor = false;
            String username = request.queryParams("username");
            String name = request.queryParams("nombre");
            String password = request.queryParams("password");
            String adm_strin = request.queryParams("adm");
            if (adm_strin!=null){
                    adm = true;
            }
            String autor_strin = request.queryParams("autor");
            if(autor_strin !=null)
            {
                autor = true;
            }

            Usuario user = new Usuario(username,name,password,adm,autor);

            usuarioDao.inserIntoUsers(user);

            String html = automaticHtmlCode(articuloDao.getAllArticulos());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Start Page");
            attributes.put("code",html);

            return new ModelAndView(attributes, "startPage.ftl");
        }, engine);
    }

    public void startPage (FreeMarkerEngine engine)
    {
        /***
         * http://localhost:4567/startPage/
         * @param engine
         */
        get("/startPage/", (request, response) -> {
            List<Articulo> articulaaaa;
            articulaaaa = articuloDao.getAllArticulos();
            String htmlCode = automaticHtmlCode(articulaaaa);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Start Page");
            attributes.put("code",htmlCode);

            return new ModelAndView(attributes, "startPage.ftl");
        }, engine);
    }

    public void infoArticulo (FreeMarkerEngine engine)
    {
        /***
         * http://localhost:4567/listArti/:id/
         * @param engine
         */
        before("/listArti/:id/",(request, response) -> {
            Usuario usuario = request.session().attribute("username");
            if(usuario==null){
                //parada del request, enviando un codigo.
                halt(401, "No tiene permisos para acceder -- Lo dice el filtro....");
            }
        });
        get("/listArti/:id/", (request, response) -> {

            Long id = Long.parseLong(request.params(":id"));
            Articulo arti = getArticuloById(id);
            actual_id = id;
            String htmlCode =  automaticHtmlCodejustOne(arti);
            String htmlCode2 =  automaticCommentHtmlCode(commentDao.getAllComments(),arti.getId());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Agregar Nuevo Usuario");
            attributes.put("code",htmlCode);
            attributes.put("code2",htmlCode2);
            return new ModelAndView(attributes, "articulo.ftl");
        }, engine);
    }

    public void login (FreeMarkerEngine engine)
    {
        /***
         * http://localhost:4567/login/
         * @param engine
         */
        get("/login/", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Start Page");
            //  attributes.put("code",htmlCode);

            return new ModelAndView(attributes, "login.ftl");
        }, engine);

        /***
         * http://localhost:4567/login/:username/:password
         * @param engine
         */
        post("/login/logged", (request, response)->{
            //
            Session session=request.session(true);
            String username = request.queryParams("username");
            String password = request.queryParams("password");
            Usuario usuario= getUserByUsername(username);
            if(usuario != null && usuario.getPassword().equalsIgnoreCase(password)){

                    session.attribute("username", usuario);

                          }
        else{
                halt(401,"Credenciales no validas...");
                response.redirect("/login/");
            }


            List<Articulo> articulaaaa;
            articulaaaa = articuloDao.getAllArticulos();
            String htmlCode = automaticHtmlCode(articulaaaa);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Start Page");
            attributes.put("code",htmlCode);

            return new ModelAndView(attributes, "startPage.ftl");
        }, engine);
    }

    public void invalidarSession (FreeMarkerEngine engine) {

        /**
         * http://localhost:4567/invalidarSesion/
         * @param engine
         */
        get("/invalidarSesion/", (request, response) -> {
            Session session = request.session(true);
            String id = session.id();
            session.invalidate();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Start Page");
            return new ModelAndView(attributes, "login.ftl");
        }, engine);
    }
    /**
     * http://localhost:4567/addArticulo/
     * @param engine
     */
    public void addArticule (FreeMarkerEngine engine)
    {
        get("/addArticulo/", (request, response) -> {

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Adding Articule");


            return new ModelAndView(attributes, "addArticulos.ftl");
        }, engine);
    }

    /***
     * http://localhost:4567/addArticulo/added
     * @param engine
     */

    public void addedArti (FreeMarkerEngine engine)
    {
        before("/addArticulo/added",(request, response) -> {
            Usuario usuario = request.session().attribute("username");
            if(usuario==null){
                //parada del request, enviando un codigo.
                halt(401, "No tiene permisos para acceder -- Lo dice el filtro....");
            }

        });

        before("/addArticulo/added",(request, response) -> {
                    Usuario usuario = request.session().attribute("username");
                    if(!usuario.isAdm() && !usuario.isAutor()){
                        //parada del request, enviando un codigo.
                        halt(401, "No tiene permisos para acceder -- Lo dice el filtro....");
                    } });
        post("/addArticulo/added", (request, response) -> {
            List<Articulo> articulos = articuloDao.getAllArticulos();
            long ids = articulos.size();
            Usuario user = request.session().attribute("username");
            ArrayList<Comentario> comentarios = new ArrayList<>();
            ArrayList<Tag> tags = new ArrayList<>();
            String titulo = request.queryParams("titulo");
            String cuerpo = request.queryParams("comment");
            String tags_ = request.queryParams("tags");
          //  ArrayList<Tag> tags = new ArrayList<>();
            List<String> result = Arrays.asList(tags_.split("\\s*,\\s*"));
            List<Tag> allTags = tagDao.getAllTags();
            long id_tag = allTags.size();
            id_tag +=1;
            Tag tagp = new Tag(id_tag,tags_);
            tagDao.inserIntoTags(tagp);
            tags.add(tagp);
            Calendar fechaaq = new GregorianCalendar();
            int año = fechaaq.get(Calendar.YEAR);
            int mes = fechaaq.get(Calendar.MONTH);
            int dia = fechaaq.get(Calendar.DAY_OF_MONTH);
            String fecha = dia + "/" + (mes+1) + "/" + año;
            ids +=1;
            Articulo articulo = new Articulo(ids,titulo,cuerpo,user.getUsername(),fecha,comentarios,tags_);
            articuloDao.inserIntoArticulos(articulo);
            String html = automaticHtmlCode(articuloDao.getAllArticulos());
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Start Page");
            attributes.put("code",html);
            return new ModelAndView(attributes, "startPage.ftl");
        }, engine);
    }
    /***
     * http://localhost:4567/articulo/valida
     * @param engine
     */
    public void validateUser (FreeMarkerEngine engine)
    {
        before("/articulo/valida",(request, response) -> {
            Usuario usuario_ = request.session().attribute("username");
            Usuario usuario = getUserByUsername(usuario_.getUsername());
            if(usuario==null){
                //parada del request, enviando un codigo.
                halt(401, "No tiene permisos para acceder -- Lo dice el filtro....");
            }
        });

        post("/articulo/valida", (request, response) -> {
            String comment = request.queryParams("comment");
            long id_c = commentDao.getAllComments().size();
            Articulo articulo = getArticuloById(actual_id);
            Usuario usuario = request.session().attribute("username");
            String este_user = usuario.getUsername();
            Comentario comentario = new Comentario((id_c++), comment, este_user, articulo.getId());
            commentDao.inserIntoTags(comentario);
            List<Comentario> comentarios = commentDao.getAllComments();
            String htmlCode =  automaticHtmlCodejustOne(articulo);
            String htmlCode2 =  automaticCommentHtmlCode(commentDao.getAllComments(),actual_id);
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Agregar Nuevo Usuario");
            attributes.put("code",htmlCode);
            attributes.put("code2",htmlCode2);
            return new ModelAndView(attributes, "articulo.ftl");
        }, engine);
    }

    public void listArtyby (FreeMarkerEngine engine)
    {
        get("/listArtiBy/", (request, response) -> {
            Usuario user_arti = request.session().attribute("username");
            List<Articulo> articulos = articuloDao.getAllArticulos();
            String html="";
            List<Articulo> list_aux= new ArrayList<>();
            if(!user_arti.isAdm()){
            for (Articulo item:articulos
                 ){
                       if(item.getAutor().equalsIgnoreCase(user_arti.getUsername()))
                       {
                           list_aux.add(item);
                       }
            }
            html = automaticHtmlCodeForTable(list_aux);}
            else
            {
                html = automaticHtmlCodeForTable(articulos);
            }
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Adding Articule");
            attributes.put("code", html);
            return new ModelAndView(attributes, "listArtiByUser.ftl");
        }, engine);
    }
    public void deleteArticulo(FreeMarkerEngine engine) {
        get("/deleteArticulo/:id/", (request, response) -> {
            long ID = Long.parseLong(request.params(":id"));
            actual_id--;
            articuloDao.deleteArticulo(ID);
            response.redirect("/listArtiBy/");
            return "";
        });
    }

    /***
     *
     * http://localhost:4567/actStudent/
     * @param FreeMarkerengine
     */
    public void updateArtit(FreeMarkerEngine FreeMarkerengine) {
        get("/actArticulo/:id/", (request, response) -> {
            Articulo articulo = getArticuloById(Long.parseLong(request.params(":id")));
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Actualizando Articulo");
            attributes.put("articulo", articulo);
            return new ModelAndView(attributes, "updateArti.ftl");
        }, FreeMarkerengine);
    }
    /***
     * http://localhost:4567/individualInfo/
     * @param FreeMarkerengine
     */
    public void IndividualShow(FreeMarkerEngine FreeMarkerengine) {
        get("/individualInfo/:id/", (request, response) -> {
            Articulo articulo = getArticuloById(Long.parseLong(request.params(":id")));
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Información de Articulo");
            attributes.put("articulo", articulo);
            return new ModelAndView(attributes, "infoArticulo.ftl");
        }, FreeMarkerengine);

        post("/individualInfo/:id/", (request, response) -> {

            Articulo articulo = getArticuloById(Long.parseLong(request.params("id")));

            articulo.setCuerpo(request.queryParams("comment"));
            articulo.setTitulo(request.queryParams("titulo"));
            articulo.setTag(request.queryParams("tags"));

            System.out.println(articulo.getTag());

            articuloDao.updateTitulo(articulo);
            articuloDao.updateTag(articulo);
            articuloDao.updateCuerpo(articulo);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Información de articulo");
            attributes.put("articulo", articulo);
            return new ModelAndView(attributes, "infoArticulo.ftl");
        }, FreeMarkerengine);
    }

    private String automaticHtmlCode(List<Articulo> articulos) {
        String htmlCode = "";
        String titulo = "";
        String userName = "";
        String cuerpo = "";
            for (Articulo item: articulos) {

            titulo = item.getTitulo();
            userName = item.getAutor();
            cuerpo = item.getCuerpo();
            htmlCode += "<h4><small> Recent Post </small></h4>" + "\n\t\t" +
                    "<hr>" + "\n\t\t" +
                    "<h2>" + titulo + "</h2>" + "\n\t\t" +
                    "<h5><span class=\"glyphicon glyphicon-time\"></span> Posted by: "+ userName + "</h5>" + "\n\t\t" +
                    "<h5><span class=\"glyphicon glyphicon-time\"></span>"+ item.getFecha() + "</h5>" + "\n\t\t" +
                    "<h5><span class=\"glyphicon glyphicon-time\"></span>"+ item.getTag() + "</h5>" + "\n\t\t" +
                    "<h5 onclick=\"document.location = '/listArti/" + item.getId() + "/';\"><span class=\"label label-danger\">" + titulo + "</h5><br>" + "\n\t\t" +
                    "<p>" + cuerpo.substring(0,70) + "</p>" + "\n\t\t\t" +
                    " <br><br>\n\t    ";

        }

        return htmlCode;
    }
    private String automaticHtmlCodejustOne(Articulo item) {
        String htmlCode = "";
        String titulo = "";
        String userName = "";
        String cuerpo = "";

            titulo = item.getTitulo();
            userName = item.getAutor();
            cuerpo = item.getCuerpo();
            htmlCode += "<h4><small> Recent Post </small></h4>" + "\n\t\t" +
                    "<hr>" + "\n\t\t" +
                    "<h2>" + titulo + "</h2>" + "\n\t\t" +
                    "<h5><span class=\"glyphicon glyphicon-time\"></span> Posted by: "+ userName + "</h5>" + "\n\t\t" +
                    "<p>" + cuerpo + "</p>" + "\n\t\t\t" +
                    " <br><br>\n\t    ";



        return htmlCode;
    }
    public  Articulo getArticuloById (long id)
    {
        for (Articulo item:articuloDao.getAllArticulos()
             ) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public Usuario getUserByUsername (String username)
    {
        for (Usuario item: usuarioDao.getAllUsers()
             ) {
            if (item.getUsername().equals(username))
            {
                return item;
            }
        }
        return null;
    }
    public Articulo getArtiByName (String titulo)
    {
        for (Articulo item:articuloDao.getAllArticulos()
             ) {
            if (item.getTitulo().equalsIgnoreCase(titulo))
            {
                return item;
            }
        }
        return null;
    }

    public String automaticCommentHtmlCode (List<Comentario> item, long id_arti)
    {

        String htmlCode = "";
        for (Comentario itemp:item
             ) {
         if (itemp.getArticulo() == id_arti){
             System.out.println(itemp.getAutor());
            htmlCode += "<h4><small> Recent Post </small></h4>" + "\n\t\t" +
                    "<hr>" + "\n\t\t" +
                    "<h5><span class=\"glyphicon glyphicon-time\"></span> Posted by: " + itemp.getAutor() + "</h5>" + "\n\t\t" +
                    "<p>" + itemp.getComment() + "</p>" + "\n\t\t\t" +
                    " <br><br>\n\t    ";
        }
        }
    return htmlCode;}

    private String automaticHtmlCodeForTable(List<Articulo> articulos) {
        String htmlCode = "";
        for (Articulo item : articulos) {
            htmlCode += "<tr onclick=\"document.location = '/individualInfo/" + item.getId() + "/';\">" + "\n\t\t" +
                    "<td>" + item .getTitulo() + "</td>" + "\n\t\t" +
                    "<td>" + item .getAutor() + "</td>" + "\n\t\t" +
                    "<td>" + item .getFecha() + "</td>" + "\n\t\t" +
                    "<td>" + "\n\t\t\t" +
                    "<a href=\"/actArticulo/" + item .getId() + "/\" class=\"btn btn-warning\"  role=\"button\">Actualizar</a>" + "\n\t\t\t" +
                    "<a href=\"/deleteArticulo/" + item .getId() + "/\"class=\"btn btn-danger\"  role=\"button\">Eliminar</a>" + "\n\t\t\t" +
                    "</td>" + "\n\t    " +
                    "</tr>\n\t";
        }

        return htmlCode;
    }


}


