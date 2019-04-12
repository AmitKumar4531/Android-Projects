package com.example.androidmusicplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SongList extends ListActivity {
    static String[] pl;
    int pos=0;
    Long current;
    static List<String > a=new ArrayList<String>();
    ArrayList<File> mysongs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_song_list);
		Intent ni=getIntent();
		Bundle b=ni.getExtras();
		  pos=b.getInt("pos");
		 // current=b.getLong("current");
		
		File r=Environment.getExternalStorageDirectory();
		mysongs=findSongs(r);
		pl=new String[mysongs.size()];
		for(int i=0;i<mysongs.size();i++)
		{
		  pl[i]=mysongs.get(i).getName().toString();
		 // pl[i]=pl[i]+"\t\t"+(mysongs.get(i).getUsableSpace()/(1024*1024)+" MB");
		}
		ArrayAdapter ad;
	    ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pl);
	    setListAdapter(ad);
	    
	    
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		pos=position;
		finish();
	/*	Intent i=new Intent();
		i.putExtra("pos",position);
		i.putExtra("songlist",mysongs);
		setResult(RESULT_OK,i);
		startActivity(i); */
		
	}
	
	/*public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Intent i=new Intent(SongList.this,MainActivity.class);
		i.putExtra("pos",position);
		i.putExtra("songlist",mysongs);
		startActivity(i);
	} */
	
	
	public ArrayList<File> findSongs(File root)
	{
		ArrayList<File> al=new ArrayList<File>();
		File[] files=root.listFiles();
		for (File f : files) 
		{
			if(f.isDirectory() && !f.isHidden())
			{
				al.addAll(findSongs(f));
			}
			else
			{
				if(f.getName().endsWith(".mp3") || f.getName().endsWith(".wav"))
					al.add(f);
			}
		}
		return al;
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		Intent i=new Intent();
		i.putExtra("pos",pos);
		i.putExtra("songlist",mysongs);
		//i.putExtra("current",current);
		setResult(RESULT_OK,i);
		super.finish();
	}
	
	
	
}
