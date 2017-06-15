package TemplatesHandler;

import Clases.Articulo;
import Clases.Usuario;
import DataBaseManager.ArticuloDao;
import DataBaseManager.UsuarioDao;
import freemarker.template.Configuration;
import spark.ModelAndView;
import spark.Session;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by jrdis on 10/6/2017.
 */
public class ManejadorTemp {
    ArticuloDao articuloDao = new ArticuloDao();
    UsuarioDao usuarioDao = new UsuarioDao();

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
            if(adm_strin.equalsIgnoreCase("true"))
            {
                adm = true;
            }
            String autor_strin = request.queryParams("autor");
            if(autor_strin.equalsIgnoreCase("true"))
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
        before("/listArti/:id/*",(request, response) -> {
            Long id = Long.parseLong(request.params(":id"));
            Articulo articulo = getArticuloById(id);
            Usuario usuario = getUserByUsername(articulo.getAutor());
            if(usuario==null){
                //parada del request, enviando un codigo.
                halt(401, "No tiene permisos para acceder -- Lo dice el filtro....");
            }
        });
        get("/listArti/:id/", (request, response) -> {

            Long id = Long.parseLong(request.params(":id"));

            Articulo arti = getArticuloById(id);

          String htmlCode =  automaticHtmlCodejustOne(arti);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Agregar Nuevo Usuario");
            attributes.put("code",htmlCode);

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
         //   List<Articulo> articulaaaa;
         //   articulaaaa = articuloDao.getAllArticulos();
         //   String htmlCode = automaticHtmlCode(articulaaaa);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("Titulo", "Adding Articule");


            return new ModelAndView(attributes, "addArticulos.ftl");
        }, engine);
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
                    "<h5 onclick=\"document.location = '/listArti/" + item.getId() + "/';\"><span class=\"label label-danger\">" + titulo + "</h5><br>" + "\n\t\t" +
                    "<p>" + cuerpo + "</p>" + "\n\t\t\t" +
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
}

