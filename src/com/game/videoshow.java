package com.game;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class videoshow extends Activity implements OnClickListener,OnErrorListener,
        OnBufferingUpdateListener, OnCompletionListener,
        MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {
    private static final String TAG = "VideoPlayer";

    private MediaPlayer mp;
    private SurfaceView mPreview;
    private EditText mPath;
    private TextView title;
    private int id;
    private SurfaceHolder holder;
    private ImageButton mPlay;
    private ImageButton mPause;
    private ImageButton mReset;
    private ImageButton mStop;
    private String current;

    /**
     * Called when the activity is first created.
     */
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        setContentView(R.layout.videoshow);

        title = (TextView)findViewById(R.id.title);

        Bundle bData = this.getIntent().getExtras();
        if (bData != null)
        {
        	id = bData.getInt( "id" );        
        	String titlebuf = bData.getString( "title" );        
	        title.setText(titlebuf);
        }
        
        // Set up the play/pause/reset/stop buttons
        mPreview = (SurfaceView) findViewById(R.id.sv);
        //mPath = (EditText) findViewById(R.id.path);
        //mPlay = (ImageButton) findViewById(R.id.play);
        //mPause = (ImageButton) findViewById(R.id.pause);
        //mReset = (ImageButton) findViewById(R.id.reset);
        //mStop = (ImageButton) findViewById(R.id.stop);

        Button A = (Button)findViewById(R.id.next);
        A.setOnClickListener(this);

        
        playVideo();

        /*
        mPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mp != null) {
                    mp.pause();
                }
            }
        });
        mReset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mp != null) {
                    mp.seekTo(0);
                }
            }
        });
        mStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mp != null) {
                    mp.stop();
                    mp.release();
                }
            }
        });
		*/
        
        // Set the transparency
        getWindow().setFormat(PixelFormat.TRANSPARENT);

        // Set a size for the video screen
        holder = mPreview.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(320, 200);
    }

    private void playVideo() {
        try {
            final String path = "/sdcard/" + id + ".wmv";
            Log.v(TAG, "path: " + path);

            // If the path has not changed, just start the media player
            if (path.equals(current) && mp != null) {
                mp.start();
                return;
            }
            current = path;

            // Create a new media player and set the listeners
            mp = new MediaPlayer();
            mp.setOnErrorListener(this);
            mp.setOnBufferingUpdateListener(this);
            mp.setOnCompletionListener(this);
            mp.setOnPreparedListener(this);
            mp.setAudioStreamType(2);

            // Set the surface for the video output
            mp.setDisplay((SurfaceHolder) mPreview.getHolder().getSurface());

            // Set the data source in another thread
            // which actually downloads the mp3 or videos
            // to a temporary location
            Runnable r = new Runnable() {
                public void run() {
                    try {
                        setDataSource(path);
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    try {
						mp.prepare();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    Log.v(TAG, "Duration:  ===>" + mp.getDuration());
                    mp.start();
                }
            };
            new Thread(r).start();
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
            if (mp != null) {
                mp.stop();
                mp.release();
            }
        }
    }

    /**
     * If the user has specified a local url, then we download the
     * url stream to a temporary location and then call the setDataSource
     * for that local file
     *
     * @param path
     * @throws IOException
     */
    private void setDataSource(String path) throws IOException {
        if (!URLUtil.isNetworkUrl(path)) {
            mp.setDataSource(path);
        } else {
            URL url = new URL(path);
            URLConnection cn = url.openConnection();
            cn.connect();
            InputStream stream = cn.getInputStream();
            if (stream == null)
                throw new RuntimeException("stream is null");
            File temp = File.createTempFile("mediaplayertmp", "dat");
            String tempPath = temp.getAbsolutePath();
            FileOutputStream out = new FileOutputStream(temp);
            byte buf[] = new byte[128];
            do {
                int numread = stream.read(buf);
                if (numread <= 0)
                    break;
                out.write(buf, 0, numread);
            } while (true);
            mp.setDataSource(tempPath);
            try {
                stream.close();
            }
            catch (IOException ex) {
                Log.e(TAG, "error: " + ex.getMessage(), ex);
            }
        }
    }

    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        Log.e(TAG, "onError--->   what:" + what + "    extra:" + extra);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        
        return false;
    }

    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.d(TAG, "onBufferingUpdate called --->   percent:" + percent);
    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
    }

    public void surfaceCreated(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceCreated called");
    }

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(TAG, "surfaceChanged called");
    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceDestroyed called");
    }
    
    public void onClick(View v) 
    {
    	Intent newAct = new Intent();
    	Bundle bData = null;
    	
    	switch (v.getId()) {
	        case R.id.next: // Do something when click button1
	        	
                if (mp != null) {
                    mp.stop();
                    mp.release();
                }
	        	
	            newAct.setClass( videoshow.this, cshow.class );
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
