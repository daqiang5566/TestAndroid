package com.example.testandroid.gif;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.example.testandroid.R;
import com.example.testandroid.gif.GifView2.OnGifCompletionListener;

public class GifActivity extends Activity {

	private GifView2 gifView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		gifView2 = new GifView2(this);
		gifView2.setMovieResource(R.drawable.gif_zem71);

		Log.i("duration", "duration = " + gifView2.getMovie().duration());

		gifView2.setPlayTime(1, new OnGifCompletionListener() {

			@Override
			public void onCompletion() {
				// TODO Auto-generated method stub
				gifView2.setMovieTime(0);
				gifView2.setPaused(true);
			}
		});

		setContentView(gifView2);
	}
}
