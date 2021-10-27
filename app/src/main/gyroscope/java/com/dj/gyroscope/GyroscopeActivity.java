package com.dj.gyroscope;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.logutil.LogUtils;

import butterknife.ButterKnife;

public class GyroscopeActivity extends BaseActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor magneticSensor;
    private Sensor accelerometerSensor;
    private float[] gravity;
    private float[] r;
    private float[] geomagnetic;
    private float[] values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gyroscope);
        ButterKnife.bind(this);

        /**
         * 初始化传感器
         * */
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //获取Sensor
        magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //初始化数组
        gravity = new float[3];//用来保存加速度传感器的值
        r = new float[9];//
        geomagnetic = new float[3];//用来保存地磁传感器的值
        values = new float[3];//用来保存最终的结果


        sensorManager.registerListener(this, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {//磁力传感器，单位是uT(微特斯拉)，测量设备周围三个物理轴（x，y，z）的磁场
            geomagnetic = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {//加速度传感器，单位是m/s2，测量应用于设备X、Y、Z轴上的加速度
            gravity = event.values;
            LogUtils.e("沿 x、y、z 轴的加速力（包括重力）:"+gravity[0]+" , "+gravity[1]+" , "+gravity[2]);
//            getOritation();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 获取手机旋转角度
     */
    public void getOritation() {
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic);
        //values从这里返回
        SensorManager.getOrientation(r, values);
        //提取数据
        double degreeZ = Math.toDegrees(values[0]);
        double degreeX = Math.toDegrees(values[1]);
        double degreeY = Math.toDegrees(values[2]);
        LogUtils.e("degreeX = "+degreeX+",degreeY = "+degreeY+",degreeZ = "+degreeZ);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }
}
