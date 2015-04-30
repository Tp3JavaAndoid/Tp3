package cem.keven.pelletier.cashapp;






import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kinpell on 27/04/2015.
 */
public class Commande {
   /*CHAMPS*/
    private  Long id;
    private double total;
    private DateTime date;
    private List<AchatItem> itemsTransaction ;

    /*METHODES*/
    public Long getId() {
        return id;
    }
    public double getTotal(){
        return total;
    }
    public DateTime getDate(){
        return  date;
    }
    public List<AchatItem> getItemsTransaction(){
        return itemsTransaction;
    }
    public void setId(long id) {
        this.id = id;
    }
    /* CONSTRUCTEUR */
    public  Commande(double totalT,List<AchatItem> items){
        total = totalT;
        date = DateTime.now();
        itemsTransaction = items;


    }

    @Override
    public String toString(){
        DecimalFormat df = new DecimalFormat("####0.00");
        return date.toString() + " " + df.format(total);
    }



}
