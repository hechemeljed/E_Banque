/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionBean;

import entities.CompteBancaire;
import entities.OperationBancaire;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import session.GestionnaireDeCompteBancaire;

/**
 *
 * @author squallo
 */
@ManagedBean
@ViewScoped
public class TransfertMBean implements Serializable {

    @EJB
    private GestionnaireDeCompteBancaire gestionnaireDeCompteBancaire;
    private long idCb1;
    private long idCb2;
    private double amount;
    private long idTransfert;
    private List<Long> autoComplete = new ArrayList<Long>();
    private String txt1;
    private String txt2;

    public TransfertMBean() {
    }

    public long getIdCb1() {
        return idCb1;
    }

    public void setIdCb1(long idCb1) {
        this.idCb1 = idCb1;
    }

    public long getIdCb2() {
        return this.idCb2;
    }

    public void setIdCb2(long idCb2) {
        this.idCb2 = idCb2;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getIdTransfert() {
        return idTransfert;
    }

    public void setIdTransfert(long idTransfert) {
        this.idTransfert = idTransfert;
    }

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    public List<Long> getAutoComplete(String query) {
        autoComplete.add(new Long(100000));
        autoComplete.add(new Long(111111));
        autoComplete.add(new Long(1222222));
        autoComplete.add(new Long(133333));
        autoComplete.add(new Long(144444));
        autoComplete.add(new Long(155555));

        FacesMessage msg = new FacesMessage("Succesful", "auto complete invoqué a été supprimé !");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        return autoComplete;
    }

    public void setAutoComplete(List<Long> autoComplete) {
        this.autoComplete = autoComplete;
    }

    public String transfert() {
        int indSeparator1 = this.txt1.lastIndexOf(":");
        int indSeparator2 = this.txt2.lastIndexOf(":");
        try {
            this.idCb1 = Long.parseLong(this.txt1.substring(indSeparator1 + 1, this.txt1.length()));
            this.idCb2 = Long.parseLong(this.txt2.substring(indSeparator2 + 1, this.txt2.length()));
        } catch (Exception e) {
            System.out.println("erreur lors du formatage !!!  : " + this.txt1.substring(indSeparator1 + 1, this.txt1.length()));
        }

        gestionnaireDeCompteBancaire.transfert(idCb1, idCb2, amount);

        return "Operations?id=" + this.getIdCb1() + "&amp;faces-redirect=true&amp;includeViewParams=true";
    }

    public String tranfertById(CompteBancaire cb) {
        this.idTransfert = cb.getId();
        this.idCb1 = cb.getId();
        this.txt1 = cb.getOwner() + ":" + cb.getId();
        return "Transfert?faces-redirect=true&amp;includeViewParams=true";
    }

    public List<String> complete(String query) {
        List<String> results = new ArrayList<String>();

        String like = "%" + query.toLowerCase() + "%";

        for (CompteBancaire cb : gestionnaireDeCompteBancaire.getComptesLike(like)) {
            results.add(cb.getOwner() + ":" + cb.getId());
        }


        return results;
    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public void showFilterCompte() {
        System.out.println("--- test de search");
        int indSeparator = this.txt1.lastIndexOf(":");
        Long id;
        System.out.println("--- " + this.txt1.substring(indSeparator + 1, this.txt1.length()));
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            id = Long.parseLong(this.txt1.substring(indSeparator + 1, this.txt1.length()));
            FacesContext ctx = FacesContext.getCurrentInstance();
            ExternalContext extContext = ctx.getExternalContext();
            //String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/showCompte.xhtml?id="+id));
            String url = "showCompte.xhtml?id=" + id;
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (Exception e) {
            System.out.println("erreur lors du formatage !!!  : " + this.txt1.substring(indSeparator + 1, this.txt1.length()));
            e.printStackTrace();
        }
        System.out.println("no redirect !!");
    }
}
