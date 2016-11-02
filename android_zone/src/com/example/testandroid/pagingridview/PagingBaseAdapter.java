package com.example.testandroid.pagingridview;

import java.util.ArrayList;
import java.util.List;

import android.widget.BaseAdapter;


public abstract class PagingBaseAdapter<T> extends BaseAdapter {

	protected List<T> items;

	public PagingBaseAdapter() {
		this.items = new ArrayList<T>();
	}

	public PagingBaseAdapter(List<T> items) {
		this.items = items;
	}

	public void addMoreItems(List<T> newItems) {
		this.items.addAll(newItems);
		notifyDataSetChanged();
	}

	public void removeAllItems() {
		this.items.clear();
		notifyDataSetChanged();
	}


}
