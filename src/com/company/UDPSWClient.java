package com.company;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
* For each ACK received

Static text [AckRcvd]:

Sequence number of datagram that was ACKed

Static text:

if duplicate ACK received: [DuplAck]

if corrupted ACK is received: [ErrAck.]

if ACK will move window: [MoveWnd]

For each timeout event

Static text [TimeOut]:

Sequence number of datagram that timed out

Representative Examples (not intended to be complete):

first time sending of a datagram
SENDing 1 0:10 100234456 SENT

resending a datagram that has timed out
ReSend. 1 0:10 122234456 SENT

first time sending of a datagram, but datagram was dropped and never sent
SENDing 1 0:10 100234456 DROP

first time sending of a datagram, but datagram was corrupted by the network
SENDing 1 0:10 100234456 ERRR

ACK received for frame 1, and window will move
AckRcvd 1 MoveWnd

ACK received for frame 1, but had an error, so window will not move
AckRcvd 1 ErrAck.

Duplicate ACK received for frame 1:
AckRcvd 1 DuplAck

Timeout occurred for datagram 1:
TimeOut 1
* */


public class UDPSWClient {
    static int akno=0;
    static int seqno=0;
    static byte[] sendData = new byte[1024];

    static String sendFirstMessage = "SENDing ";
    static String resendMessage = "ReSend. ";
    static String SENT = "SENT";
    static String DROP = "DROP";
    static String ERR= "ERR";
    static String ackRcvd = "AckRcvd";
    static String duplAck = "DuplAck";
    static String errAck = "ErrAck";
    static String moveWnd = "MoveWnd";
    static String timeout = "TimeOut";


    // sequence number of datagram (integer, [0,(size of file)/(size of packet)]

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
            int extraDataOffset = packetsize / 16;
            long allBytes = 0;

            int numberOfPackets = (int)Math.ceil( myFile.length() / (packetsize - extraDataOffset)); // - packet overhead

            bufferedInputStream = new BufferedInputStream(new FileInputStream(myFile));
            for (int i = 0; i < numberOfPackets+1; i++) {
                byte[] mybytearray = new byte[packetsize];
                bufferedInputStream.read(mybytearray, 0, mybytearray.length);

                allBytes += mybytearray.length;
                System.out.println( sendFirstMessage + (i + 1) + " " +
                        String.format("%d",(allBytes - packetsize)) +
                        ":" + String.format("%d",allBytes) + " " +
                        String.format("%d",System.currentTimeMillis()) + " " + SENT );
                DatagramPacket datagramPacket = new DatagramPacket(mybytearray, mybytearray.length, InetAddress.getByName("127.0.0.1"), 4000);
                datagramSocket.send(datagramPacket);
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(ackRcvd +  " " + (i + 1) + " " + moveWnd);
            }
        } finally {
            if (bufferedInputStream != null)
                bufferedInputStream.close();
            if (datagramSocket != null)
                datagramSocket.close();
        }

    }
}
