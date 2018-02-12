package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPImageServer {

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(4000);
        int packetsize = 1024;
        FileOutputStream fos = null;
        double nosofpackets = Math.ceil(((int) (new File("C:/Users/aaols/IdeaProjects/timeserver/src/com/company/eth.jpg")).length()) / packetsize);
        fos = new FileOutputStream("C:/Users/aaols/IdeaProjects/timeserver/src/com/company/eth2.jpg");

        BufferedOutputStream bos = new BufferedOutputStream(fos);

        byte[] mybytearray = new byte[packetsize];
        DatagramPacket receivePacket = new DatagramPacket(mybytearray, mybytearray.length);

        System.out.println(nosofpackets+" "+ Arrays.toString(mybytearray) +" "+ packetsize);

        for (double i = 0; i < nosofpackets + 1; i++){
            serverSocket.receive(receivePacket);
            byte audioData[] = receivePacket.getData();
            System.out.println("Packet:" + (i + 1));
            bos.write(audioData, 0, audioData.length);
        }
    }
}
