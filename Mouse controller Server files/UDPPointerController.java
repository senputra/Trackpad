
import java.awt.Robot.*;
import java.io.*;
import java.lang.Exception;
import java.lang.System;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Robot.*;
import java.awt.*;
import java.nio.*;
import java.awt.event.InputEvent;

public class UDPPointerController extends Thread{



    public int lastCoor[] = {-2,-1};
    public int currentCoor[] = {MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y};
    public int newCoor[] = new int[2];
    Object Lock = new Object();

    byte[] data;

    public UDPPointerController(){
        //System.out.println("okeh");
    }

    @Override
    public void run(){
        setPriority(Thread.MAX_PRIORITY);
        synchronized(this){
            try{
                while(true){

                    //System.out.println("wait");
                    wait();
                    //System.out.println("continue");
                    //System.out.println("lastcoor "+lastCoor[0]+" "+lastCoor[1]);
                    //System.out.println("newCoor "+newCoor[0]+" "+newCoor[1]);
                    if(newCoor[0]!=-2 || newCoor[1]!=-1){
                        movePointer(lastCoor[0]-newCoor[0],lastCoor[1]-newCoor[1]);
                    }else{
                        continue;
                    }
                    lastCoor[0] = newCoor[0];
                    lastCoor[1] = newCoor[1];
                }
            }catch (Exception e){
                //System.out.println(e);
            }
        }
    }

    public void updateCoordinate(byte[] XCoordinate, byte[] YCoordinate) throws Exception{
        newCoor[0] = ByteBuffer.wrap(XCoordinate).getShort();
        newCoor[1] = ByteBuffer.wrap(YCoordinate).getShort();
        //System.out.println("okeh Coor" + newCoor[0] + " " + newCoor[1]);
        if (lastCoor[0] == -2 && lastCoor[1] == -1) {
            //System.out.println("new");
            lastCoor[0] = newCoor[0];
            lastCoor[1] = newCoor[1];
            return;
        } else if (newCoor[0] == -2 && newCoor[1] == -1) {
            //System.out.println("register-1"+ByteBuffer.wrap(XCoordinate).getShort()+" "+ByteBuffer.wrap(YCoordinate).getShort());
            lastCoor[0] = -2;
            lastCoor[1] = -1;
            return;
        } else {
            synchronized (this){
                //System.out.println("notify");
                notify();
            }
        }
    }

    public void movePointer(int Xdisplacement, int Ydisplacement)throws Exception{
        //System.out.println("moving pointer"+Xdisplacement+" "+Ydisplacement+"");
        currentCoor[0] -= Xdisplacement;
        currentCoor[1] -= Ydisplacement;
        new Robot().mouseMove(currentCoor[0], currentCoor[1]);
        //System.out.println("current pointer"+currentCoor[0]+" "+currentCoor[1]+"\n");
    }

    public void leftClick(){
        try {
            new Robot().mousePress(InputEvent.BUTTON1_MASK);
            new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
            System.out.println("Left CLick");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void rightClick(){
        try {
            new Robot().mousePress(InputEvent.BUTTON3_MASK);
            new Robot().mouseRelease(InputEvent.BUTTON3_MASK);
            System.out.println("Right CLick");

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void leftReleased(){
        try {
            new Robot().mouseRelease(InputEvent.BUTTON1_MASK);
            System.out.println("Left Up ");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void leftPressed(){
        try {
            new Robot().mousePress(InputEvent.BUTTON1_MASK);
            System.out.println("Left Down");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    //private variable onl fro scrolling
    private int lastScrollCoor[] = {-2,-1};
    private int newScrollCoor[] = new int[2];
    int step = 0;
    final int scrollPerStep = 35;

    public void scroll(byte[] XCoordinate, byte[] YCoordinate) throws Exception{
        newScrollCoor[0] = ByteBuffer.wrap(XCoordinate).getShort();
        newScrollCoor[1] = ByteBuffer.wrap(YCoordinate).getShort();
        if (lastScrollCoor[0] == -2 && lastScrollCoor[1] == -1) {
            //System.out.println("new");
            lastScrollCoor[0] = newScrollCoor[0];
            lastScrollCoor[1] = newScrollCoor[1];
            return;
        } else if (newScrollCoor[0] == -2 && newScrollCoor[1] == -1){
            lastScrollCoor[0] = -2;
            lastScrollCoor[1] = -1;
            return;
        } else {
            if((lastScrollCoor[1] - newScrollCoor[1]) > 0) {
                step = step < 0 ? 0 : step + (lastScrollCoor[1] - newScrollCoor[1]);
                System.out.println(step);
                if (step > scrollPerStep) {
                    new Robot().mouseWheel(1);
                    step = 0;
                    System.out.println("down "+step);
                }
            }else {
                step = step > 0 ? 0 : step + (lastScrollCoor[1] - newScrollCoor[1]);
                System.out.println(step);
                if (step < -scrollPerStep) {
                    new Robot().mouseWheel(-1);
                    step = 0;
                    System.out.println("up "+step);
                }
            }
            lastScrollCoor[1] = newScrollCoor[1];
        }
    }

}
