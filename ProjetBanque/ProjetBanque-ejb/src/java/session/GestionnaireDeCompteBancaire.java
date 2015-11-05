/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entities.CompteBancaire;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class GestionnaireDeCompteBancaire {

    @PersistenceContext
    private EntityManager em;

    public CompteBancaire creerCompte(CompteBancaire cb) {
        em.persist(cb);
        return cb;
    }

    public void creerComptesTest() {

        creerCompte(new CompteBancaire("John Lennon", 150000));
        creerCompte(new CompteBancaire("Cartney", 950000));
        creerCompte(new CompteBancaire("Ringo Starr", 20000));
        creerCompte(new CompteBancaire("Georges Harrisson", 100000));
        creerCompte(new CompteBancaire("Amine HALLILI", 150000));
        creerCompte(new CompteBancaire("Amine ELMALLEM", 150000));
        creerCompte(new CompteBancaire("Regis", 950000));
        creerCompte(new CompteBancaire("Toto toto", 20000));
        creerCompte(new CompteBancaire("Michel Buffa", 100000));
        creerCompte(new CompteBancaire("Pascal fasioni", 150000));
        creerCompte(new CompteBancaire("Sarah Misi", 950000));
        creerCompte(new CompteBancaire("Test Test", 20000));
        creerCompte(new CompteBancaire("Proprietaire DeCompte", 100000));
        System.out.print("*************** comptes de test créés ***********************");
    }

    public List<CompteBancaire> getAllComptes() {
        Query q = em.createNamedQuery("CompteBancaire.findAll");
        return q.getResultList();
    }

    public List<CompteBancaire> getAllComptes(int offset, int nbrParPage) {
        Query q = em.createNamedQuery("CompteBancaire.findAll");
        q.setMaxResults(nbrParPage);
        q.setFirstResult((offset - 1) * nbrParPage);
        return q.getResultList();
    }

    public void persist(Object object) {
        em.persist(object);
    }

    public CompteBancaire update(CompteBancaire compte) {
        return em.merge(compte);
    }

    public CompteBancaire getCompte(long id) {
        return em.find(CompteBancaire.class, id);
    }

    public boolean deleteCompte(CompteBancaire cb) {
        em.remove(em.merge(cb));
        return true;
    }

    public CompteBancaire merge(CompteBancaire cb) {
        return em.merge(cb);
    }

    public List<CompteBancaire> getComptesLike(String s) {
        
        Query q = em.createNamedQuery("CompteBancaire.findLikeCompte");
        q.setParameter("like", s);

        return q.getResultList();
    }

    public void transfert(long id1, long id2, double montant) {
        CompteBancaire c1 = em.find(CompteBancaire.class, id1);
        CompteBancaire c2 = em.find(CompteBancaire.class, id2);
        c1.crediter(montant);
        c2.debiter(montant);
    }
}
