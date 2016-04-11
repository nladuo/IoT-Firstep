package kalen.app.blecomsample.ui;

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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import com.example.android.bluetoothlegatt.BluetoothLeService;

import kalen.app.blecomsample.R;
import kalen.app.blecomsample.model.C;
/**
 * 
 * @author kalen
 *
 */
public class DeviceControlActivity extends Activity {
    private final static String TAG = DeviceControlActivity.class.getSimpleName();

    private TextView mRecieveDataTView;
    private EditText mSendDataEditText;
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
     * ��??����������?????����??����?????��
     * ACTION_GATT_CONNECTED: connected to a GATT server.
     * ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
     * ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
     * ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
     *                         or notification operations.
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
            	
                onReceiveData(intent.getByteArrayExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        final Intent intent = getIntent();
        //��峰���?����???������������????���?mac��??���?
        mDeviceName = intent.getStringExtra(C.EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(C.EXTRAS_DEVICE_ADDRESS);
        //���濮����������
        mRecieveDataTView = (TextView) findViewById(R.id.control_receive_data_tv);
        mSendDataEditText = (EditText) findViewById(R.id.control_send_data_et);
        
        getActionBar().setTitle(mDeviceName);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        //��??��诲���������������??����edittext������??��???��??��?????��������??��
       findViewById(R.id.control_send_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��??�������������?����
				if (mCharacteristic == null) {
					Toast.makeText(DeviceControlActivity.this, "uninitialized characteristics", 
							Toast.LENGTH_SHORT).show();
					return;
				}
				//������������??�����?
				if (!mConnected) {
					Toast.makeText(DeviceControlActivity.this, "浣�??���????��??����??��???�����?", 
							Toast.LENGTH_SHORT).show();
					return;
				}
				
				String dataStr = mSendDataEditText.getText().toString();
				sendStrDataToLeDevice(dataStr);
			}
		});
    }
    
    private void sendStrDataToLeDevice(String data){
    	
		//�?�濮����������??��
		
		if (!data.equals("")) {
			byte[] datas = data.getBytes();
			mCharacteristic.setValue(datas);
			mBluetoothLeService.writeCharacteristic(mCharacteristic);
		}
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
     * ��??��????����??��??���?
     * @param resourceId
     */
    private void updateConnectionState(final int resourceId) {
    	Toast.makeText(this, getResources().getString(resourceId), 
    			Toast.LENGTH_SHORT).show();
    }

    /**
     * ��������??������??��??����??��������浣�
     * @param data
     */
    private void onReceiveData(byte[] bytes) {
    	String data = new String(bytes);
        if (data != null) {
        	try {
				data = data.substring(0, data.lastIndexOf("\n")); 
			} catch (Exception e) { }
        	StringBuffer sb = new StringBuffer(mRecieveDataTView.getText().toString());
        	mRecieveDataTView.setText(sb.append(data).toString());
        }
    }

    /**
     * ��峰�����??�����?��?������??��?���?��??��?��??����???��褰�����?????��??��??��浣跨��?����??�?
     * 						������4.0������??��������?������??��?��??������??��������??讹��?
     * @param gattServices
     */
    private void getCharacteristic(List<BluetoothGattService> gattServices) {
        if (gattServices == null) return;
        String uuid = null;

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            uuid = gattService.getUuid().toString();
            
            //��??��???������??����?����??����service uuid
            if (uuid.equals(C.SERVICE_UUID)) {
            	List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();
                //��???��service������?����?�����?characteristics
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    uuid = gattCharacteristic.getUuid().toString();
                    if(uuid.equals(C.CHAR_UUID)){
                    	mCharacteristic = gattCharacteristic;
                    	final int charaProp = gattCharacteristic.getProperties();
                		//?????��??��??���������?
                		if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            mBluetoothLeService.setCharacteristicNotification(
                                    mCharacteristic, true);
                        }
                    	System.out.println("uuid----->" + uuid);
                    }
                }
			}
        }
        
        //濡�����?????����峰�����characteristics???������???�����?activity???��??��??������???�����?
        if (mCharacteristic == null) {
        	Toast.makeText(DeviceControlActivity.this, "�????����??��???��??��?��???峰���??������???�����?", 
        			Toast.LENGTH_LONG).show();
			finish();
		}

    }

    /**
     * ��峰�����������?�������????��������?���?IntentFilter
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
}
