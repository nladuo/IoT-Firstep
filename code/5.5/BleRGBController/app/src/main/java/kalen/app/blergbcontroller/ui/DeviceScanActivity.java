package kalen.app.blergbcontroller.ui;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import kalen.app.blergbcontroller.adapter.LeDeviceListAdapter;
import kalen.app.blergbcontroller.model.C;
import kalen.app.blergbcontroller.R;

public class DeviceScanActivity extends AppCompatActivity implements OnItemClickListener{
    
    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;
    
    private LeDeviceListAdapter mAdapter;
    private ListView mLView;

    private static final int REQUEST_ENABLE_BT = 1;
    // 扫描持续10000ms
    private static final long SCAN_PERIOD = 10000;

    private static final int SCAN_TRUE_PERMISSION_REQ_CODE = 100;
    private static final int SCAN_FALSE_PERMISSION_REQ_CODE = 101;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mHandler = new Handler();
        
        mLView = (ListView) findViewById(R.id.scan_device_lv);

        // 判断硬件是否支持蓝牙4.0
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }

        // 获取蓝牙适配器
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // 判断设备是否支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

      //如果没开蓝牙,请求打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Initializes list view adapter.
        mAdapter = new LeDeviceListAdapter(this);
        mLView.setAdapter(mAdapter);
        mLView.setOnItemClickListener(this);
        scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 如果用户不同意打开蓝牙,就直接退出
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
        mAdapter.clear();
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
    	final BluetoothDevice device = mAdapter.getDevice(position);
        if (device == null) return;
        final Intent intent = new Intent(this, DeviceControlActivity.class);
        intent.putExtra(C.EXTRAS_DEVICE_NAME, device.getName());
        intent.putExtra(C.EXTRAS_DEVICE_ADDRESS, device.getAddress());
        if (mScanning) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning = false;
        }
        startActivity(intent);
	}

    /**
     * 扫描蓝牙设备（判断权限）
     */
    void scanLeDevice(boolean enable) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                if (enable) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            SCAN_TRUE_PERMISSION_REQ_CODE);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            SCAN_FALSE_PERMISSION_REQ_CODE);
                }

                if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(this, "开启位置服务才能使用扫描", Toast.LENGTH_SHORT).show();
                }
            } else {
                doScanLeDevice(enable);
            }
        } else {
            doScanLeDevice(enable);
        }
    }

    /**
     * 扫描蓝牙设备
     * @param enable 是否扫描
     */
    void doScanLeDevice(final boolean enable) {
        if (enable) { //开始扫描
            // 10s后停止扫描
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();//这句会调用onCreateOptionsMenu
                    getSupportActionBar().setTitle(R.string.app_name);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
            getSupportActionBar().setTitle(R.string.title_devices);
        } else { //停止扫描
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            getSupportActionBar().setTitle(R.string.app_name);
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case SCAN_TRUE_PERMISSION_REQ_CODE:
                if (grantResults.length != 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) { //有权限不通过就直接退出
                        finish();
                    }
                }

                doScanLeDevice(true);
                break;
            case SCAN_FALSE_PERMISSION_REQ_CODE:
                if (grantResults.length != 0) {
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED) { //有权限不通过就直接退出
                        finish();
                    }
                }

                doScanLeDevice(false);
                break;
        }

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                mAdapter.clear();
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }
        return true;
    }


    // 扫描到设备后,添加到adapter中
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter.addDevice(device);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    };
}