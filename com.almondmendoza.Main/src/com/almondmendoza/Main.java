package com.almondmendoza;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.almondmendoza.drawings.DrawingActivity;

public class Main extends Activity
{
    private Button drawBtn;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        drawBtn = (Button) findViewById(R.id.drawBtn);
        
        drawBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				Intent drawIntent = new Intent(Main.this, DrawingActivity.class);
                startActivity( drawIntent);
			}
		});
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.drawBtn:
                Intent drawIntent = new Intent(this, DrawingActivity.class);
                startActivity( drawIntent);
                break;
        }
    }
}
