package org.nexu.spaceinvader.activities;

import org.nexu.spaceinvader.R;
import org.nexu.spaceinvader.R.layout;
import org.nexu.spaceinvader.customsview.SpacePanel;

import android.app.Activity;
import android.os.Bundle;

public class SpaceInvaderActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SpacePanel panel = new SpacePanel(getApplicationContext());
        this.setContentView(panel);
    }
}