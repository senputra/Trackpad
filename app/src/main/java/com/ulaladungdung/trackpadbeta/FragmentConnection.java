package com.ulaladungdung.trackpadbeta;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Nobody on 12/18/2015.
 */
public class FragmentConnection extends Fragment {
    final String TAG = "dakdoi";
    EditText[] ETPartialIP = new EditText[5];
    Button btnConnect;
    String serverIP;
    int serverPort;
    View view;
    Connection mConnection;

    ConnectToServer mainConnection;

    interface Connection {
        void getConnection(ConnectToServer cts);
    }

    public FragmentConnection(){
    }

    public void setup(){
        setupConnectButton();;
        setupETPartialIP();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        Activity activity = (Activity) context;

        try{
             mConnection = (Connection) activity;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_connection,container,false);
        setup();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d("dodakdoi","connection");
                return true;
            }
        });

        return view;
    }

    //method to setting up all the EditText regarding the IP adrress and port
    private void setupETPartialIP(){

        int checking = 1; //5 means all data are correct

        ETPartialIP[0] = (EditText)view.findViewById(R.id.et_ipaddress1);
        ETPartialIP[1] = (EditText)view.findViewById(R.id.et_ipaddress2);
        ETPartialIP[2] = (EditText)view.findViewById(R.id.et_ipaddress3);
        ETPartialIP[3] = (EditText)view.findViewById(R.id.et_ipaddress4);
        //named ETPArtialIP for convenience in programing
        //the lats one is the port value
        ETPartialIP[4] = (EditText)view.findViewById(R.id.et_port);

        ETPartialIP[0].addTextChangedListener(new TextWatcher() {
            int i = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String partialIPStr = ETPartialIP[i].getText().toString();
                //if the space is blank checking is unnecessary
                if (!(partialIPStr.equalsIgnoreCase(""))) {
                    int partialIP = Integer.parseInt(partialIPStr);

                    //check if the input is a correct IP address
                    // 0 - 255 inclusive
                    //pop a toast out to remind the user
                    if (partialIP < 0 || partialIP > 255) {
                        Toast.makeText(getActivity().getApplicationContext(), "IP address must be between 0-255", Toast.LENGTH_SHORT).show();
                        ETPartialIP[i].setText(partialIPStr.substring(0, 2));
                        ETPartialIP[i].setSelection(2);
                        return;
                    }

                    //if everything is alright jump to other EditText
                    if(partialIPStr.length()==3){
                        ETPartialIP[i+1].requestFocus();
                        return;
                    }

                }

            }
        });
        ETPartialIP[1].addTextChangedListener(new TextWatcher() {
            int i = 1;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String partialIPStr = ETPartialIP[i].getText().toString();
                //if the space is blank checking is unnecessary
                if (!(partialIPStr.equalsIgnoreCase(""))) {

                    int partialIP = Integer.parseInt(partialIPStr);

                    //check if the input is a correct IP address
                    // 0 - 255 inclusive
                    //pop a toast out to remind the user
                    if (partialIP < 0 || partialIP > 255) {
                        Toast.makeText(getActivity().getApplicationContext(), "IP address must be between 0-255", Toast.LENGTH_SHORT).show();
                        ETPartialIP[i].setText(partialIPStr.substring(0, 2));
                        ETPartialIP[i].setSelection(2);
                        return;
                    }

                    //if everything is alright jump to other EditText
                    if(partialIPStr.length()==3){
                        ETPartialIP[i+1].requestFocus();
                        return;
                    }

                }

            }
        });
        ETPartialIP[2].addTextChangedListener(new TextWatcher() {
            int i = 2;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String partialIPStr = ETPartialIP[i].getText().toString();
                //if the space is blank checking is unnecessary
                if (!(partialIPStr.equalsIgnoreCase(""))) {

                    int partialIP = Integer.parseInt(partialIPStr);

                    //check if the input is a correct IP address
                    // 0 - 255 inclusive
                    //pop a toast out to remind the user
                    if (partialIP < 0 || partialIP > 255) {
                        Toast.makeText(getActivity().getApplicationContext(), "IP address must be between 0-255", Toast.LENGTH_SHORT).show();
                        ETPartialIP[i].setText(partialIPStr.substring(0, 2));
                        ETPartialIP[i].setSelection(2);
                        return;
                    }

                    //if everything is alright jump to other EditText
                    if(partialIPStr.length()==3){
                        ETPartialIP[i+1].requestFocus();
                        return;
                    }

                }

            }
        });
        ETPartialIP[3].addTextChangedListener(new TextWatcher() {
            int i = 3;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String partialIPStr = ETPartialIP[i].getText().toString();
                //if the space is blank checking is unnecessary
                if (!(partialIPStr.equalsIgnoreCase(""))) {

                    int partialIP = Integer.parseInt(partialIPStr);

                    //check if the input is a correct IP address
                    // 0 - 255 inclusive
                    //pop a toast out to remind the user
                    if (partialIP < 0 || partialIP > 255) {
                        Toast.makeText(getActivity().getApplicationContext(), "IP address must be between 0-255", Toast.LENGTH_SHORT).show();
                        ETPartialIP[i].setText(partialIPStr.substring(0, 2));
                        ETPartialIP[i].setSelection(2);
                        return;
                    }

                    //if everything is alright jump to other EditText
                    if(partialIPStr.length()==3){
                        ETPartialIP[i+1].requestFocus();
                        return;
                    }

                }

            }
        });
        //add listener to the port input
        ETPartialIP[4].addTextChangedListener(new TextWatcher() {
            int i = 4;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String partialIPStr = ETPartialIP[i].getText().toString();
                //if the space is blank checking is unnecessary
                if (!(partialIPStr.equalsIgnoreCase(""))) {

                    int partialIP = Integer.parseInt(partialIPStr);

                    //check if the input is a correct PORT
                    // which is 0 - 65535 or the max value of unsigned short or char
                    //pop a toast out to remind the user
                    if (partialIP < 0 || partialIP > Character.MAX_VALUE) {
                        Toast.makeText(getActivity().getApplicationContext(), "Port must be between 0-65535", Toast.LENGTH_SHORT).show();
                        ETPartialIP[i].setText(partialIPStr.substring(0, 4));
                        ETPartialIP[i].setSelection(4);
                    }

                }

            }
        });


        //add listener to the action key
        // moving the focus to other edit text
        ETPartialIP[0].setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                ETPartialIP[1].requestFocus();
                return true;
            }
        });
        ETPartialIP[1].setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                ETPartialIP[2].requestFocus();
                return true;
            }
        });
        ETPartialIP[2].setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                ETPartialIP[3].requestFocus();
                return true;
            }
        });
        ETPartialIP[3].setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                ETPartialIP[4].requestFocus();
                return true;
            }
        });
        //the action key acts the same as clicking Button
        ETPartialIP[4].setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                btnConnect.performClick();
                return true;
            }
        });
    }


    private void setupConnectButton(){
        btnConnect = (Button)view.findViewById(R.id.btn_connect);

        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEveryPartialIP()){
                    serverIP = ETPartialIP[0].getText().toString()+"."+
                            ETPartialIP[1].getText().toString()+"."+
                            ETPartialIP[2].getText().toString()+"."+
                            ETPartialIP[3].getText().toString();
                    serverPort = Integer.parseInt(ETPartialIP[4].getText().toString());

                    Log.d(TAG, serverIP + " " + serverPort);

                    //calling other class to handle the connection so it
                    //wont freeze the UI and trigger app not responding alert
                    mainConnection = new ConnectToServer(serverIP,serverPort);
                    sendConnection();
                    //keepWake = new ConnectionStabilizer(serverIP,serverPort,mainConnection.getLockObject(),mainConnection.getSocket());

//                    Runnable run = new Runnable() {
//                        @Override
//                        public void run() {
//                            for(int i =0; i<30; i++){
//                                mainConnection.isConnected();
//                                if (mainConnection.isConnected) {
//                                    keepWake.start();
//                                    Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
//                                }
//                                if (i == 29){
//                                    Toast.makeText(getApplicationContext(),"Failed to find Server",Toast.LENGTH_SHORT).show();
//                                }
//                                try {
//                                }catch (Exception e){
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    };

//                    Handler handle = new Handler();
//                    handle.post(run);

                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Missing input(s)!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //check if all blanks are filled in correctly
    private boolean checkEveryPartialIP(){
        /*Log.d(TAG, "okehhh"+(!(ETPartialIP[0].getText().toString().equalsIgnoreCase("")
                ||ETPartialIP[1].getText().toString().equalsIgnoreCase("")
                ||ETPartialIP[2].getText().toString().equalsIgnoreCase("")
                ||ETPartialIP[3].getText().toString().equalsIgnoreCase("")
                ||ETPartialIP[4].getText().toString().equalsIgnoreCase(""))));*/
        return !(ETPartialIP[0].getText().toString().equalsIgnoreCase("")
                ||ETPartialIP[1].getText().toString().equalsIgnoreCase("")
                ||ETPartialIP[2].getText().toString().equalsIgnoreCase("")
                ||ETPartialIP[3].getText().toString().equalsIgnoreCase("")
                ||ETPartialIP[4].getText().toString().equalsIgnoreCase(""));
    }

    public void sendConnection(){
        mConnection.getConnection(mainConnection);
    }
}
