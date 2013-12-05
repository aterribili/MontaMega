package br.com.android.montamega.adapter;

import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.android.montamega.R;
import br.com.android.montamega.mega.Mega;

public class ListAdapter extends BaseAdapter {
	private final List<Mega> mList;
	private final FragmentActivity c;

	public ListAdapter(FragmentActivity c, List<Mega> mList) {
		this.mList = mList;
		this.c = c;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = c.getLayoutInflater().inflate(R.layout.item, null);

		Mega mega = mList.get(position);

		TextView tvMega = (TextView) view.findViewById(R.id.itemMega);
		TextView tvDate = (TextView) view.findViewById(R.id.dteMega);

		if (mega != null) {
			tvMega.setText(mega.getMega());
			tvDate.setText(mega.getDate());
		}

		return view;
	}

}
