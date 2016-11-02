package com.example.testandroid.playAudio;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.testandroid.R;

public class AudioActivity extends Activity {
	
	private MediaPlayer mp;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_playaudio);
		mp = new MediaPlayer();
	}
	
	public void playAudio(View v){
		try {
			File SDPath = Environment.getExternalStorageDirectory();
			String pathStr = SDPath.getAbsoluteFile() + File.separator + "msg_482115031016fa0c69d9f34106.amr";
			//pathStr =  "/storage/sdcard0/nightse/12959105/msg/voice/6e1370e8809046b787b128d663a1c7cb";			
			Log.i("Tag", "------------------------" + pathStr);			
			File file = new File(pathStr);
			if(file.exists()){
				mp.reset();
				mp.setDataSource(pathStr);
				mp.prepare();
				mp.start();	
			}else{
				Log.i("Tag", "------------------------");
			}			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
