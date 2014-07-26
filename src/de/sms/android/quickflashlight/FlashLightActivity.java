package de.sms.android.quickflashlight;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class FlashLightActivity extends Activity {

	private Camera camera;
	
	private Parameters p;
	
	private Button button;
	
	private RelativeLayout layout;
	
	@Override
	public void finish() {
		super.finish();
		
		if (camera != null && p != null) {
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(p);
			camera.stopPreview();
			camera.release();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		button = (Button) findViewById(R.id.buttonFlashlight);
		layout = (RelativeLayout) findViewById(R.id.relativeLayout);

		Context context = this;
		PackageManager pm = context.getPackageManager();

		// if device support camera?
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return;
		}

		camera = Camera.open();
		p = camera.getParameters();
		
		if (isLightOn(camera))
		{
			setLigthOff(camera);
		} 
		else 
		{
			setLightOn(camera);
		}

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (isLightOn(camera))
				{
					setLigthOff(camera);
				} 
				else 
				{
					setLightOn(camera);
				}
			}
		});

	}
	
	
	private boolean isLightOn(Camera camera)
	{
		Parameters param = camera.getParameters();
		String flashMode = param.getFlashMode();
		if(flashMode.equals(Parameters.FLASH_MODE_OFF))
		{
			return false;
		}
		return true;
	}
	
	
	private void setLightOn(Camera camera)
	{
		p.setFlashMode(Parameters.FLASH_MODE_TORCH);

		camera.setParameters(p);
		camera.startPreview();
		button.setText(R.string.off);
		layout.setBackgroundColor(Color.YELLOW);
	}
	
	
	private void setLigthOff(Camera camera)
	{
		p.setFlashMode(Parameters.FLASH_MODE_OFF);
		camera.setParameters(p);
		camera.stopPreview();
		button.setText(R.string.on);
		layout.setBackgroundColor(Color.BLACK);
	}
}