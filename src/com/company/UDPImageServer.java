package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

public class UDPImageServer {

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(4000);
        int packetsize = 1024;
        double allBytes = 0;
        double numberOfPackets = Math.ceil(((new File("C:/Users/aaols/IdeaProjects/timeserver/src/com/company/eth.jpg")).length()) / packetsize);
        FileOutputStream fileOutputStream = new FileOutputStream("output.jpg");

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        byte[] mybytearray = new byte[packetsize];
        DatagramPacket receivePacket = new DatagramPacket(mybytearray, mybytearray.length);

        System.out.println(numberOfPackets);

        for (int i = 0; i < numberOfPackets + 3; i++){
            serverSocket.receive(receivePacket);
            byte binaryData[] = receivePacket.getData();
            allBytes += binaryData.length;
            System.out.println("Packet: " + (i + 1) +
                    " - " + String.format("%d",(long)(allBytes - packetsize)) +
                    " - " + String.format("%d",(long)allBytes));
            bufferedOutputStream.write(binaryData, 0, binaryData.length);
        }
    }
}
