package cem.keven.pelletier.cashapp;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by kinpell on 23/04/2015.
 */
public class AjouterDialog extends DialogFragment {

    CRUD<Produit> repo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.ajouter_produit_form, container, false);

        final Button button = (Button)v.findViewById(R.id.ajouter);
        final EditText code = (EditText)v.findViewById(R.id.code);
        final EditText nom = (EditText)v.findViewById(R.id.nom);
        final EditText price = (EditText)v.findViewById(R.id.price);


        Bundle bundle = getArguments();
        if (bundle != null) {
            long codeBar  =  getArguments().getLong("code");
            code.setText(Long.toString(codeBar));
        }





        repo = RepositoryProduit.get();

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                    long c = Long.parseLong(code.getText().toString());
                    double prix = Double.parseDouble(price.getText().toString());
                    Produit p = new Produit(c, nom.getText().toString(), prix);

                    Log.i("Dialog", p.toString());
                    repo.save(p);


                AjouterDialog.this.dismiss();
            }
        });

        return v;
    }

    public static DialogFragment newInstance() {
        return new AjouterDialog();
    }

    public static DialogFragment newInstanceAvecCode(long codeBar) {
        AjouterDialog f = new AjouterDialog();

        Bundle args = new Bundle();
        args.putLong("code", codeBar);
        f.setArguments(args);

        return f;

    }

}
