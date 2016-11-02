package com.example.testandroid;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.example.testandroid.CustomViewGroup.ViewGroupActivity;
import com.example.testandroid.blur.BlurActivity;
import com.example.testandroid.circle.CircleActivity;
import com.example.testandroid.circle.CircleProgressActivity;
import com.example.testandroid.circle.MatrialProgressActivity;
import com.example.testandroid.circularfloatingactionmenu.samples.DemoActivity;
import com.example.testandroid.countdowview.CountdownActivity;
import com.example.testandroid.customview.CustomActvity;
import com.example.testandroid.gif.GifActivity;
import com.example.testandroid.gif.GifView2;
import com.example.testandroid.marquee.MarqueeActivity;
import com.example.testandroid.matirx.MatirxActivity;
import com.example.testandroid.pagingridview.example.CustomPagingGirdViewActivity;
import com.example.testandroid.pinnedSectionListView.PinnedSectionListActivity;
import com.example.testandroid.playAudio.AudioActivity;
import com.example.testandroid.pulldown_scrollview.PulldownViewActivity;
import com.example.testandroid.pulltorefresh.ScrollViewGridViewActivity;
import com.example.testandroid.swiperefresh.SwipeRefreshActivity;
import com.example.testandroid.testevent.TestEventActivity;
import com.example.testandroid.thread.ThreadActivity;
import com.example.testandroid.videoview.VedioPreviewActivity;
import com.example.testandroid.videoview.VideoViewActivity;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = this.getLayoutInflater().from(this).inflate(R.layout.activity_main, null, false);
		setContentView(view);

		testEnum();

		android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setCustomView(R.layout.activitybar_custom);
		actionBar.setTitle("朋友圈");

		findViewById(R.id.pulldown_scrollview_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								PulldownViewActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.listview_section_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								PinnedSectionListActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.blur_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								BlurActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.circleProgress_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								CircleProgressActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.matrialProgress_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								MatrialProgressActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.custon_radio_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								CutomRadioActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.custon_cricle_btn).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								DemoActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.custon_test_event).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								TestEventActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.custon_paging_gridview).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								CustomPagingGirdViewActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.custom_gridview).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								ScrollViewGridViewActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.marquee).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								MarqueeActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.countdown).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								CountdownActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.customgirdview).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});

		findViewById(R.id.view_group).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								ViewGroupActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.view_swipe_refresh).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								SwipeRefreshActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.view_swipe_refresh).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								SwipeRefreshActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.thread_test).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								ThreadActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.circle_test).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								CircleActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.video_test).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								VideoViewActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.audio_test).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								AudioActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.layer_list).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								LayerListActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.video_preview).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								VedioPreviewActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.matrix).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								MatirxActivity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.customview).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MainActivity.this,
								CustomActvity.class);
						startActivity(intent);
					}
				});

		findViewById(R.id.gif).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, GifActivity.class);
				startActivity(intent);
			}
		});

		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = tm.getDeviceId();
		Log.i("dddd", "deviceId = " + deviceId);

		/*
		 * String rootPath = SDCardUtils.getRootDirectoryPath(); String
		 * extralPath = SDCardUtils.getSDCardPath(); gifView2 = new
		 * GifView2(this); gifView2.setMovieResource(R.drawable.gif_zem71);
		 * 
		 * Log.i("duration", "duration = " + gifView2.getMovie().duration());
		 * 
		 * gifView2.setPlayTime(1, new OnGifCompletionListener() {
		 * 
		 * @Override public void onCompletion() { // TODO Auto-generated method
		 * stub gifView2.setMovieTime(0); gifView2.setPaused(true); } });
		 * 
		 * view.addView(gifView2);
		 */
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.custom_main, menu); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); return
	 * super.onOptionsItemSelected(item); }
	 */

	private void testEnum() {
		Plant plant = Plant.MOON;
		Plant plant1 = Plant.SUN;

		Log.i("plant", "plant = " + plant);
		Log.i("plant", "plant1 = " + plant1);
		Log.i("plant", "sum = " + plant1.sum());
		Log.i("plant", "sum = " + plant.sum());
	}

	private enum Plant {
		TUXING(1, 1), MUXING(2, 2), MOON(3, 3), SUN(4, 4);

		private Plant(int x, int y) {
			this.x = x;
			this.y = y;
		}

		private int sum() {
			return this.x + this.y;
		}

		private int x;
		private int y;
	}
}
