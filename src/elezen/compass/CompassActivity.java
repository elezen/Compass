package elezen.compass;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CompassActivity extends Activity {
    private ImageView mImage;  
    private TextView mTv;
    private SensorManager mSensorManager;
    private boolean mFirst=true;
    private float lastDeg=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);
		mImage = (ImageView) findViewById(R.id.imageCompass);
		mTv=(TextView)findViewById(R.id.textViewDeg);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
	}

    @Override 
    protected void onResume() {  
        super.onResume();  
        mSensorManager.registerListener(myListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_NORMAL);
    }  

    @Override 
    protected void onPause() {  
        super.onPause();  

        mSensorManager.unregisterListener(myListener);  
    }  

    private void setDirection(float deg){
    	if(Math.abs(lastDeg-deg)<0.1F)return;
    	lastDeg=deg;
    	if(mFirst){
    		mImage.setPivotX(mImage.getWidth()/2);
    		mImage.setPivotY(mImage.getHeight()/2);
    		mFirst=false;
    	}
    	mImage.setRotation(-deg);
    	mTv.setText(String.format("% 5.01f",deg));
    }
	final SensorEventListener myListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			 if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {  
		            setDirection(sensorEvent.values[0]);
		        }  			
		}
		public void onAccuracyChanged(Sensor sensor, int accuracy) {}
	};



}
