package br.com.android.montamega.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.android.montamega.R;
import br.com.android.montamega.adapter.ListAdapter;
import br.com.android.montamega.mega.MegaDAO;

public class ListMegaFragment extends Fragment {
	private MegaDAO db;
	public static ListView mListView;

	public ListMegaFragment() {
		this.db = new MegaDAO(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.favorits, container, false);

		mListView = (ListView) rootView.findViewById(R.id.listView);

		db = new MegaDAO(getActivity());
		mListView.setAdapter(new ListAdapter(getActivity(), db.getAll()));

		registerForContextMenu(mListView);

		return rootView;
	}
}
