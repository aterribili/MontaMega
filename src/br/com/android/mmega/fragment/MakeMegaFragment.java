package br.com.android.mmega.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import br.com.android.mmega.MainActivity;
import br.com.android.mmega.R;
import br.com.android.mmega.adapter.ListAdapter;
import br.com.android.mmega.extras.Extras;
import br.com.android.mmega.mega.Mega;

public class MakeMegaFragment extends Fragment {

	private TextView edMega;
	private ImageView btMega, btSave;
	private Switch swResults;
	private Spinner spOptions;
	private Context context;
	private boolean wwlResult;

	private final Random rd = new Random();
	private ArrayList<Integer> numbers;
	private List<String> all, biggers, smaller;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.mega_tab, container,
				false);

		context = rootView.getContext();

		MainActivity.megas = new ArrayList<Mega>();
		all = new ArrayList<String>();
		smaller = new ArrayList<String>();
		biggers = new ArrayList<String>();

		btMega = (ImageView) rootView.findViewById(R.id.btMega);
		btSave = (ImageView) rootView.findViewById(R.id.btSalvar);
		btSave.setVisibility(View.INVISIBLE);
		edMega = (TextView) rootView.findViewById(R.id.edTxtMega);

//		if (getArguments() != null)
//			edMega.setText(getArguments().getString(Extras.MEGA));

		swResults = (Switch) rootView.findViewById(R.id.swLastRes);
		spOptions = (Spinner) rootView.findViewById(R.id.spOptions);
		spOptions.setVisibility(View.INVISIBLE);
		ArrayAdapter<CharSequence> mAdapter = ArrayAdapter.createFromResource(
				rootView.getContext(), R.array.array_options,
				android.R.layout.simple_spinner_item);
		mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spOptions.setAdapter(mAdapter);

		createAleatory();

		try {
			InputStream is = MainActivity.am.open(Extras.FILENAME);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String l = "";

			while ((l = br.readLine()) != null) {
				all.add(l);
			}

			Collections.sort(all);
			Collections.reverse(all);

			for (int i = 0; i < 30; i++) {
				biggers.add(all.get(i));
			}
			for (int i = 30; i < 60; i++) {
				smaller.add(all.get(i));
			}
			is.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		btMega.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				edMega.setText(String.valueOf(createAleatory()));
				btSave.setVisibility(View.VISIBLE);
				wwlResult = false;
			}
		});

		swResults
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton compoundButton,
							boolean status) {
						if (status) {
							Toast.makeText(rootView.getContext(),
									R.string.work_with_last_results,
									Toast.LENGTH_LONG).show();
							spOptions.setVisibility(View.VISIBLE);

							btMega.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {

									if (spOptions.getSelectedItemPosition() == 0)
										edMega.setText(String
												.valueOf(montaMega(biggers)));

									else if (spOptions
											.getSelectedItemPosition() == 1)
										edMega.setText(String
												.valueOf(montaMega(smaller)));

									else
										edMega.setText(String
												.valueOf(montaMega(all)));

									btSave.setVisibility(View.VISIBLE);
									wwlResult = true;
								}
							});

						} else if (!status) {
							spOptions.setVisibility(View.INVISIBLE);
							btMega.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View v) {
									edMega.setText(String
											.valueOf(createAleatory()));
									btSave.setVisibility(View.VISIBLE);
									wwlResult = false;
								}
							});
						}
					}
				});

		btSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DateFormat dateFormat = new SimpleDateFormat(Extras.DATE_FORMAT);
				Calendar cal = Calendar.getInstance();

				cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
						cal.get(Calendar.DAY_OF_MONTH));
				Date aaa = cal.getTime();

				int awwlResult = wwlResult ? 1 : 0;

				if (MainActivity.db.add(new Mega(awwlResult, edMega.getText()
						.toString(), dateFormat.format(aaa).toString()), 15)) {

					ListMegaFragment.mListView.setAdapter(new ListAdapter(
							getActivity(), MainActivity.db.getAll()));

					Toast.makeText(context, R.string.saved_game,
							Toast.LENGTH_SHORT).show();

					btSave.setVisibility(View.INVISIBLE);

				} else {
					Toast.makeText(context, R.string.error_to_save,
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		return rootView;
	}

	private boolean verifyfNum(int number) throws Exception {
		boolean flag = false;
		if (number == 0) {
			flag = true;
		} else {
			for (int inteiro : numbers) {
				if (!flag)
					flag = (inteiro == number);
			}
		}
		return flag;
	}

	private String montarString(ArrayList<Integer> numbers) throws Exception {
		String strNum = null;
		for (int intr : numbers) {
			if (strNum == null) {
				strNum = formataNum(intr, 2);
			} else {
				strNum = strNum + Extras.LINE + formataNum(intr, 2);
			}
		}

		return strNum;
	}

	private String formataNum(int number, int tam) {
		String valor = String.valueOf(number);
		int ate = tam - valor.length();
		for (int x = 1; x <= ate; x++) {
			valor = Extras.ZERO + valor;
		}
		return valor;
	}

	private String createAleatory() {
		String result = "";
		try {
			numbers = new ArrayList<Integer>();
			int number;
			for (int i = 0; i < 6; i++) {
				number = rd.nextInt(60);

				while (verifyfNum(number)) {
					number = rd.nextInt(60);
				}
				numbers.add(number);
				Collections.sort(numbers);
				result = montarString(numbers);

			}
		} catch (Exception er) {

		}
		return result;
	}

	public String getNumero(String s) {
		String ms[] = s.split(Extras.EXPRESSION);
		return ms[1];
	}

	public String montaMega(List<String> lista) {
		Collections.shuffle(lista);
		numbers = new ArrayList<Integer>();
		int number;
		String result = null;

		for (int i = 0; i < 6; i++) {
			String s = getNumero(lista.get(i));

			number = Integer.parseInt(s);
			try {
				while (verifyfNum(number)) {
					number = rd.nextInt(60);
				}
				numbers.add(number);
				Collections.sort(numbers);
				result = montarString(numbers);

			} catch (Exception e) {
			}
		}
		return result;
	}

//	@Override
//	public void onDestroyView() {
//		if (getArguments() == null) {
//			Bundle b = new Bundle();
//			b.putString(Extras.MEGA, edMega.getText().toString());
//			this.setArguments(b);
//		}
//		super.onDestroyView();
//	}
}