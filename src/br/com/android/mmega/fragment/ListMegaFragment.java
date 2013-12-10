package br.com.android.mmega.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import br.com.android.mmega.MainActivity;
import br.com.android.mmega.R;
import br.com.android.mmega.adapter.ListAdapter;
import br.com.android.mmega.mega.Mega;

public class ListMegaFragment extends Fragment {
	public static ListView mListView;
	public static Mega mega;

	public ListMegaFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.favorits, container, false);

		mListView = (ListView) rootView.findViewById(R.id.listView);

		mListView.setAdapter(new ListAdapter(getActivity(), MainActivity.db
				.getAll()));

		registerForContextMenu(mListView);

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int posicao, long id) {
				mega = (Mega) adapter.getItemAtPosition(posicao);

				return false;
			}
		});

		return rootView;
	}

}
