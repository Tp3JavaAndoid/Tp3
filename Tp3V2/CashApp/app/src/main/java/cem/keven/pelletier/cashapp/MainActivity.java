package cem.keven.pelletier.cashapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends ActionBarActivity {

    MonAdapteur adapter;
    List<AchatItem> transaction;
    CRUD<Produit> repo;
    CRUD<Commande> repoCommande;
    Double total = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transaction  = new ArrayList<AchatItem>();

        repo = RepositoryProduit.get(getApplicationContext());
        repoCommande = RepositoryAchats.get(getApplicationContext());
        adapter = new MonAdapteur(MainActivity.this, transaction);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter);
        calculerTotal();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_creer) {

            repo.deleteAll();

            List<Produit> produits = new ArrayList<Produit>();

            produits.add(new Produit(Long.parseLong("7011043018498") ,"Céréales",5.45));
            produits.add(new Produit(Long.parseLong("6797413659379") ,"Lait 1 litre",5.95));
            produits.add(new Produit(Long.parseLong("7651921095861") ,"Beurre",4.96));
            produits.add(new Produit(Long.parseLong("5729286863776") ,"Chocolat",0.99));
            produits.add(new Produit(Long.parseLong("6075631136316") ,"Riz",3.99));
            produits.add(new Produit(Long.parseLong("6116088359160") ,"Salade",0.99));
            produits.add(new Produit(Long.parseLong("3298482123547") ,"Chips lays",2.99));
            produits.add(new Produit(Long.parseLong("1629924473432") ,"PopCorn",2.95));
            produits.add(new Produit(Long.parseLong("5648382418072") ,"Coca-Cola",1.98));
            produits.add(new Produit(Long.parseLong("5942916222891") ,"Jus Oasis",2.54));
            produits.add(new Produit(Long.parseLong("8038722591249") ,"Sauce Tomate",0.95));
            produits.add(new Produit(Long.parseLong("1589477250587") ,"Pain",2.49));
            produits.add(new Produit(Long.parseLong("7611473873003") ,"Yogourt",3.98));
            produits.add(new Produit(Long.parseLong("5088408786417") ,"Fromage",6.99));
            produits.add(new Produit(Long.parseLong("3481608318716") ,"Poulet",8.98));
            produits.add(new Produit(Long.parseLong("4407073486209") ,"Soupe",2.79));
            produits.add(new Produit(Long.parseLong("3593013272573") ,"Gateau",10.93));
            produits.add(new Produit(Long.parseLong("4233891349934") ,"Pizza",7.55));
            produits.add(new Produit(Long.parseLong("2657604046187") ,"BonBon",2.99));
            produits.add(new Produit(Long.parseLong("7224672377618") ,"Bacon",5.00));

            repo.saveMany(produits);

        }

        if (id == R.id.action_vider) {
            repo.deleteAll();
        }

        if (id == R.id.action_commande) {

            Random r = new Random();

            List<Produit> produits = repo.getAll();
            if(produits.size() > 0) {
                Produit[] p = new Produit[produits.size() + 1];
                int index = 0;

                for (Produit it : produits) {
                    p[index] = it;
                    index++;
                }

                for (int i = 0; i < 15; ++i) {
                    Produit produitAjouter = p[r.nextInt(20)];

                    boolean itemExist = false;

                    if (transaction.size() > 0)
                        for (AchatItem it : transaction) {

                            if (it.produit.getCodeBar() == produitAjouter.getCodeBar()) {

                                it.qty++;
                                itemExist = true;
                            }
                        }

                    if (itemExist != true) {
                        AchatItem achatItem = new AchatItem();
                        achatItem.produit = produitAjouter;
                        achatItem.qty = r.nextInt(10) + 1;
                        transaction.add(achatItem);
                    }
                }
                calculerTotal();
                adapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(getApplicationContext(),"Imposible de créer une commande la liste de produit est vide",Toast.LENGTH_LONG).show();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public void payer(View v){
        if(transaction.size() > 0) {

            Commande commande = new Commande(total, transaction);
            repoCommande.save(commande);


            List<Commande> commandes = repoCommande.getAll();
            Log.i("Dialog", "Liste des commandes");
            for (Commande c : commandes) {
                    Log.i("Dialog", c.toString());

            }

            transaction.clear();
            adapter.notifyDataSetChanged();
            calculerTotal();

        }
    }

    public void clicScanner(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    public void ajouterProduit(View v){

        DialogFragment fragAjouter = AjouterDialog.newInstance();
        fragAjouter.show(getFragmentManager(),"ajouterProduit");

    }

    public void minus(View v){
        AchatItem i = (AchatItem)v.getTag();
        if(i.qty > 1){
            i.qty--;
            calculerTotal();
            adapter.notifyDataSetChanged();
        }
        else{
            transaction.remove(i);
            calculerTotal();
            adapter.notifyDataSetChanged();
        }


    }

    public void plus(View v){
        AchatItem i = (AchatItem)v.getTag();
        i.qty++;
        calculerTotal();
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);


        if (scanResult.getContents() != null) {
            if(scanResult.getFormatName().matches("UPC_A|UPC_B|EAN_13|EAN_8")) {
                List<Produit> produits = repo.getAll();
                Long code = Long.parseLong(scanResult.getContents());
                boolean produitExits = false;
                boolean itemExist = false;
                for (Produit p : produits) {

                    if (p.getCodeBar() == code) {
                        if (transaction.size() > 0)
                            for (AchatItem item : transaction) {

                                if (item.produit.getCodeBar() == p.getCodeBar()) {

                                    item.qty++;
                                    itemExist = true;
                                }
                            }
                        if (itemExist != true) {
                            AchatItem i = new AchatItem();
                            i.produit = p;
                            i.qty = 1;
                            transaction.add(i);
                        }
                        produitExits = true;
                    }

                }
                if (produitExits == false) {

                    DialogFragment fragAjouter = AjouterDialog.newInstanceAvecCode(code);
                    fragAjouter.show(getFragmentManager(), "ajouterProduit");

                }


                Log.i("Main", "Scan result from scanner is  " + scanResult.getContents());

                calculerTotal();
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(getApplicationContext(),"Le code bar n'est pas du bon format",Toast.LENGTH_LONG).show();
            }

        }

    }

    public void calculerTotal (){

        Double t = 0.00;
        for(AchatItem i : transaction){
            t += i.produit.getPrix() * i.qty;
        }
        DecimalFormat df = new DecimalFormat("####0.00");
        TextView totalText = (TextView)findViewById(R.id.total);
        totalText.setText("Total: " + df.format(t));
        total = t;
    }


}
