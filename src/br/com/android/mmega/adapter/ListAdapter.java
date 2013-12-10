package br.com.android.mmega.adapter;

import java.util.List;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.android.mmega.R;
import br.com.android.mmega.mega.Mega;

public class ListAdapter extends BaseAdapter {
	private final List<Mega> mList;
	private final FragmentActivity c;
	private TextView tvMega;
	private TextView tvDate;
	private ImageView imMega;

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

		// if (tvMega == null)
		tvMega = (TextView) view.findViewById(R.id.itemMega);
		// if (tvDate == null)
		tvDate = (TextView) view.findViewById(R.id.dteMega);
		// if (imMega == null)
		imMega = (ImageView) view.findViewById(R.id.imMega);

		if (mega != null) {
			tvMega.setText(mega.getMega());
			tvDate.setText(mega.getDate());
			if (mega.getLastResult() == 1) {
				imMega.setVisibility(View.VISIBLE);
			} else {
				imMega.setVisibility(View.INVISIBLE);
			}
		}

		return view;
	}
}
