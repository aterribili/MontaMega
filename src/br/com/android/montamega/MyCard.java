package br.com.android.montamega;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class MyCard extends Card {

	public MyCard(Mega mega){
		super(mega.getMega());
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_mega, null);

		((TextView) view.findViewById(R.id.title)).setText("Jogo da Mega");
		((TextView) view.findViewById(R.id.description)).setText(title);
		return view;
	}

	
	
	
}
