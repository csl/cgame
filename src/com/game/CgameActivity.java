package com.game;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CgameActivity extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button A = (Button)findViewById(R.id.A);
        A.setOnClickListener(this);
        Button B = (Button)findViewById(R.id.B);
        B.setOnClickListener(this);
        Button C = (Button)findViewById(R.id.C);
        C.setOnClickListener(this);
        Button D = (Button)findViewById(R.id.D);
        D.setOnClickListener(this);
        Button E = (Button)findViewById(R.id.E);
        E.setOnClickListener(this);
    }
    
    public void onClick(View v) 
    {
    	Intent newAct = new Intent();
    	Bundle bData = null;
    	
    	int tmp=(int)((Math.random()*2)+1);  
    	
    	if (tmp == 1)
            newAct.setClass( CgameActivity.this, cshow.class );
    	else
            newAct.setClass( CgameActivity.this, videoshow.class );
    		
    	
    	switch (v.getId()) {
	        case R.id.A: // Do something when click button1
	            bData = new Bundle();
	            bData.putInt( "id", 1);
	            bData.putString( "title", "¤ô³¾«OÅ@°Ï");
	            break;
	        case R.id.B: // Do something when click button2
	            bData = new Bundle();
	            bData.putInt( "id", 2);
	            bData.putString( "title", "ÆQ¥Ð");
	            break;
	        case R.id.C: // Do something when click button3
	            bData = new Bundle();
	            bData.putInt( "id", 3);
	            bData.putString( "title", "¥x¦¿ÄH³bÀ]");
	            break;
	        case R.id.D: // Do something when click button4
	            bData = new Bundle();
	            bData.putInt( "id", 4);
	            bData.putString( "title", "¥|¯ó¬õ¾ðªL°Ï");
	            break;
	        case R.id.E: // Do something when click button5
	            bData = new Bundle();
	            bData.putInt( "id", 5);
	            bData.putString( "title", "¶ý¯ª¼q");
	            break;
    	}   	
    	
        newAct.putExtras( bData );
        startActivity( newAct );
        finish();

    }
}