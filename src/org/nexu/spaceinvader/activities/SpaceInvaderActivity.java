package org.nexu.spaceinvader.activities;

import org.nexu.spaceinvader.customsview.SpacePanel;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

public class SpaceInvaderActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SurfaceView panel = new SpacePanel(getApplicationContext());
        this.setContentView(panel);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
    
    
    @Override
    protected void onPause() {
    	super.onPause();
    }
    
}