package com.company;

public class Packet {
    short cksum; //16-bit 2 byte
    short len; //16-bit 2 byte
    int ackno; //32-bit 4 byte
    int seqno; //32-bit 4 byte
    byte data; //0-500 bytes [] data packet only

    /*
    * cksum: 2 byte IP checksum. 0 for good, 1 for bad
    *
    * len: 2 byte total length of path,
    *   For Ack packet this is 8: 2 for cksum, 2 for len, and 2 for ACK no
    *   for data packets this is 12 + payload size:
    *       2 for cksum, 2 for len, 4 for ACK no, 4 for seqno, and as many bytes as there are in data[]
    *
    * ackno: ackno: 4-byte cumulative acknowledgment number.
    *   ackno is the sequence number you are waiting for, that you have not received yet – it is the equivalent of Next Frame Expected.
    *   This says that the sender of a packet has received all packets with sequence numbers earlier than ackno,
    *   and is waiting for the packet with a seqno of ackno. The first sequence number in any connection is 1,
    *   so if you have not received any packets yet, you should set ackno to 1.
    *   ackno will not contain seqno or data
    *
    * seqno: Each packet transmitted in a stream of data must be numbered with a seqno. The first packet in a stream has a seqno of 1. This protocol numbers packets.
    *
    * data: Contains (len - 12) bytes of payload data for the application.
    *   To conserve packets, a sender should not send more than one unacknowledged Data frame with less than the maximum number of bytes (500)
    *   Use a datagram with an empty data[] to indicate the end of your stream.
    * */
}
