package cem.keven.pelletier.cashapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import cem.keven.pelletier.cashapp.Produit;
import cem.keven.pelletier.cashapp.R;


public class MonAdapteur extends ArrayAdapter<AchatItem> {

	public MonAdapteur(Context context, List<AchatItem> objects) {
		super(context, R.layout.list_item, objects);
	}
	
	/**
	 * Methode qui produit la vue a inserer a cette position dans la liste
	 * La meme pour chaque element.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        // transformer le fichier XML en objets Java : gonfler
		LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View row = inflater.inflate(R.layout.list_item, parent, false);

        // le bouton plus se souvient qu'il correspond à cet item
        // seul moyen de distinguer tous les boutons +
        AchatItem item = getItem(position);
        row.findViewById(R.id.plusButton).setTag(item);
        row.findViewById(R.id.minusButton).setTag(item);


		// remplir le layout avec les bonnes valeurs
        TextView prodTV = (TextView) row.findViewById(R.id.item_produit);
        prodTV.setText(item.produit.getNom());

        TextView qtyTV = (TextView) row.findViewById(R.id.item_quantite);
        qtyTV.setText(String.valueOf(item.qty));

        // attache l'item a la ligne au complet si on veut réagir à un clic partout
        row.setTag(item);
		return row;
	}

}
