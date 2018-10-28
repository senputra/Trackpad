package com.ulaladungdung.trackpadbeta;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Nobody on 12/12/2015.
 */
public class ConnectionStabilizer extends Thread{

    boolean keepActive = true;
    int sleepInterval = 200;

    int serverPort;
    InetAddress serverAddress;

    Object lockObject;
    byte[] data = {};

    DatagramSocket socket;
    DatagramPacket staticPacket;

    int idleTimeout = 700;
    boolean isIdle = true;

    int busyInterval = 500;
    int idleInterval = 200;

    public ConnectionStabilizer(String serverAddress, int serverPort, Object lockObject){
        try {
            this.serverAddress = InetAddress.getByName(serverAddress);
            this.serverPort = serverPort;
            this.lockObject = lockObject;
            socket = new DatagramSocket();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ConnectionStabilizer(String serverAddress, int serverPort, Object lockObject ,DatagramSocket socket){
        try {
            this.socket = socket;
            this.serverAddress = InetAddress.getByName(serverAddress);
            this.serverPort = serverPort;
            this.lockObject = lockObject;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        yield();
        while(keepActive) {
            try {
                staticPacket = new DatagramPacket(data, 0, serverAddress, serverPort);
                socket.send(staticPacket);
            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                sleep(sleepInterval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void disable(){
        keepActive = false;
    }

    public void enable(){
        keepActive = true;
    }

    public boolean isActive(){
        return keepActive;
    }

    public void setSleepInterval(int interval){
        this.sleepInterval = interval;
    }

}
