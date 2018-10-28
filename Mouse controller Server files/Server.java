import java.awt.Robot.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Robot.*;
import java.awt.*;
import java.nio.*;
public class Server{
	int serverPort = 54785;
	ServerSocket server;
	
	public Server() throws Exception{
		server = new ServerSocket(serverPort);
		System.out.println("Listener port at : "+server.getLocalSocketAddress());
	}
	
	public void listen(){
		while(true){
			try{
				Socket socket = server.accept();
				
				new ClientThread(socket).start();
				
			}catch (Exception e){
				System.out.println("ASD");
			}
		}
	}
	
	//Construct a thread to handle client's command
	public class ClientThread extends Thread{
		Socket socket;
		int counter = 1;
		int counter1 = 0;
		int counter2 = 0;
		int counter3 = 0;

		public ClientThread(Socket socket){
			this.socket = socket;
			System.out.println(socket+" binded ");
		}
		
		public String arrowHeading(int value){ //
			switch (value){
			case 183: 
				this.counter = 1; 
				return "UP";
			case 184:  
				this.counter = 1; 
				return "DOWN";
			case 185:  
				this.counter = 1; 
				return "RIGHT";
			case 186: 
				this.counter = 1; 
				return "LEFT";
			}
			return"";
		}
		
		public String arrowDetector(int data){
			switch (this.counter){ //determine if the arrow key is pressed
			case 1 : 
				this.counter1 = data;
				this.counter2 = 0;
				this.counter3 = 0;
				this.counter++;
				//System.out.println("case 1");
				break;
			case 2 :
				this.counter1 += data;
				this.counter2 = data;
				this.counter++;
				//System.out.println("case 2");
				break;
			case 3 :
				this.counter++;
				this.counter1 += data;
				this.counter2 += data;
				this.counter3 = data;
				if(arrowHeading(counter1)!=""){
					return arrowHeading(counter1);
				}
				//System.out.println("case 3");
				break;
			case 4:
				//System.out.println("case 4");
				this.counter++;
				this.counter1 = data;
				this.counter2+=data;
				this.counter3+=data;
				if(arrowHeading(counter2)!=""){
					return arrowHeading(counter2);
				}
				break;
			case 5:
				//System.out.println("case 5");
				this.counter++;
				this.counter2 = data;
				this.counter1+=data;
				this.counter3+=data;
				if(arrowHeading(counter3)!=""){
					return arrowHeading(counter3);
				}
				break;
			case 6:
				//System.out.println("case 6");
				this.counter=4;
				this.counter3 = data;
				this.counter1+=data;
				this.counter2+=data;
				if(arrowHeading(counter1)!=""){
					return arrowHeading(counter1);
				}
				break;
			}
			return"";
		}
		
		public void run(){
			try{
				InputStream in = socket.getInputStream();
				System.out.println("Thread - 1 is initiatied");
				System.out.println("Press arrow to move cursors!");
				int data;
				byte[] XCoordinate = new byte[2];
				byte[] YCoordinate = new byte[2];
				PointerController mPC = new PointerController();
				mPC.start();
				long lastTime = System.currentTimeMillis();
				while(true){
					XCoordinate[0] = (byte)in.read();
					System.out.print(XCoordinate[0]+" ");
					XCoordinate[1] = (byte)in.read();
					System.out.print(XCoordinate[1]+" ");
					YCoordinate[0] = (byte)in.read();
					System.out.print(YCoordinate[0]+" ");
					YCoordinate[1] = (byte)in.read();
					System.out.println(YCoordinate[1]);
					System.out.println("gaptime : "+ (System.currentTimeMillis() - lastTime));
					lastTime = System.currentTimeMillis();
					//new Robot().mouseMove(MouseInfo.getPointerInfo().getLocation().x-ByteBuffer.wrap(XCoordinate).getShort(),MouseInfo.getPointerInfo().getLocation().y-ByteBuffer.wrap(YCoordinate).getShort());
					//mPC.updateCoordinate(XCoordinate,YCoordinate);
					
					
					/*String arrowDirection = arrowDetector(data);
					if (arrowDirection == ""){
					}else{
						switch (arrowDirection){
							case "UP": new Robot().mouseMove(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y-5); break;
							case "DOWN": new Robot().mouseMove(MouseInfo.getPointerInfo().getLocation().x,MouseInfo.getPointerInfo().getLocation().y+5); break;
							case "LEFT": new Robot().mouseMove(MouseInfo.getPointerInfo().getLocation().x-5,MouseInfo.getPointerInfo().getLocation().y); break;
							case "RIGHT": new Robot().mouseMove(MouseInfo.getPointerInfo().getLocation().x+5,MouseInfo.getPointerInfo().getLocation().y); break;
						}
						System.out.println("panah ke "+arrowDirection);
					}*/
				}
			}catch (Exception e){
				System.out.println(e);
			}
		}
	}
	
	public static void main(String args[]) throws Exception{
		new Server().listen();
	}
}