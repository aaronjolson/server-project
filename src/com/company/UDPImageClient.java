package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPImageClient {

    public static void main(String[] args) throws IOException {
        File myFile = new File("C:/Users/aaols/IdeaProjects/timeserver/src/com/company/eth.jpg");
        DatagramSocket ds = null;
        BufferedInputStream bis = null;
        try {
            ds = new DatagramSocket();
            DatagramPacket dp;
            int packetsize = 1024;
            double nosofpackets;
            nosofpackets = Math.ceil(((int) myFile.length()) / packetsize);

            bis = new BufferedInputStream(new FileInputStream(myFile));
            for (double i = 0; i < nosofpackets + 1; i++) {
                byte[] mybytearray = new byte[packetsize];
                bis.read(mybytearray, 0, mybytearray.length);
                System.out.println("Packet:" + (i + 1));
                dp = new DatagramPacket(mybytearray, mybytearray.length, InetAddress.getByName("127.0.0.1"), 4000);
                ds.send(dp);
            }
        } finally {
            if (bis != null)
                bis.close();
            if (ds != null)
                ds.close();
        }

    }
}
