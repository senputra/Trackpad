package com.ulaladungdung.trackpadbeta;

import android.content.Context;
import android.support.v4.content.res.TypedArrayUtils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import android.os.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Nobody on 12/5/2015.
 */
/*

public class ConnectToServer extends Thread{
    final String TAG = "dakdoi";
    private int serverPort;
    private String serverIP;
    private Socket socket;

    public ConnectToServer(String serverIP,int serverPort){
        this.serverPort = serverPort;
        this.serverIP = serverIP;
    }

    @Override
    public void run(){
        try {
            //connecting to the server
            socket = new Socket(serverIP,serverPort);
            Log.d(TAG,"Connected");
            //declaring an object to send data to server
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public OutputStream getOutputStream() throws Exception{
        return socket.getOutputStream();
    }

}*/

//UDP connection
public class ConnectToServer extends Thread {
    int packetSize = 8;
    DatagramPacket packet = null;
    DatagramSocket socket;
    byte[] receivedData = new byte[packetSize];
    InetAddress serverIP;
    int serverUDPPort;
    boolean isConnected = false;

    public ConnectToServer(String serverIP, int serverUDPPort) {
        try {
            this.serverIP = InetAddress.getByName(serverIP);
            this.serverUDPPort = serverUDPPort;
            socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData(byte[] data) {
        try {
            packet = new DatagramPacket(data, data.length, serverIP, serverUDPPort);
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] udpPacketAdaptor(int action, byte[] value) {
        byte[] one = ByteBuffer.allocate(4).putInt(action).array();
        byte[] data = new byte[one.length + value.length];
        for (int i = 0; i < data.length; ++i) {
            data[i] = i < one.length ? one[i] : value[i - one.length];
        }
        return data;
    }

    public byte[] coordinateToByte(int x, int y) {
        return ByteBuffer.allocate(4).putInt((x * 65536) + y).array();
    }

    public Object getLockObject() {
        return this;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public boolean isConnected() {


        byte[] emptyData = {0x00};
        byte[] ping = udpPacketAdaptor(88, emptyData);
        byte[] pingResponse;
        try {
            packet = new DatagramPacket(ping, ping.length, serverIP, serverUDPPort);
            socket.send(packet);
            socket.receive(packet);
            pingResponse = packet.getData();
            if (ByteBuffer.wrap(pingResponse).getInt() == 8888) {
                isConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }
}
