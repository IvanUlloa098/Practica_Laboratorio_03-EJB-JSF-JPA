package ec.edu.ups.bean;

import ec.edu.ups.ejb.BodegaFacade;
import ec.edu.ups.ejb.CiudadFacade;
import ec.edu.ups.ejb.PaisFacade;
import ec.edu.ups.ejb.ProvinciaFacade;
import ec.edu.ups.entidad.*;
import ec.edu.ups.http.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class BodegaBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @EJB
    private BodegaFacade ejbBodegaFacade;
    @EJB
    private PaisFacade paisFacade;
    @EJB
    private ProvinciaFacade provinciaFacade;
    @EJB
    private CiudadFacade ciudadFacade;
    private List<Bodega> bodegas;
    private String nombre;
    private String nombreBodega;
    private int idBodega;
    private Bodega bodega;
    private String cookie;


    /*ADICION*/
    private String level1, level2, level3;
    private boolean level2ListDisabled = true, level3ListDisabled = true;
    private Map<String, String> paises;
    private String codePais;
    private String apiKey="4ddd12a869916be484c4ef8b97915bc5";


    public BodegaBean(){

    }


    @PostConstruct
    public void init(){
        this.bodegas=this.ejbBodegaFacade.findAll();
    }

    public Bodega[] getBodegas() {
        return bodegas.toArray(new Bodega[0]);
    }

    public void setBodegas(List<Bodega> bodegas) {
        this.bodegas = bodegas;
    }

    public String getnombre() {
        return nombre;
    }

    public void setnombre(String nombre) {
        this.nombre = nombre;
    }


    public String add (){//pedro
        String [] paisProCiu=this.level3.split("-");
        Pais pais = paisFacade.find("Ecuador");
        
        /*POR DEFECTO VOY A USAR EL PAIS DE ECUADOR*/
        Provincia provincia=consultarProvincia(paisProCiu[1].toUpperCase(),pais);
        Ciudad ciudad =consultarCiudad(paisProCiu[2].toUpperCase(),provincia);
        this.ejbBodegaFacade.create(new Bodega(this.nombre.toUpperCase(),ciudad));
        /*limipeza de campos*/
        this.nombre="";
       this.level1="Seleccionar";
        this.level3="Seleccionar";
        this.bodegas=this.ejbBodegaFacade.findAll();
        return  null;
    }
    
    /*METODOS ADICIONADOS*/

    public Pais consultarPais(String nombre){
        Pais p;

        System.out.println("PAIS BEANNNN*******************************"+nombre);

        p=paisFacade.find(nombre);
        if (p==null){
            paisFacade.create(new Pais(nombre,nombre));
            System.out.println("Crear PAIS"+nombre);
            return paisFacade.find(nombre);
        }
        return new Pais("", "");
    }


    public Provincia consultarProvincia(String nombre, Pais pais){
        Provincia p=provinciaFacade.find(nombre);
        if (p ==null){
            provinciaFacade.create(new Provincia(nombre,nombre,pais));
            return  provinciaFacade.find(nombre);
        }
        return p;
    }


    public Ciudad consultarCiudad(String nombre, Provincia provincia){
        Ciudad c=ciudadFacade.find(nombre);
        if (c ==null){
            ciudadFacade.create(new Ciudad(nombre,nombre,provincia));
            return ciudadFacade.find(nombre);
        }
        return c;
    }

    public void buscarBodega(int codigo){
        this.bodega=ejbBodegaFacade.find(codigo);
        this.nombreBodega=bodega.getNombre();
        this.idBodega=bodega.getCodigo();
    }

    public void actualizarBodega(){
        String [] paisProCiu=this.level3.split("-");
        Pais pais = paisFacade.find("Ecuador");
        Provincia provincia=consultarProvincia(paisProCiu[1].toUpperCase(),pais);
        Ciudad ciudad =consultarCiudad(paisProCiu[2].toUpperCase(),provincia);
        this.bodega.setCiudad(ciudad);
        this.bodega.setNombre(nombreBodega.toUpperCase());
        this.ejbBodegaFacade.edit(this.bodega);
        this.bodegas=null;
        this.bodegas=ejbBodegaFacade.findAll();
        this.level1="Seleccionar";
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "paginaBodegas.xhtml");
    }

    public void navegar(){
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "paginaEdicionBodega.xhtml");
    }



    public String deleted(Bodega b){
        ejbBodegaFacade.remove(b);
        bodegas=ejbBodegaFacade.findAll();
        return null;
    }

    public String edit (Bodega b){
        b.setEditable(true);
        return null;
    }

    public String save(Bodega b){
        this.ejbBodegaFacade.edit(b);
        b.setEditable(false);
        return null;
    }
    /*ADICION*/
    public boolean isLevel2ListDisabled() {
        return level2ListDisabled;
    }

    public void setLevel2ListDisabled(boolean level2ListDisabled) {
        this.level2ListDisabled = level2ListDisabled;
    }

    public boolean isLevel3ListDisabled() {
        return level3ListDisabled;
    }

    public void setLevel3ListDisabled(boolean level3ListDisabled) {
        this.level3ListDisabled = level3ListDisabled;
    }

    public String getLevel1() {
        return level1;
    }

    public String getLevel2() {
        return level2;
    }

    public String getLevel3() {
        return level3;
    }

    public void setLevel1(String level1) {
        this.level1 = level1;
        this.setLevel2("Seleccionar");
        this.level2ListDisabled = this.level1.equals("Seleccionar");
    }

    public void setLevel2(String level2) {
        this.level2 = level2;
        this.setLevel3("Seleccionar");
        this.level3ListDisabled = this.level2.equals("Seleccionar");
    }

    public void setLevel3(String level3) {
        this.level3 = level3;
    }

    private ArrayList<String> generateList(String pre, int size) {
        //String[] list = new String[size];
        ArrayList<String> list=new ArrayList<String>();
        list.add("Seleccionar");

        return list;
    }

    private ArrayList<String> obtenerPaises() {
        ArrayList<String> paisesf=new ArrayList<String>();
        paisesf.add("Seleccionar");

        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {

                    String pedro = status.getResult();

                    pedro = pedro.substring(0, pedro.length() - 1);
                    String[] p = pedro.split("}");

                    /* Paises */
                    paises=new HashMap<>();
                    for (String pp : p) {
                        pp = pp.substring(9, pp.length());

                        String[] pp2 = pp.split(",");
                        String key = pp2[0].substring(1, pp2[0].length() - 1);
                        String value = pp2[1].substring(pp2[1].length() - 3, pp2[1].length() - 1);

                        paises.put(key, value);
                        paisesf.add(key);
                    }


                }

            }
        });

        cliente.excecute("http://battuta.medunes.net/api/country/all/?key="+apiKey);
        return paisesf;
    }


    private ArrayList<String> obtenerProvincias(String pais){

        ArrayList<String> provincias=new ArrayList<String>();
        provincias.add("Seleccionar");
        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {

                    String pedro = status.getResult();

                    pedro = pedro.substring(0, pedro.length() - 1);
                    String[] p = pedro.split("}");

                    for (String pp : p) {
                        pp=pp.substring(11, pp.length());

                        String [] pp2=pp.split(",");
                        String key=pp2[0].substring(1, pp2[0].length()-1);
                        provincias.add(pais+"-"+key);

                    }


                }

            }
        });
        this.codePais=paises.get(pais);
        cliente.excecute("http://battuta.medunes.net/api/region/"+this.codePais+"/all/?key="+apiKey);
        return provincias;
    }

    private ArrayList<String> obtenerCiudades(String provincia){

        ArrayList<String> ciudades=new ArrayList<String>();
        ciudades.add("Seleccionar");
        HttpClient cliente = new HttpClient(new OnHttpRequestComplete() {
            @Override
            public void onComplete(Response status) {
                if (status.isSuccess()) {

                    String pedro = status.getResult();

                    pedro = pedro.substring(0, pedro.length() - 1);
                    String[] p = pedro.split("}");

                    for (String pp : p) {
                        pp=pp.substring(9, pp.length());

                        String [] pp2=pp.split(",");
                        String key=pp2[0].substring(1, pp2[0].length()-1);
                        ciudades.add(provincia+"-"+key);
                    }


                }

            }
        });
        String[] provinciaF=provincia.split(" ");

        cliente.excecute("http://battuta.medunes.net/api/city/"+this.codePais+"/search/?region="+provinciaF[provinciaF.length-1]+"&key="+apiKey);
        return ciudades;
    }


    public ArrayList<String> getLevel1List() {
        //this.generateList("", 5);


        //return this.generateList("", 5);
        return obtenerPaises();
    }

    public ArrayList<String> getLevel2List() {
        if (this.level2ListDisabled)
            return this.generateList("", 1);
        else//return this.generateList(this.level1 + "--", 5);
            return this.obtenerProvincias(this.level1);
    }

    public ArrayList<String> getLevel3List() {
        if (this.level3ListDisabled)
            return this.generateList("", 1);
        else//return this.generateList(this.level2 + "--", 5);
            return this.obtenerCiudades(this.level2);
    }

    /*verfa*/

    public String getNombreBodega() {
        return nombreBodega;
    }

    public void setNombreBodega(String nombreBodega) {
        this.nombreBodega = nombreBodega;
    }

    public int getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(int idBodega) {
        this.idBodega = idBodega;
    }

    public String getCookie(){
        Cookie cookie = (Cookie) FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap().get("administrador");
        if(cookie == null || cookie.getValue().equals("")){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("../public/paginaCatalogo.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "Bienvenido!";
    }

    public void regresar(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../private/paginaAdministrador.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
