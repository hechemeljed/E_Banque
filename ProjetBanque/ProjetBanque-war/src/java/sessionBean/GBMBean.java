/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.CompteBancaire;
import entities.OperationBancaire;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import session.GestionnaireDeCompteBancaire;


@Named("gBMBean")
@SessionScoped
public class GBMBean implements Serializable {

    @EJB
    private GestionnaireDeCompteBancaire gestionnaireDeCompteBancaire;
    private CompteBancaire cb;
    private List<CompteBancaire> lcb;
    private long id;
    private Collection<OperationBancaire> operations;
    private int offset=1;
    private int nbrParPage=10;

    
    
    @PostConstruct
    public void init()
    {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        lcb = gestionnaireDeCompteBancaire.getAllComptes();
        if(lcb == null || lcb.isEmpty())
        {
            gestionnaireDeCompteBancaire.creerComptesTest();
            lcb = gestionnaireDeCompteBancaire.getAllComptes();
        }
    }
    
    public void preRenderView() {
        // Hack pour éviter l'erreur Cannot create a session after the response 
        // has been committed des datatables PrimeFaces
        System.out.println("PRE RENDER VIEW");
        refresh();
        
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public String testAccount() {
        System.out.print("---- creation des comptes de test ----");
        gestionnaireDeCompteBancaire.creerComptesTest();
        return "Affichage_Comptes?faces-redirect=true&amp;includeViewParams=true";
    }

    public CompteBancaire getCb() {
        return cb;
    }

    public void setCb(CompteBancaire cb) {
        this.cb = cb;
    }

    public GBMBean() {
    }

    public List<CompteBancaire> getAllComptes() {
        if (lcb == null) {
            lcb = gestionnaireDeCompteBancaire.getAllComptes();
        }
        return lcb;
    }

    public String showCompte(CompteBancaire cb) {
        this.cb = cb;
        System.out.print("---- compte recupéré" + cb.getId() + "----");
        return "showCompte?id=" + this.cb.getId() + "faces-redirect=true&amp;includeViewParams=true";
    }
    
    
    public String update() {
        System.out.println("---- UPDATE ----  : " + this.cb.getId());
        this.cb = gestionnaireDeCompteBancaire.update(cb);
        return "Affichage_Comptes?faces-redirect=true&amp;includeViewParams=true";
    }

    public String list() {
        System.out.println("---- LIST ----");
        
        return "Affichage_Comptes?faces-redirect=true&amp;includeViewParams=true";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void loadAccount(ComponentSystemEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        if (this.getId() == 0) {
            System.out.print("---- loading a new Account !");
            this.cb = new CompteBancaire("new", 0);
        } else {
            System.out.print("---- loading Account : " + getId() + " ####");
            this.cb = gestionnaireDeCompteBancaire.getCompte(this.getId());
            this.operations = this.cb.getOperations();
        }
    }

    public String showOperation(CompteBancaire cb) {
        this.cb = cb;
        this.operations = this.cb.getOperations();
        return "Operations?id=" + this.cb.getId() + "faces-redirect=true&amp;includeViewParams=true";
    }

    public Collection<OperationBancaire> getOperations() {

        return this.operations;
    }

    public String deleteCompte(CompteBancaire cb) {
        if (gestionnaireDeCompteBancaire.deleteCompte(gestionnaireDeCompteBancaire.merge(cb))) {
            return "Affichage_Comptes?faces-redirect=true&amp;includeViewParams=true&amp;successMsg=ok";
        } else {
            return "Error?faces-redirect=true&amp;includeViewParams=true";
        }
    }
    
    public String nextPageLink(int offset)
    {
        return "Affichages_Comptes?offset="+offset+"&amp;faces-redirect=true&amp;includeViewParams=true";
    }
    
    public String getPagesList()
    {
        return "5,10,15,20";
    }

    private void refresh() {
        System.out.println("REFRESH !");
       lcb = gestionnaireDeCompteBancaire.getAllComptes();
    }
    
}
