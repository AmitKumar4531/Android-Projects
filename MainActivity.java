package com.example.androidmusicplayer;


import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	Button play,prev,rew,next,ff,slist;
	ToggleButton repeat,shuffle;
	SeekBar sb1;
	TextView tv1,cd,td;
	MediaPlayer mp;
	
	ArrayList<File> mysongs;
	int pos=0;
	private final Handler handler = new Handler();
	final int VAL_REQ_CODE=12;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		play=(Button)findViewById(R.id.b1);
		prev=(Button)findViewById(R.id.b3);
		rew=(Button)findViewById(R.id.b2);
		next=(Button)findViewById(R.id.b5);
		ff=(Button)findViewById(R.id.b4);
		shuffle=(ToggleButton)findViewById(R.id.tb1);
		repeat=(ToggleButton)findViewById(R.id.tb2);
		slist=(Button)findViewById(R.id.b8);
		sb1=(SeekBar)findViewById(R.id.sb1);
		tv1=(TextView)findViewById(R.id.tv1);
		td=(TextView)findViewById(R.id.td);
		cd=(TextView)findViewById(R.id.cd);
		final File r=Environment.getExternalStorageDirectory();
	/*	Intent p=getIntent();
		Bundle b=p.getExtras();
		if(mp.isPlaying())
		{
			mp.stop();
			mp.release();
		}
		if(b!=null)
		{
		mysongs=(ArrayList)b.getParcelableArrayList("songlist");
		pos=b.getInt("pos",0);
		Uri u=Uri.parse(mysongs.get(pos).toString());
		mp=MediaPlayer.create(this,u);
		mp.start();
		play.setBackgroundResource(android.R.drawable.ic_media_pause);
		}
		else
		{
			mp=MediaPlayer.create(this,R.raw.seeuagain); 
		}	
	    String path=r.getPath()+"/"+SongList.pl[pos];
		String path=r.getPath()+"/seeuagain.mp3";
		mp=new MediaPlayer();
		try
		{
		 mp.setDataSource(path);
		 mp.prepare();
		}
		catch(Exception e)
		{
			Toast.makeText(MainActivity.this,"Media player not found",5000).show();
		}
		
		*/
		mp=MediaPlayer.create(this,R.raw.seeuagain);
		prev.setEnabled(false);
		next.setEnabled(false);
		long duration=mp.getDuration();
		long dis=duration/1000;
		long dim=dis/60;
		long rs=dis%60;
		String dm=""+dim;
		String rss=""+rs;
		if(dim<10)
			dm="0"+dim;
		if(rs<10)
			rss="0"+rs;
		String dur=dm+":"+rss;
		td.setText(dur);
		sb1.setMax((int)dis);
		
    /*    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp1) {
				// TODO Auto-generated method stub
				sb1.setProgress(0);
               if((repeat.getText().toString()).equals("All"))
               {
            	   pos=(pos+1)%mysongs.size();
					//repeat.setText("Single");
				}
				else
				{
					//do something
					
				}
			
                mp.stop();
				mp.release();
				Uri u=Uri.parse(mysongs.get(pos).toString());
				mp=MediaPlayer.create(MainActivity.this,u);
				mp.start();
				play.setBackgroundResource(android.R.drawable.ic_media_pause);
       		    onCompletion(mp);
               //repeat.setText("All");
			    play.setBackgroundResource(android.R.drawable.ic_media_play);
			    mp=MediaPlayer.create(MainActivity.this,R.raw.seeuagain); 
			     
			}
		});  */
		
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Toast.makeText(MainActivity.this,"SEEK BAR RELEASED",500).show();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			//	Toast.makeText(MainActivity.this,"SEEK BAR STARTED",500).show();
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				// TODO Auto-generated method stub
				if(fromUser)
				   mp.seekTo(progress*1000);
				String s1=""+progress/60;
				String s2=""+progress%60;
				if((progress/60)<10)
					s1="0"+s1;
				if((progress%60)<10)
					s2="0"+s2;
				cd.setText(s1+":"+s2);
				
			}
		});
       
		
		play.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!mp.isPlaying())
				{
					mp.start();
					startPlayProgressUpdater();
					play.setBackgroundResource(android.R.drawable.ic_media_pause);
					
				}
				else
				{
					mp.pause();
					startPlayProgressUpdater();
					play.setBackgroundResource(android.R.drawable.ic_media_play);
				}
			}
				
			
		});
		
		prev.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mp.stop();
				mp.release();
				pos=(pos-1)%mysongs.size();
				Uri u=Uri.parse(mysongs.get(pos).toString());
				mp=MediaPlayer.create(MainActivity.this,u);
				mp.start();
				play.setBackgroundResource(android.R.drawable.ic_media_pause);
				long duration=mp.getDuration();
				long dis=duration/1000;
				long dim=dis/60;
				long rs=dis%60;
				String dm=""+dim;
				String rss=""+rs;
				if(dim<10)
					dm="0"+dim;
				if(rs<10)
					rss="0"+rs;
				String dur=dm+":"+rss;
				td.setText(dur);
				sb1.setMax((int)dis);
				
				
			}
		});
		
		rew.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				long cd=mp.getCurrentPosition();
				long np=cd-5000;
				if(np>0)
					mp.seekTo((int)np);	
			}
		});
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mp.stop();
				mp.release();
				pos=(pos+1)%mysongs.size();
				Uri u=Uri.parse(mysongs.get(pos).toString());
				mp=MediaPlayer.create(MainActivity.this,u);
				mp.start();
				play.setBackgroundResource(android.R.drawable.ic_media_pause);
				long duration=mp.getDuration();
				long dis=duration/1000;
				long dim=dis/60;
				long rs=dis%60;
				String dm=""+dim;
				String rss=""+rs;
				if(dim<10)
					dm="0"+dim;
				if(rs<10)
					rss="0"+rs;
				String dur=dm+":"+rss;
				td.setText(dur);
				sb1.setMax((int)dis);
				
			}
		});
		
		ff.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				long cd=mp.getCurrentPosition();
				long np=cd+5000;
				if(np<mp.getDuration())
					mp.seekTo((int)np);
			}
		});
		
		shuffle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                if(shuffle.isChecked())
                {
                	Toast.makeText(MainActivity.this,"Shuffle Mode Activated",2000).show();
                }
                else
                	Toast.makeText(MainActivity.this,"Shuffle Mode Deactivated",2000).show();
                	

			}
		});
		
		repeat.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(repeat.isChecked())
                {
                	Toast.makeText(MainActivity.this,"Repeat Mode Activated",2000).show();
                }
                else
                	Toast.makeText(MainActivity.this,"repeat Mode Deactivated",2000).show();
			}
		});
		
		slist.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,SongList.class);
				i.putExtra("pos",pos);
				//i.putExtra("current",mp.getCurrentPosition());
				startActivityForResult(i,VAL_REQ_CODE);
				
			}
		});
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Bundle b=data.getExtras();
		next.setEnabled(true);
		prev.setEnabled(true);
		if(b!=null)
		{
		mysongs=(ArrayList)b.getParcelableArrayList("songlist");
		pos=b.getInt("pos",0);
		Uri u=Uri.parse(mysongs.get(pos).toString());
		mp.stop();
		mp.release();
		mp=MediaPlayer.create(this,u);
		//mp.setLooping(true);
		mp.start();
		startPlayProgressUpdater();
		tv1.setText(mysongs.get(pos).getName().toString());
		play.setBackgroundResource(android.R.drawable.ic_media_pause);
		}
		else
		{
			mp=MediaPlayer.create(this,R.raw.seeuagain); 
		}	
		
		long duration=mp.getDuration();
		long dis=duration/1000;
		long dim=dis/60;
		long rs=dis%60;
		String dm=""+dim;
		String rss=""+rs;
		if(dim<10)
			dm="0"+dim;
		if(rs<10)
			rss="0"+rs;
		String dur=dm+":"+rss;
		td.setText(dur);
		sb1.setMax((int)dis);
		
	}

	
	public void startPlayProgressUpdater() {
    	sb1.setProgress(mp.getCurrentPosition()/1000);
    	
    	if(cd.getText().toString().equals(td.getText().toString()))
    	{
    	    if(!repeat.isChecked())
    	    {
    	    	if(shuffle.isChecked())
    	    	{
    	    		Random rand = new Random();
					pos=rand.nextInt(mysongs.size());
    	    	}
    	    	else
    			   pos=(pos+1)%mysongs.size();
    	    }	
    		else if(repeat.isChecked())
    			pos=pos;
    		sb1.setProgress(0);
			Uri u=Uri.parse(mysongs.get(pos).toString());
			mp.stop();
			mp.release();
			mp=MediaPlayer.create(this,u);
			mp.start();
			startPlayProgressUpdater();
			play.setBackgroundResource(android.R.drawable.ic_media_pause);
			tv1.setText(mysongs.get(pos).getName().toString());
			long duration=mp.getDuration();
			long dis=duration/1000;
			long dim=dis/60;
			long rs=dis%60;
			String dm=""+dim;
			String rss=""+rs;
			if(dim<10)
				dm="0"+dim;
			if(rs<10)
				rss="0"+rs;
			String dur=dm+":"+rss;
			td.setText(dur);
			sb1.setMax((int)dis);
			
    	}
 
		if (mp.isPlaying()) {
			Runnable r= new Runnable() {
		        public void run() {
		        	startPlayProgressUpdater();
				}
		    };
		    handler.postDelayed(r,1000);
    	}
		else{
			mp.pause();
			}
    } 

	
}
