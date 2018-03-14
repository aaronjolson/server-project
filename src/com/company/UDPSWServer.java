package com.company;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

/*
* The information provided in the receiver’s window should include (fixed width output), for each datagram received:

Static text

if first time datagram is being received: [RECV]

if duplicate datagram received: [DUPL]

datagram received time in milliseconds

Sequence number of the received data datagram

Condition of data Datagram

if datagram is corrupt: [CRPT]

if datagram is received out of sequence: [!Seq]

if datagram is good: [RECV]

For each ACK datagram, decision for the ACK: [DROP|SENT|ERR]
Use the same rules as the Sender.

Representative Examples (not intended to be complete):

Datagram 1 was received successfully
RECV 100234456 1 RECV

Datagram 1 was received a second time (duplicate)
DUPL 111234456 1 !Seq

Datagram 1 was received but with an error
RECV 100234456 1 CRPT

ACK for Datagram 1 was created and sent successfully
SENDing ACK 1 100234456 SENT

ACK for Datagram 1 was created but was dropped by the network
SENDing ACK 1 100234456 DROP

ACK for Datagram 1 was created but was corrupted by the network
SENDing ACK 1 100234456 ERR

[RECV|DUPL]
[RECV|!Seq|CRPT]
[SENT|DROP|ERR]
* */

public class UDPSWServer {
    static String RECV = "RECV";
    static String DUPL = "DUPL";
    static String CRPT = "CRPT";
    static String SeqEr = "!Seq";
    static String ACK = "ACK";
    static String SENT = "SENT";
    static String ERR = "ERR";
    static String DROP = "DROP";
    static String timeout = "TimeOut";
    static String sendFirstMessage = "SENDing";

    public static void main(String[] args) throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(4000);
        int packetsize = 10000; // This can be any size > sender packet size now
        double allBytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream("output.jpg");

        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        byte[] receiveData = new byte[packetsize];
        byte[] sendData  = new byte[packetsize];

        byte[] mybytearray = new byte[packetsize];
        DatagramPacket receivePacket = new DatagramPacket(mybytearray, mybytearray.length);

        System.out.println("Starting server and waiting for first packet...");
        boolean serverRunning = true;
        int i = 0;

//        datagram.setData,
//        socket.recieve,
//        array.copyOfrange()

        while (serverRunning) {
            try {
                serverSocket.receive(receivePacket);
                serverSocket.setSoTimeout(3000);
                byte binaryData[] = receivePacket.getData();

                binaryData = Arrays.copyOfRange(binaryData, 0, receivePacket.getLength());

                allBytes += binaryData.length;
                System.out.println(RECV + " " + String.format("%d",System.currentTimeMillis()) +
                        " " + (i + 1) + " " + RECV);
                i += 1;
                bufferedOutputStream.write(binaryData, 0, binaryData.length);
            } catch (SocketTimeoutException s) {
                System.out.println("Transfer Finished");
                break;
            }

            System.out.println(sendFirstMessage + " " + ACK + " " + (i) + " " +
                    String.format("%d",System.currentTimeMillis()) + " " + SENT
            );
        }
    }

    private static byte[] trimBytes(byte[] bytes){
        int i = bytes.length -1;
        while(i >= 0 && bytes[i] == 0){
            --i;
        }
        return Arrays.copyOf(bytes, i + 1);
    }
}
