/**
*    RED CHAT CLIENT
*    Connects to a UDP Server
*    Receives a line of input from the keyboard and sends it to the server
*    Receives a response from the server and displays it.
*
*    author: Jeff Yue
*    Partner: Jeran Ulrich
*    version: 2.1
*/

import java.io.*;
import java.net.*;

class UDPClient {
    
    public static void main(String args[]) throws Exception
    {

      BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

      DatagramSocket clientSocket = new DatagramSocket();

      InetAddress IPAddress = InetAddress.getByName("10.49.139.28");

      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      
      //Start chat protocol
      
      //Send initial contact
      String firstContact = "HELLO";         //Start
      int lastContact = 900;        //End
      sendData = firstContact.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
      clientSocket.send(sendPacket);
    
      //Receive first response
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      clientSocket.receive(receivePacket);
    
      //Check response from server
      String firstRawReceive = new String(receivePacket.getData());
      int firstReceive = Integer.parseInt(firstRawReceive);
      while(firstReceive == 100)          //Wait for other user 
      {
         clientSocket.receive(receivePacket);
         firstRawReceive = new String(receivePacket.getData());
         firstReceive = Integer.parseInt(firstRawReceive);
      
      }
      if(firstReceive == 200)             //Both users on
      {
         while(firstReceive != lastContact)       //Check for END statement
         {
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendNewPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendNewPacket);

            DatagramPacket receiveNewPacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receiveNewPacket);
            firstRawReceive = new String(receiveNewPacket.getData());
            firstReceive = Integer.parseInt(firstRawReceive);
            System.out.println("FROM USER: " + firstRawReceive);
         }
        
      
      }
      
      clientSocket.close();
      }
}