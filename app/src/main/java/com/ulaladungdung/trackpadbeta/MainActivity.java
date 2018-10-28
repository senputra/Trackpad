package com.ulaladungdung.trackpadbeta;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements FragmentConnection.Connection {

    final String TAG = "dakdoi";
   DrawerLayout drawer;
    ConnectToServer mainConnection;
    ConnectionStabilizer keepWake;
    CustomViewPager mViewPager;
    TrackpadFragmentAdapter trackpadFragmentAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mViewPager = (CustomViewPager) findViewById(R.id.pager);
        trackpadFragmentAdapter = new TrackpadFragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(trackpadFragmentAdapter);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.pager,new FragmentConnection(),"android:switcher:" + R.id.pager + ":"+"0");
        fragmentTransaction.add(R.id.pager, new FragmentTrackpad(), "android:switcher:" + R.id.pager + ":" + "1");
        fragmentTransaction.commit();

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.tutup);
        drawer.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        final TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Connection"));
        tabLayout.addTab(tabLayout.newTab().setText("Trackpad"));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {
                    mViewPager.setSwipingEnabled(false);
                } else {
                    mViewPager.setSwipingEnabled(true);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



//    //empty byte array
//    byte[] emptyData = {};
//
//    //variables for the tap timeout
//    int singleTapTimeout = 110;
//    int longTapTimeout = 1000;
//    int secondTapTimeout = 150;
//
//    private final Runnable mSingleTap = new Runnable() {
//        public void run() {
//            mainConnection.sendData(mainConnection.udpPacketAdaptor(2,emptyData));
//            tapCount = 1;
//            lastTapTime = System.currentTimeMillis();
//            Log.d(TAG, "Single Tap");
//        }
//    };
//
//    private final Runnable mLongPressed = new Runnable() {
//        public void run() {
//            //mainConnection.sendData(mainConnection.udpPacketAdaptor(3,emptyData));
//            Log.d(TAG, "Long press!");
//        }
//    };
//    private final Runnable mTwoFingerTap = new Runnable() {
//        @Override
//        public void run() {
//            mainConnection.sendData(mainConnection.udpPacketAdaptor(3,emptyData));
//            Log.d(TAG, "two finger press!");
//            isTwoFingerTap = true;
//        }
//    };
//
//    private final Runnable mDoubleTapHold = new Runnable() {
//        @Override
//        public void run() {
//            mainConnection.sendData(mainConnection.udpPacketAdaptor(4,emptyData));
//            isDoubleTapHold = true;
//            Log.d(TAG, "Double Tap hold!");
//        }
//    };
//
//    private final Runnable mDoubleTapOff = new Runnable() {
//        @Override
//        public void run() {
//            mainConnection.sendData(mainConnection.udpPacketAdaptor(5,emptyData));
//            isDoubleTapHold = false;
//            Log.d(TAG, "Double Tap off!");
//        }
//    };
//
//    //variable to save extra data
//    long lastFingerDownTime;
//    long lastTapTime;
//    int fingerDownX, fingerDownY;
//    boolean longPressActive;
//    boolean needSingleTap = true;
//    boolean isDoubleTapHold = false;
//    int tapCount = 0;
//    boolean isTwoFingerTap = false;
//    //threshold
//    int clickDistanceThreshold = 20; //in pixel
//
//    //runnables for gesture
//    private final Handler handler = new Handler();
//
//    @Override
//    public boolean onTouchEvent(MotionEvent e){
//        //int CurrMaxTouch = e.getPointerCount();
//        int action = e.getActionMasked();
//        //int id = e.getPointerId(e.getActionIndex());
//        //TCP Connection type
////        /*try {
////            WriteToServer dataStream = new WriteToServer(mainConnection.getOutputStream());
////            switch (action) {
////                case MotionEvent.ACTION_DOWN:
////                    dataStream.sendCoordinate((int) e.getX(), (int) e.getY());
////                    Log.d(TAG,"finger down");
////                    break;
////                case MotionEvent.ACTION_MOVE:
////                    long lastTime = System.currentTimeMillis();
////                    dataStream.sendCoordinate((int) e.getX(), (int) e.getY());
////                    Log.d(TAG,"gaptime : "+ (System.currentTimeMillis() - lastTime));
////                    break;
////                case MotionEvent.ACTION_POINTER_DOWN:
////                    break;
////                case MotionEvent.ACTION_UP:
////                    dataStream.sendCoordinate(-1, -1);
////                    break;
////
////                case MotionEvent.ACTION_POINTER_UP:
////                    break;
////
////                case MotionEvent.ACTION_OUTSIDE: break;
////                case MotionEvent.ACTION_CANCEL: break;
////            }
////        }catch (Exception er){
////            er.printStackTrace();
////        }*/
//        try {
//            switch (action) {
//                case MotionEvent.ACTION_DOWN:
//                    //Log.d(TAG," tapcount: "+tapCount);
//                    lastFingerDownTime = System.currentTimeMillis();
//                    fingerDownX = (int)e.getX();
//                    fingerDownY = (int)e.getY();
//                    longPressActive = true;
//                    handler.removeCallbacks(mSingleTap);
//
//                    if(tapCount == 1){
//                        if(System.currentTimeMillis() - lastTapTime <= secondTapTimeout){
//                            handler.removeCallbacks(mLongPressed);
//                            mDoubleTapHold.run();
//                            needSingleTap = false;
//                            mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte(fingerDownX, fingerDownY)));
//                            break;
//                        }
//                    }
//
//                    tapCount = 0;
//                    needSingleTap = true;
//                    handler.postDelayed(mLongPressed, longTapTimeout);
//
//                    mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte(fingerDownX, fingerDownY)));
//                    //Log.d(TAG,"finger down");
//                    break;
//
//                case MotionEvent.ACTION_MOVE:
//                    if(e.getPointerCount()==1) {
//                        if (longPressActive) {
//                            if (fingerDownX - e.getX() > clickDistanceThreshold || fingerDownX - e.getX() > clickDistanceThreshold) {
//                                handler.removeCallbacks(mLongPressed);
//                                lastFingerDownTime = 0;
//                                longPressActive = false;
//                            }
//                        }
//                        //long lastTime = System.currentTimeMillis();
//                        mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte((int) e.getX(), (int) e.getY())));
//                        //Log.d(TAG, "gaptime : " + (System.currentTimeMillis() - lastTime));
//                        Log.d(TAG,"X coor:"+e.getX()+" Y Coor:"+e.getY());
//                    }else if(e.getPointerCount()==2){
//                        mainConnection.sendData(mainConnection.udpPacketAdaptor(6, mainConnection.coordinateToByte((int) (e.getX(0)+e.getX(1)/2), (int) (e.getY(0)+e.getY(1)/2))));
//                        //Log.d(TAG,"asdsad double slide");
//                    }
//                    break;
//
//                case MotionEvent.ACTION_POINTER_DOWN:
//                    handler.removeCallbacks(mLongPressed);
//                    break;
//
//                case MotionEvent.ACTION_UP:
//                    if(isDoubleTapHold){
//                        mDoubleTapOff.run();
//                    }else if(isTwoFingerTap){
//                        handler.removeCallbacks(mSingleTap);
//                        isTwoFingerTap = false;
//                    }else if(needSingleTap) {
//                        if (System.currentTimeMillis() - lastFingerDownTime <= singleTapTimeout) {
//                            tapCount+=1;
//                            lastTapTime = System.currentTimeMillis();
//                            handler.postDelayed(mSingleTap,100);
//                        }
//                    }
//
//                    lastFingerDownTime = 0;
//                    handler.removeCallbacks(mLongPressed);
//                    mainConnection.sendData(mainConnection.udpPacketAdaptor(1, mainConnection.coordinateToByte(-1, -1)));
//                    break;
//
//
//                case MotionEvent.ACTION_POINTER_UP:
//                    mTwoFingerTap.run();
//                    break;
//
//                case MotionEvent.ACTION_OUTSIDE: break;
//                case MotionEvent.ACTION_CANCEL: break;
//            }
//        }catch (Exception er){
//            er.printStackTrace();
//        }
//
//        return super.onTouchEvent(e);
//    }




    @Override
    public void getConnection(ConnectToServer cts) {
        this.mainConnection = cts;
        FragmentTrackpad ft = (FragmentTrackpad)getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":" + "1");
        ft.setConnection(cts);
        return;
    }
}
