package kalen.app.blecom.ui;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.example.android.bluetoothlegatt.BluetoothLeService;

import kalen.app.blecom.model.C;
import kalen.app.blergbcontroller.R;

public class DeviceControlActivity extends Activity implements OnSeekBarChangeListener{
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    private TextView seekBarValueR;
    private TextView seekBarValueG;
    private TextView seekBarValueB;
    private SeekBar seekBarR;
    private SeekBar seekBarG;
    private SeekBar seekBarB;
    private String mDeviceAddress;
    private String mDeviceName;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothGattCharacteristic mCharacteristic = null; 
    private boolean mConnected = false;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    /**
     * 用于接收蓝牙状态广播的BroadcastReceiver
     * ACTION_GATT_CONNECTED: 蓝牙连接成功广播.
     * ACTION_GATT_DISCONNECTED: 蓝牙断开广播.
     * ACTION_GATT_SERVICES_DISCOVERED: 发现GATT的服务广播.
     * ACTION_DATA_AVAILABLE: 接收到蓝牙的数据广播
     * 
     */
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mConnected = true;
                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mConnected = false;
                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                getCharacteristic(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
            	System.out.println("receive data");
//                onReceiveData(intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        final Intent intent = getIntent();
        //获取设备名以及设备地址
        mDeviceName = intent.getStringExtra(C.EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(C.EXTRAS_DEVICE_ADDRESS);
        
        getActionBar().setTitle(mDeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //绑定BLE服务
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        
        seekBarValueR = (TextView) findViewById(R.id.seekbar_value_r);
        seekBarValueG = (TextView) findViewById(R.id.seekbar_value_g);
        seekBarValueB = (TextView) findViewById(R.id.seekbar_value_b);
        
        seekBarR =  (SeekBar) findViewById(R.id.seekbar_r);
        seekBarG =  (SeekBar) findViewById(R.id.seekbar_g);
        seekBarB =  (SeekBar) findViewById(R.id.seekbar_b);
        
        seekBarR.setOnSeekBarChangeListener(this);
        seekBarG.setOnSeekBarChangeListener(this);
        seekBarB.setOnSeekBarChangeListener(this);
        
        seekBarValueR.setText("R:" + seekBarR.getProgress());
		seekBarValueG.setText("G:" + seekBarG.getProgress());
		seekBarValueB.setText("B:" + seekBarB.getProgress());
    }
    
    
    /**
     * 给BLE发送RGB值
     * @param R
     * @param G
     * @param B
     */
    private void sendRGBToBle(int R, int G, int B){
    	//没连接,直接返回
		if (!mConnected) {
			Toast.makeText(DeviceControlActivity.this, "设备尚未连接", 
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		//没找到对应的特征值,直接返回
		if (mCharacteristic == null) {
			Toast.makeText(DeviceControlActivity.this, "没有找到对应的特征值,请尝试重新连接", 
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		byte[] datas = new byte[]{(byte) R, (byte) (G + 50), (byte) (B + 100)};
		mCharacteristic.setValue(datas);
		mBluetoothLeService.writeCharacteristic(mCharacteristic);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 更新连接状态
     * @param resourceId
     */
    private void updateConnectionState(final int resourceId) {
    	Toast.makeText(this, getResources().getString(resourceId), 
    			Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取BLE的特征值
     * @param gattServices
     */
    private void getCharacteristic(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            uuid = gattService.getUuid().toString();
            
            //找uuid为0xffe0的服务
            if (uuid.equals(C.SERVICE_UUID)) {
            	List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();
                //找uuid为0xffe1的特征值
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    uuid = gattCharacteristic.getUuid().toString();
                    if(uuid.equals(C.CHAR_UUID)){
                    	mCharacteristic = gattCharacteristic;
                    	final int charaProp = gattCharacteristic.getProperties();
                		//开启该特征值的数据的监听
                		if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mBluetoothLeService.setCharacteristicNotification(
                                    mCharacteristic, true);
                        }
                    	System.out.println("uuid----->" + uuid);
                    }
                }
			}
        }
        
        //如果没找到指定的特征值,直接返回
        if (mCharacteristic == null) {
        	Toast.makeText(DeviceControlActivity.this, "未找到指定特征值", 
        			Toast.LENGTH_LONG).show();
			finish();
		}

    }

    /**
     * 构建IntentFilter
     * @return
     */
    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        return intentFilter;
    }


	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		sendRGBToBle(seekBarR.getProgress(), seekBarG.getProgress(), seekBarB.getProgress());
		seekBarValueR.setText("R:" + seekBarR.getProgress());
		seekBarValueG.setText("G:" + seekBarG.getProgress());
		seekBarValueB.setText("B:" + seekBarB.getProgress());
	}


	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
}
