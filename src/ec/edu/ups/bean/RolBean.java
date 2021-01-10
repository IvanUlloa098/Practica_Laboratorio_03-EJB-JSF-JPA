package ec.edu.ups.bean;

import ec.edu.ups.ejb.*;
import ec.edu.ups.entidad.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class RolBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @EJB
    private RolFacade ejbRolFacade;
    private List<Rol> list;
    private List<Usuario>usuarios;

    public RolBean(){
        usuarios=new ArrayList<Usuario>();
    }
    @PostConstruct
    public void init(){
        //ejbRolFacade.create(new Rol("Secretaria","Rol para la secretaria",usuarios));
        list=ejbRolFacade.findAll();
    }
    public void setList(List<Rol> list) {
        this.list = list;
    }
    public Rol[] getList() {
        return list.toArray(new Rol[0]);
    }

}

