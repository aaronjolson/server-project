package com.company;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

public class UDPImageServer {

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(4000);
        int packetsize = 64;
        double allBytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream("output.jpg");

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        byte[] mybytearray = new byte[packetsize];
        DatagramPacket receivePacket = new DatagramPacket(mybytearray, mybytearray.length);

        System.out.println("Starting Server...");
        boolean serverRunning = true;
        int i = 0;
        while (serverRunning) {
            try {
                serverSocket.receive(receivePacket);
                serverSocket.setSoTimeout(5000);
                byte binaryData[] = receivePacket.getData();
                allBytes += binaryData.length;
                System.out.println("Packet: " + (i + 1) +
                        " - " + String.format("%d", (long) (allBytes - packetsize)) +
                        " - " + String.format("%d", (long) allBytes));
                i += 1;
                bufferedOutputStream.write(binaryData, 0, binaryData.length);
            } catch (SocketTimeoutException s) {
                    System.out.println("Transfer Finished");
                    break;
            }
        }
    }
}
