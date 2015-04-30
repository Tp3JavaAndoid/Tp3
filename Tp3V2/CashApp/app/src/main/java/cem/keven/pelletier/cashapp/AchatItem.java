package cem.keven.pelletier.cashapp;

/**
 * Created by 1030672 on 2015-04-21.
 */
public class AchatItem {

    private Produit produit;
    private Integer qty;

    public Produit getProduit(){ return  produit;}
    public Integer getQty() {return  qty;}
    public void setQty(Integer q){
        qty = q;
    }
    public AchatItem(Produit prod,Integer quant){
        qty = quant;
        produit = prod;
    }


}
