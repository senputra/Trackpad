package com.ulaladungdung.trackpadbeta;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nobody on 12/18/2015.
 */
public class FragmentTrackpad extends Fragment implements View.OnTouchListener {

    ConnectToServer mainConnection;

    public FragmentTrackpad(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_trackpad,container,false);
        view.setOnTouchListener(this);

        return view;

    }

    public void setConnection(ConnectToServer cts){
        mainConnection = cts;
        TextView tv = (TextView)getActivity().findViewById(R.id.tv_trackpad);
        tv.setText(cts.serverUDPPort+"");
    }


    final String TAG = "dakdoi";

    //empty byte array
    byte[] emptyData = {};

    //variables for the tap timeout
    int singleTapTimeout = 110;
    int longTapTimeout = 1000;
    int doubleTapTimeout = 50;
    int secondTapTimeout = 150;

    final Runnable mSingleTap = new Runnable() {
        public void run() {
            mainConnection.sendData(mainConnection.udpPacketAdaptor(2,emptyData));
            tapCount = 0;
            lastTapTime = System.currentTimeMillis();
            Log.d(TAG, "Single Tap");
        }
    };
    private final Runnable mLongPressed = new Runnable() {
        public void run() {
            //mainConnection.sendData(mainConnection.udpPacketAdaptor(3,emptyData));
            Log.d(TAG, "Long press!");
        }
    };

    private final Runnable mTwoFingerTap = new Runnable() {
        @Override
        public void run() {
            mainConnection.sendData(mainConnection.udpPacketAdaptor(3,emptyData));
            Log.d(TAG, "two finger press!");
            isTwoFingerTap = true;
        }
    };

    private final Runnable mDoubleTapHold = new Runnable() {
        @Override
        public void run() {
            mainConnection.sendData(mainConnection.udpPacketAdaptor(4,emptyData));
            isDoubleTapHold = true;
            Log.d(TAG, "Double Tap hold!");
        }
    };

    private final Runnable mDoubleTapOff = new Runnable() {
        @Override
        public void run() {
            mainConnection.sendData(mainConnection.udpPacketAdaptor(5,emptyData));
            isDoubleTapHold = false;
            Log.d(TAG, "Double Tap off!");
        }
    };
    //variable to save extra data
    long lastFingerDownTime;
    long lastTapTime;
    boolean mustTwoFingerTap;
    int firstXAverage, firstYAverage ;
    int fingerDownX, fingerDownY;
    boolean longPressActive;
    boolean needSingleTap = true;
    boolean isDoubleTapHold = false;
    boolean isScroll = false;
    int tapCount = 0;
    boolean isTwoFingerTap = false;

    //threshold
    int clickDistanceThreshold = 20; //in pixel

    //runnables for gesture
    private final Handler handler = new Handler();



    @Override
    public boolean onTouch(View v, MotionEvent e){
        int action = e.getActionMasked();
        //int id = e.getPointerId(e.getActionIndex());
        //TCP Connection type
//        /*try {
//            WriteToServer dataStream = new WriteToServer(mainConnection.getOutputStream());
//            switch (action) {
//                case MotionEvent.ACTION_DOWN:
//                    dataStream.sendCoordinate((int) e.getX(), (int) e.getY());
//                    Log.d(TAG,"finger down");
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    long lastTime = System.currentTimeMillis();
//                    dataStream.sendCoordinate((int) e.getX(), (int) e.getY());
//                    Log.d(TAG,"gaptime : "+ (System.currentTimeMillis() - lastTime));
//                    break;
//                case MotionEvent.ACTION_POINTER_DOWN:
//                    break;
//                case MotionEvent.ACTION_UP:
//                    dataStream.sendCoordinate(-1, -1);
//                    break;
//
//                case MotionEvent.ACTION_POINTER_UP:
//                    break;
//
//                case MotionEvent.ACTION_OUTSIDE: break;
//                case MotionEvent.ACTION_CANCEL: break;
//            }
//        }catch (Exception er){
//            er.printStackTrace();
//        }*/
        try {
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    //Log.d(TAG," tapcount: "+tapCount);
                    lastFingerDownTime = System.currentTimeMillis();
                    fingerDownX = (int)e.getX();
                    fingerDownY = (int)e.getY();
                    longPressActive = true;
                    handler.removeCallbacks(mSingleTap);

                    if(tapCount == 1){
                        if(System.currentTimeMillis() - lastTapTime <= secondTapTimeout){
                            handler.removeCallbacks(mSingleTap);
                            handler.removeCallbacks(mLongPressed);
                            mDoubleTapHold.run();
                            needSingleTap = false;
                            mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte(fingerDownX, fingerDownY)));
                            break;
                        }
                    }

                    tapCount = 0;
                    needSingleTap = true;
                    handler.postDelayed(mLongPressed, longTapTimeout);

                    mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte(fingerDownX, fingerDownY)));
                    //Log.d(TAG,"finger down");
                    break;

                case MotionEvent.ACTION_MOVE:
                    if(e.getPointerCount()==1) {
                        if (longPressActive) {
                            if (fingerDownX - e.getX() > clickDistanceThreshold || fingerDownX - e.getX() > clickDistanceThreshold) {
                                handler.removeCallbacks(mLongPressed);
                                lastFingerDownTime = 0;
                                longPressActive = false;
                            }
                        }
                        //long lastTime = System.currentTimeMillis();
                        mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte((int) e.getX(), (int) e.getY())));
                        //Log.d(TAG, "gaptime : " + (System.currentTimeMillis() - lastTime));
                        //Log.d(TAG,"X coor:"+e.getX()+" Y Coor:"+e.getY());
                    }else if(e.getPointerCount()==2){
                        int xAvearage = (int)(e.getX(0) + e.getX(1))/2;
                        int yAvearage = (int)(e.getY(0) + e.getY(1))/2;
                        if ((!isScroll) && (firstXAverage - xAvearage > clickDistanceThreshold || firstYAverage - yAvearage > clickDistanceThreshold
                        || firstXAverage - xAvearage < -clickDistanceThreshold || firstYAverage - yAvearage < -clickDistanceThreshold)) {
                            mustTwoFingerTap = false;
                            isScroll = true;
                            Log.d(TAG,"scrolling");
                        }
                        mainConnection.sendData(mainConnection.udpPacketAdaptor(6, mainConnection.coordinateToByte(xAvearage, yAvearage)));
                        //Log.d(TAG,e.getX(0)+"X"+e.getX(1) + " = "+((e.getX(0) + e.getX(1)) / 2));
                        //Log.d(TAG,e.getY(0)+"Y"+e.getY(1)+ " = "+((e.getY(0) + e.getY(1)) / 2));
                    }

                    break;

                case MotionEvent.ACTION_POINTER_DOWN:
                    handler.removeCallbacks(mLongPressed);
                    firstXAverage = (int)(e.getX(0) + e.getX(1))/2;
                    firstYAverage = (int) (e.getY(0) + e.getY(1))/2;
                    mustTwoFingerTap = true;
                    isScroll = false;
                    mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte(-1, -1)));
                    break;

                case MotionEvent.ACTION_UP:
                    if(isDoubleTapHold &&(System.currentTimeMillis() - lastFingerDownTime <= doubleTapTimeout)){
                        mDoubleTapOff.run();
                        mSingleTap.run();

                    }else if(isDoubleTapHold ){
                        mDoubleTapOff.run();
                    }else if(isTwoFingerTap){
                        handler.removeCallbacks(mSingleTap);
                        isTwoFingerTap = false;
                    }else if(needSingleTap) {
                        if (System.currentTimeMillis() - lastFingerDownTime <= singleTapTimeout) {
                            tapCount=1;
                            lastTapTime = System.currentTimeMillis();
                            handler.postDelayed(mSingleTap,180);
                        }
                    }else if(mustTwoFingerTap){
                        mTwoFingerTap.run();
                    }

                    lastFingerDownTime = 0;
                    handler.removeCallbacks(mLongPressed);
                    mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte(-1, -1)));
                    break;


                case MotionEvent.ACTION_POINTER_UP:

                    break;

                case MotionEvent.ACTION_OUTSIDE: break;
                case MotionEvent.ACTION_CANCEL: break;
            }
        }catch (Exception er){
            er.printStackTrace();
        }

        return true;
    }

}

