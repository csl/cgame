package com.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class cshow extends Activity implements OnClickListener
{
    /** Called when the activity is first created. */
	
	String [] intr;
	TextView title;
	TextView content;
	int id;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cshow);

        title = (TextView)findViewById(R.id.title);
        content = (TextView)findViewById(R.id.content);
        intr = new String[5];

        Bundle bData = this.getIntent().getExtras();
        if (bData != null)
        {
        	id = bData.getInt( "id" );        
        	String titlebuf = bData.getString( "title" );        
        
        	//intr
	        intr[0] = "冬天將至，超可愛的候鳥黑面琵鷺要來台灣過冬了，我們到保護區瞧瞧吧 水鳥保護區內含多種溼地形態，是水鳥與動植物最豐富的區域。其中有國家保護級黑面琵鷺、小白鷺、東方環頸鴴等多達數十種水鳥，依季節不同，還有不同候鳥來此過冬，豐富當地生態。政府也完整保存水鳥生態，為水鳥界保存界的一塊瑰寶";
	        intr[1] = "遠遠的看過去好像一座積滿雪的山喔，台灣怎麼會有雪，喔~原來那不是雪，是...傳統曬鹽的步驟繁雜，曬鹽的方式已絕跡，目前在四草還保留住的 曬鹽，留為教學所用，台鹽並將其製成的鹽製成鹹冰棒販售，在台江當地的鹽田保留區，將其曬成的鹽染色，以供遊客將多彩的鹽DIY裝進瓶罐裡作為紀念品";
	        intr[2] = "鹽田的阿姨說，台江有鯨魚的傳說，是鯨魚不是金魚喔...台江鯨豚館為台灣最大的鯨豚館，館內展出東南亞第一大鯨豚標本－抹香鯨標本，館內除巨型抹香鯨骨骼標本以外，也有小鬚鯨、小虎鯨、島久鯨等等懸吊式標本，同時配合掛圖說明，以利教育及導覽解說使用。台南市府利用原南寮鹽村舊鹽民活動中心整修作為標本的暫時存放處所，並命名為台江鯨豚館";
	        intr[3] = "走出鯨魚館，赫然發現身旁有群遊客，正搭著膠筏在探險..好奇怪的我們， 立即動身前往查看";
	        intr[4] = "走了一整天的台江，想到爺爺年紀大，去幫他求一支平安籤吧!";
	        
	        title.setText(titlebuf);
	        content.setText(intr[id-1]);	        
        }
        Button A = (Button)findViewById(R.id.next);
        A.setOnClickListener(this);
    }
    
    public void onClick(View v) 
    {
    	Intent newAct = new Intent();
    	Bundle bData = null;
    	
    	switch (v.getId()) {
	        case R.id.next: // Do something when click button1
	            newAct.setClass( cshow.this, cshow.class );
	            bData = new Bundle();
	            
	            if (id == 1)
	            	bData.putInt( "id", 100);
	            else if (id == 2)
	            	bData.putInt( "id", 70);
	            else if (id == 3)
	            	bData.putInt( "id", 50);
	            else if (id == 4)
	            	bData.putInt( "id", 30);

	            newAct.putExtras( bData );
	            startActivity( newAct );	
	            finish();
	            break;
	            
    	}   	
    }
}