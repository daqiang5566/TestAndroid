package com.example.testandroid.pulltorefresh;

import java.util.ArrayList;
import java.util.List;

import com.example.testandroid.R;
import com.example.testandroid.R.id;
import com.example.testandroid.R.layout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class ScrollViewGridViewActivity extends Activity {
	
	private List<String> list = new ArrayList<>();
	private GridView gridView1;
	private TestAdaper testAdaper = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview_gridview);
		gridView1 = (GridView) findViewById(R.id.gridView1);
		
		for (int i = 0; i < 100; i++) {
			list.add(i+"");
		}
		
		testAdaper = new TestAdaper(list, this);		
		gridView1.setAdapter(testAdaper);		
	}
	
	
	public class TestAdaper extends BaseAdapter{
		
		private List<String> list;
		private Context context;
		
		public TestAdaper(List<String> list,Context context){
			this.list 	 = list;
			this.context = context;
		}
		
		public void setData(List<String> tList){
			this.list = tList;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.activity_scrollview_gridview_item, null);
			}
			return convertView;
		}
		
	}

}
