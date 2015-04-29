package cem.keven.pelletier.cashapp;






import org.joda.time.DateTime;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by kinpell on 27/04/2015.
 */
public class Commande {

    double total;
    DateTime date;


    List<AchatItem> itemsTransaction ;

    private  Long id;

    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

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
