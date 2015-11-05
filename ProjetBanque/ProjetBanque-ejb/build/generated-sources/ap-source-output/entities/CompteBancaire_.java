package entities;

import entities.OperationBancaire;
import java.security.SecureRandom;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2014-12-20T11:40:38")
@StaticMetamodel(CompteBancaire.class)
public class CompteBancaire_ { 

    public static volatile SingularAttribute<CompteBancaire, Long> id;
    public static volatile SingularAttribute<CompteBancaire, Double> solde;
    public static volatile SingularAttribute<CompteBancaire, String> bigId;
    public static volatile SingularAttribute<CompteBancaire, String> owner;
    public static volatile SingularAttribute<CompteBancaire, SecureRandom> random;
    public static volatile CollectionAttribute<CompteBancaire, OperationBancaire> operations;

}