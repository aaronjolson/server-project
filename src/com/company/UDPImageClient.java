package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPImageClient {

    public static void main(String[] args) throws IOException {
        String pathString;
        if (args.length > 0) {
            pathString = args[0];
        } else {
            pathString = "./keyboard.jpg";
        }
        File myFile = new File(pathString);
        BufferedInputStream bufferedInputStream = null;
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            int packetsize = 64;
            double allBytes = 0;

            int numberOfPackets = (int)Math.ceil( myFile.length() / (packetsize - 4)); // - packet overhead

            bufferedInputStream = new BufferedInputStream(new FileInputStream(myFile));
            for (int i = 0; i < numberOfPackets+1; i++) {
                byte[] mybytearray = new byte[packetsize];
                bufferedInputStream.read(mybytearray, 0, mybytearray.length);

                allBytes += mybytearray.length;
                System.out.println("Packet: " + (i + 1) +
                        " - " + String.format("%d",(long)(allBytes - packetsize)) +
                        " - " + String.format("%d",(long)allBytes));
                DatagramPacket datagramPacket = new DatagramPacket(mybytearray, mybytearray.length, InetAddress.getByName("127.0.0.1"), 4000);
                datagramSocket.send(datagramPacket);
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
