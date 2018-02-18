package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPImageClient {
    // C:\Users\aaols\IdeaProjects\timeserver\src>javac com/company/*.java
    // java -classpath . com.company.UDPImageClient .././keyboard.jpg

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
            int packetsize = 1024;
            double allBytes = 0;
            long file_length = myFile.length();
            long countdown = myFile.length();

            int numberOfPackets = (int)Math.ceil( myFile.length() / (packetsize - 24)); // - packet overhead

//            System.out.println("Number of packets needed " + (numberOfPackets + 1));

            bufferedInputStream = new BufferedInputStream(new FileInputStream(myFile));
            for (int i = 0; i < numberOfPackets+1; i++) {
                byte[] mybytearray = new byte[packetsize];
                bufferedInputStream.read(mybytearray, 0, mybytearray.length);

                allBytes += mybytearray.length;
                countdown -= 1000;
                if (countdown > 0) {
                    System.out.println("Packet: " + (i + 1) +
                            " - " + String.format("%d", (long) ((file_length - countdown) - 1000)) +
                            " - " + String.format("%d", (long) (file_length - countdown)));
                } else { // last packet, not full sized
                    System.out.println("Packet: " + (i + 1) +
                            " - " + String.format("%d", (long) ((file_length - countdown) - 1000)) +
                            " - " + String.format("%d", (long) (file_length)));
                }
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
