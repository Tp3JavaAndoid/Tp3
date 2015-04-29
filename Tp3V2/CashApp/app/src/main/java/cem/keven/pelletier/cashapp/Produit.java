package cem.keven.pelletier.cashapp;

/**
 * Created by 1030672 on 2015-04-21.
 */
public class Produit {
    /* CHAMPS  */
    private  Long id;
    private String nom;
    private long codeBar;
    private double prix;
    /*=================================================*/
    /* MÉTHODES */
   public double getPrix(){return prix;}

    public String getNom(){
        return nom;
    }

    public long getCodeBar(){
        return codeBar;
    }
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    /*=================================================*/
    /*CONSTRUCTEUR */
    public Produit(long c,String n,double p){
        nom = n;
        codeBar = c;
        prix = p;
    }
    /*==================================================*/
    @Override
    public String toString(){
        return nom;
    }

}
