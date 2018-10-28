package com.ulaladungdung.trackpadbeta;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Nobody on 12/5/2015.
 */
//individual thread to handle writing data to server

/*public class WriteToServer extends Thread {
    String TAG ="dakdoi";
    OutputStream os;
    private final int DEFAULT_ALLOCATION_FOR_SHORT = 2;
    public static final short X_AXIS = 0;
    public static final short Y_AXIS = 1;
    private static int lastXCoordinate = -1;
    private static int lastYCoordinate = -1;

    private byte[] X_AXISbyte = ByteBuffer.allocate(2).putShort(X_AXIS).array();
    private byte[] Y_AXISbyte = ByteBuffer.allocate(2).putShort(Y_AXIS).array();

    public  WriteToServer (OutputStream os){
        this.os = os;
    }

    public void sendCoordinate(int XCoordinate, int YCoordinate){
        try {

            os.write(shortToByteArray((short)XCoordinate,2));
            os.write(shortToByteArray((short)YCoordinate,2));
            Log.d(TAG, "sending axis " + XCoordinate +" "+ YCoordinate);


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void sendDisplacement(int XCoordinate,int YCoordinate){
        if(lastXCoordinate==-1 && lastYCoordinate == -1){
            //Log.d(TAG,"last coordinate :"+lastXCoordinate+" "+lastYCoordinate);
            //Log.d(TAG,"last coordinate :"+XCoordinate+" "+YCoordinate);
            this.lastXCoordinate = XCoordinate;
            this.lastYCoordinate = YCoordinate;
            Log.d(TAG,"last coordinate  2:"+lastXCoordinate+" "+lastYCoordinate);
        }else if(XCoordinate==-1 && YCoordinate==-1) {
            Log.d(TAG,"coordinate :"+XCoordinate+" "+YCoordinate);
            lastXCoordinate = -1;
            lastYCoordinate = -1;
        }else{
            try {
                Log.d(TAG, "disp :" + ((lastXCoordinate-XCoordinate)/2) + " " + ((lastYCoordinate-YCoordinate)/2));
                os.write(shortToByteArray((short)((lastXCoordinate-XCoordinate)/2),2));
                os.write(shortToByteArray((short) ((lastYCoordinate-YCoordinate)/2),2));

                lastXCoordinate = XCoordinate;
                lastYCoordinate = YCoordinate;

            }catch (Exception e){
                e.printStackTrace();
            }
        }

       // Log.d(TAG,"last coordinate 3 :"+lastXCoordinate+" "+lastYCoordinate);

    }

    public byte[] shortToByteArray(short value, int allocation){
        return ByteBuffer.allocate(allocation).putShort(value).array();
    }
}*/




