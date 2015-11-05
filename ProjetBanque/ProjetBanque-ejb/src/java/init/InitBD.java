/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package init;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import session.GestionnaireDeCompteBancaire;

@Singleton
@Startup
@LocalBean
public class InitBD {
    @EJB
    private GestionnaireDeCompteBancaire gestionnaireDeCompteBancaire;

    @PostConstruct
    public void init() {
        System.out.println("--- INIT BD ---");
        gestionnaireDeCompteBancaire.creerComptesTest();
    }
   

}
