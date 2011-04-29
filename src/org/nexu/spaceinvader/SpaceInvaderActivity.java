package org.nexu.spaceinvader;

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