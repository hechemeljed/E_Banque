/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT c FROM CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.findById", query = "SELECT c FROM CompteBancaire c WHERE c.id = :compteBancaireId"),
    @NamedQuery(name = "CompteBancaire.findBySolde", query = "SELECT c FROM CompteBancaire c WHERE c.solde = :solde"),
    @NamedQuery(name = "CompteBancaire.findLikeCompte", query = "SELECT cb FROM CompteBancaire cb WHERE lower(cb.owner) LIKE :like"),
    @NamedQuery(name = "CompteBancaire.findByOwner", query = "SELECT c FROM CompteBancaire c WHERE c.owner = :owner")})
public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bigId;
    private String owner;
    private double solde;
    private SecureRandom random = new SecureRandom();
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private Collection<OperationBancaire> operations = new ArrayList<OperationBancaire>();

    public Long getId() {
        return id;
    }

    public CompteBancaire() {
    }

    public CompteBancaire(String owner, double solde) {
        this.owner = owner;
        this.solde = solde;
        this.operations.add(new OperationBancaire("Création compte", solde, "----"));
        this.bigId = new BigInteger(130, random).toString(32);
    }

    /**
     * Get the value of solde
     *
     * @return the value of solde
     */
    public double getSolde() {
        return solde;
    }

    /**
     * Set the value of solde
     *
     * @param solde new value of solde
     */
    public void setSolde(double solde) {
        this.solde = solde;
    }

    /**
     * Get the value of owner
     *
     * @return the value of owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the value of owner
     *
     * @param owner new value of owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBigId() {
        return bigId;
    }

    public void setBigId(String bigId) {
        this.bigId = bigId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CompteBancaire[ id=" + id + " ]";
    }

    public void addOperationBancaire(OperationBancaire op) {
        this.operations.add(op);
    }

    public Collection<OperationBancaire> getOperations() {
        return operations;
    }

    public void setOperations(Collection<OperationBancaire> operations) {
        this.operations = operations;
    }

    public void debiter(double amount) {
        this.solde -= amount;
        addOperationBancaire(new OperationBancaire("Débit", amount, owner));
    }

    public void crediter(double amount) {
        this.solde += amount;
        addOperationBancaire(new OperationBancaire("Crédit", amount, owner));
    }
}
