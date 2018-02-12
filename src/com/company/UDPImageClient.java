package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPImageClient {

    public static void main(String[] args) throws IOException {
        File myFile = new File("C:/Users/aaols/IdeaProjects/timeserver/src/com/company/eth.jpg");
        BufferedInputStream bufferedInputStream = null;
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            int packetsize = 1024;
            double nosofpackets = Math.ceil(((int) myFile.length()) / packetsize);

            bufferedInputStream = new BufferedInputStream(new FileInputStream(myFile));
            for (double i = 0; i < nosofpackets+10; i++) {
                byte[] mybytearray = new byte[packetsize];
                bufferedInputStream.read(mybytearray, 0, mybytearray.length);
                System.out.println("Packet:" + (i + 1) + " - " + mybytearray.length + " - " + nosofpackets);
                DatagramPacket dp = new DatagramPacket(mybytearray, mybytearray.length, InetAddress.getByName("127.0.0.1"), 4000);
                datagramSocket.send(dp);
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (bufferedInputStream != null)
                bufferedInputStream.close();
            if (datagramSocket != null)
                datagramSocket.close();
        }

    }
}
