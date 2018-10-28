import java.lang.Exception;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.lang.Byte;
import java.lang.Exception;
import java.lang.String;
import java.lang.System;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


public class UDPServer{

    int packetSize = 8;
    int port = 54785;
    DatagramPacket packet;
    DatagramSocket serverSocket;
    byte[] receivedData = new byte[packetSize];
    UDPPointerController mPC;

    public UDPServer(){
        try {
            serverSocket = new DatagramSocket(port);
            System.out.println("UDP port is opened at " + getMachineIPAddress()+":"+serverSocket.getLocalPort());
            mPC = new UDPPointerController();
            mPC.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen(){
        try{long lastTime = System.currentTimeMillis();
            while(true){
                packet = new DatagramPacket(receivedData,packetSize);
                serverSocket.receive(packet);//this line provides IO Block.
                receivedData = packet.getData();
                switch(receivedData[3]+0) {
                    case 1:
                        mPC.updateCoordinate(Arrays.copyOfRange(receivedData, 4, 6), Arrays.copyOfRange(receivedData, 6, 8));
                        break;
                    case 2:
                        mPC.leftClick();
                        break;
                    case 3:
                        mPC.rightClick();
                        break;
                    case 4:
                        mPC.leftPressed();
                        break;
                    case 5:
                        mPC.leftReleased();
                        break;
                    case 6:
                        mPC.scroll(Arrays.copyOfRange(receivedData, 4, 6), Arrays.copyOfRange(receivedData, 6, 8));
                        //System.out.println("X :"+ByteBuffer.wrap(Arrays.copyOfRange(receivedData, 4, 6)).getShort()+"Y :"+ByteBuffer.wrap(Arrays.copyOfRange(receivedData, 6, 8)).getShort());
                        break;
                    case 88:
                        byte[] pingResponse = ByteBuffer.allocate(4).putInt(8888).array();
                        DatagramPacket responsePacket = new DatagramPacket(pingResponse,pingResponse.length,packet.getSocketAddress());
                        serverSocket.send(responsePacket);
                        System.out.println("receiver ping. send response to "+packet.getSocketAddress());
                }

                /*for(byte b : receivedData) {
                    System.out.print(b +" ");
                }
                System.out.println( packet.getAddress() + " " + packet.getPort() +
                        " X coor:" + ByteBuffer.wrap(Arrays.copyOfRange(receivedData,4,6)).getShort()+
                        " Y coor:"+ByteBuffer.wrap(Arrays.copyOfRange(receivedData,6,8)).getShort());*/
                //System.out.println("gaptime : " + (System.currentTimeMillis() - lastTime));
                //lastTime = System.currentTimeMillis();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        new UDPServer().listen();
    }

    private String getMachineIPAddress(){
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces.hasMoreElements()){
                NetworkInterface netInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = netInterface.getInetAddresses();
                while(inetAddresses.hasMoreElements()) {
                    InetAddress ipAddress = inetAddresses.nextElement();
                    System.out.println(ipAddress.getHostAddress());
                    byte[] ip = (ipAddress.getAddress());
                   // if((ip.length == 4)&&(ipAddress != null) && (((byte)ip[0]==(byte)0xC0) || ((byte)ip[0]==(byte)0x10))) {
                       // if(netInterface.getDisplayName().indexOf("Wireless") != -1){
                           // System.out.print(netInterface.getDisplayName());
                           // System.out.println("(Preferred)");
                       // }else{
                           // System.out.println(netInterface.getDisplayName());
                       // }
                       // System.out.println("InetAddress: " + ipAddress.getHostAddress());
                       // System.out.println();

                   // }
                    if((ip.length == 4)&&(ipAddress != null) && (((byte)ip[0]==(byte)0xC0) || ((byte)ip[0]==(byte)0x10))) {
                    System.out.println(ipAddress.getHostAddress());
                        if (netInterface.getDisplayName().indexOf("Wireless") != -1 || netInterface.getDisplayName().indexOf("Hosted") != -1) {
//                            System.out.print(netInterface.getDisplayName());
//                            System.out.println("(Preferred)");
//                            System.out.println("InetAddress: " + ipAddress.getHostAddress());
                            return ipAddress.getHostAddress();

                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return"No IP Address Availabe";
    }

}
