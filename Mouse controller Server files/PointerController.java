import java.awt.Robot.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;
import java.nio.*;

public class PointerController extends Thread{
	
	public static int lastCoor[] = {-1,-1};
	public static int currentCoor[] = {MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y};
	public int newCoor[] = new int[2];
	Object Lock = new Object();
	
	byte[] data;
	
	public PointerController(){
		//System.out.println("okeh");
	}
	
	@Override
	public void run(){
		setPriority(Thread.MAX_PRIORITY);
		try{
			while(true){
				synchronized(this){
					//System.out.println("wait");
					wait();
					//System.out.println("continue");
					//System.out.println("lastcoor "+lastCoor[0]+" "+lastCoor[1]);
					//System.out.println("newCoor "+newCoor[0]+" "+newCoor[1]);
					if(newCoor[0]!=-1 || newCoor[1]!=-1){
						movePointer(lastCoor[0]-newCoor[0],lastCoor[1]-newCoor[1]);		
					}else{
						continue;
					}
					lastCoor[0] = newCoor[0];
					lastCoor[1] = newCoor[1];
				}
			}
		}catch (Exception e){
			//System.out.println(e);
		}
	}
	
	public void updateCoordinate(byte[] XCoordinate, byte[] YCoordinate) throws Exception{
		newCoor[0] = ByteBuffer.wrap(XCoordinate).getShort();
		newCoor[1] = ByteBuffer.wrap(YCoordinate).getShort();

		
		//System.out.println("okeh Coor"+newCoor[0]+" "+newCoor[1]);
		if(lastCoor[0]==-1 && lastCoor[1] == -1){
			//System.out.println("new");
			lastCoor[0] = newCoor[0];
			lastCoor[1] = newCoor[1];
			return;
		}else if(newCoor[0]==-1 || newCoor[1]==-1){
			synchronized(this){
				//System.out.println("register-1"+ByteBuffer.wrap(XCoordinate).getShort()+" "+ByteBuffer.wrap(YCoordinate).getShort());
				lastCoor[0]= -1;
				lastCoor[1]= -1;
			}
			return;
		}else{
			//System.out.println("notify");
			synchronized(this){
				notify();
			}
		}
	}
	
	public void movePointer(int Xdisplacement, int Ydisplacement)throws Exception{
		//System.out.println("moving pointer"+Xdisplacement+" "+Ydisplacement+"");
		currentCoor[0] -= Xdisplacement;
		currentCoor[1] -= Ydisplacement;
		new Robot().mouseMove(currentCoor[0],currentCoor[1]);
		//System.out.println("current pointer"+currentCoor[0]+" "+currentCoor[1]+"\n");
	}
	
}