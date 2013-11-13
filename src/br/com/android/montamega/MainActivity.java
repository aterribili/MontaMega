package br.com.android.montamega;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.fima.cardsui.views.CardUI;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	public static CardUI mCardView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				TelaMega telaMega = new TelaMega();
				return telaMega;
			default:
				Favorits favorits = new Favorits();
				return favorits;
			}
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			}
			return null;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public static class TelaMega extends Fragment {
		private EditText edTxtMega;
		private Button btMega, btSalvar;
		private final Random rd = new Random();
		private ArrayList<Integer> numbers;
		private Switch swResults;
		private DatabaseHandler db;
		private Toast toast;
		Context context;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.mega, container, false);
			
				
			context = rootView.getContext();
			btMega = (Button) rootView.findViewById(R.id.btMega);
			btSalvar = (Button) rootView.findViewById(R.id.btSalvar);
			btSalvar.setEnabled(false);
			edTxtMega = (EditText) rootView.findViewById(R.id.edTxtMega);
			edTxtMega.setEnabled(false);
			edTxtMega.setInputType(0); // Force to hide keyboard
			swResults = (Switch) rootView.findViewById(R.id.swResults);
			db = new DatabaseHandler(rootView.getContext());
			toast = Toast.makeText(rootView.getContext(),"Gera uma sequência de números de acordo com os últimos resultados da Mega",Toast.LENGTH_LONG);
			
			createAleatory();

			btSalvar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					// Save data mega on SQLite

					// popularList();
					// toastEr.show();
					db.insertTest(new Mega(edTxtMega.getText().toString()), 10);
					Toast.makeText(context, "Jogo Salvo", Toast.LENGTH_SHORT)
							.show();
					// } else {
					// Toast.makeText(context, "Erro ao Salvar",
					// Toast.LENGTH_SHORT).show();
					// }
					clearList();
					for (Mega mega : teste(context)) {
						mCardView.addCard(new MyCard(mega));
					}
					
					mCardView.refresh();

				}
			});

			swResults.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(
								CompoundButton compoundButton, boolean b) {
							if (b) {
								toast.show();
								takeOnBd();
								// popularList();
							} else if (!b) {
								createAleatory();
								// popularList();
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

		private String montarString(ArrayList<Integer> numbers)
				throws Exception {
			String strNum = null;
			for (int intr : numbers) {
				if (strNum == null) {
					strNum = formataNum(intr, 2);
				} else {
					strNum = strNum + " - " + formataNum(intr, 2);
				}
			}

			return strNum;
		}

		private String formataNum(int number, int tam) {
			String valor = String.valueOf(number);
			int ate = tam - valor.length();
			for (int x = 1; x <= ate; x++) {
				valor = "0" + valor;
			}
			return valor;
		}

		private void createAleatory() {
			btMega.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
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
							String result = montarString(numbers);
							edTxtMega.setText(String.valueOf(result));
							btSalvar.setEnabled(true);
						}
					} catch (Exception er) {
					}
				}
			});
		}

		private void takeOnBd() {
			btMega.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					edTxtMega.setText("Work");
					btSalvar.setEnabled(true);
				}
			});
		}

		/*
		 * private String giveDate() { Calendar cal = Calendar.getInstance();
		 * SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
		 * return sdf.format(cal.getTime()); }
		 */
	}

	public static class Favorits extends Fragment {
		//private DatabaseHandler db;
		Context context;
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.card_main, container,
					false);

			mCardView = (CardUI) rootView.findViewById(R.id.cardsview);
			//db = new DatabaseHandler(rootView.getContext());
			//List<Mega> listMega = db.getAllMega();

			for (Mega mega : teste(rootView.getContext())) {
				mCardView.addCard(new MyCard(mega));
			}
			
			mCardView.refresh();
			
			return rootView;
		}
	}
	
	public static List<Mega> teste(Context context){
		DatabaseHandler db;
		db = new DatabaseHandler(context);
		List<Mega> listMega = db.getAllMega();
		return listMega;
	}
	public static void clearList(){
		mCardView.clearCards();
	}
}
