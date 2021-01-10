package ec.edu.ups.bean;
import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Named;
import java.io.Serializable;

@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class NavegationController implements Serializable {

    @ManagedProperty(value = "#{param.pageId}")
    private String pageId;

    public String showPage(){
        switch (pageId){
            case "login":
                return "logIn";

            default:
                return "/public/paginaCatalogo";
        }
    }

    public String processLogin(){
        return "/public/paginaAdministrador";
    }

}
