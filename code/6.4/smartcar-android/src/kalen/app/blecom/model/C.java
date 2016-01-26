package kalen.app.blecom.model;

/**
 * 定义常量
 */
public class C {

	//我们需要的service_uuid 是 0xffe0，其中characteristics_uuid是0xffe1
    public static String SERVICE_UUID 	= "0000ffe0-0000-1000-8000-00805f9b34fb";
    public static String CHAR_UUID 		= "0000ffe1-0000-1000-8000-00805f9b34fb";

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    
    public static final int CAR_STOP 		= 0;
    public static final int CAR_UP 			= 1;
    public static final int CAR_DOWN 	= 2;
    public static final int CAR_LEFT 		= 3;
    public static final int CAR_RIGHT 	= 4;
}
