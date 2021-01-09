package ups.edu.ec.bean;

import ups.edu.ec.ejb.FacturaCabeceraFacade;
import ups.edu.ec.entidad.FacturaCabecera;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class CollectionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<FacturaCabecera> list ;

    @EJB
    FacturaCabeceraFacade  ejbfacturaCabeceraFacade ;


    public CollectionBean() {
    }


    @PostConstruct
    public void init(){
        list = ejbfacturaCabeceraFacade.findAll();
    }

    public FacturaCabecera[] getList() {
        return list.toArray(new FacturaCabecera[0]);
    }

    public void setList(List<FacturaCabecera> list) {
        this.list = list;
    }

    public String delete(FacturaCabecera c) {
        ejbfacturaCabeceraFacade.remove(c);
        list = ejbfacturaCabeceraFacade.findAll();
        return null;
    }

    public String edit(FacturaCabecera c) {
        c.setEditable(true);
        return null;
    }

    public String save(FacturaCabecera c) {
        ejbfacturaCabeceraFacade.edit(c);
        c.setEditable(false);
        return null;
    }
}