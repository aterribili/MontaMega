package br.com.android.mmega;

import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import br.com.android.mmega.adapter.ListAdapter;
import br.com.android.mmega.extras.Extras;
import br.com.android.mmega.fragment.ListMegaFragment;
import br.com.android.mmega.fragment.MakeMegaFragment;
import br.com.android.mmega.mega.Mega;
import br.com.android.mmega.mega.MegaDAO;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	public static AssetManager am;
	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;

	public static List<Mega> megas;
	public static MegaDAO db;

	private ListMegaFragment megaFragment;
	private MakeMegaFragment makeMegaFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		am = this.getAssets();
		db = new MegaDAO(MainActivity.this);

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
				if (makeMegaFragment == null)
					makeMegaFragment = new MakeMegaFragment();
				return makeMegaFragment;
			default:
				if (megaFragment == null)
					megaFragment = new ListMegaFragment();
				return megaFragment;
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
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_about) {
			Intent about = new Intent(MainActivity.this, AboutActivity.class);
			startActivity(about);

			return false;
		} else {
			return super.onOptionsItemSelected(item);
		}

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

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_delete:
			db.remove(ListMegaFragment.mega.getId());
			ListMegaFragment.mListView.setAdapter(new ListAdapter(this, db
					.getAll()));
			Toast.makeText(this, R.string.remove, Toast.LENGTH_SHORT).show();
			return false;

		case R.id.action_share:
			Intent sendIntent = new Intent();
			sendIntent.setAction(Intent.ACTION_SEND);
			sendIntent.putExtra(Intent.EXTRA_TEXT, "Esse é meu jogo da Mega :"
					+ ListMegaFragment.mega.getMega());
			sendIntent.setType(Extras.INTENT);
			startActivity(Intent.createChooser(sendIntent, "Jogo"));
			return false;

		case R.id.action_copy:
			ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
			ClipData clip = ClipData.newPlainText(Extras.MEGA,
					ListMegaFragment.mega.getMega());
			Toast.makeText(this, R.string.copy, Toast.LENGTH_SHORT).show();
			clipboard.setPrimaryClip(clip);

			return false;
		default:
			return super.onContextItemSelected(item);
		}

	}

}
