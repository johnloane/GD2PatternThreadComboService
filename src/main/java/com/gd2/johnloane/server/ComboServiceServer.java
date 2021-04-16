package com.gd2.johnloane.server;

import com.dkit.gd2.johnloane.core.ComboServiceDetails;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ComboServiceServer
{
    public static void main(String[] args)
    {
        try
        {
            //Set up the listening socket
            ServerSocket listeningSocket = new ServerSocket(ComboServiceDetails.LISTENING_PORT);

            //Set up a ThreadGroup to manage all of the client threads
            ThreadGroup clientThreadGroup = new ThreadGroup("Client threads");

            //Place more emphasis on accepting clients than processing them
            //Set priority of main thread to be one more than client threads
            clientThreadGroup.setMaxPriority(Thread.currentThread().getPriority() - 1);

            //Do the main logic of the server
            boolean continueRunning = true;
            int threadCount = 0;

            while (continueRunning)
            {
                //Wait for incoming connection and build communication link
                Socket dataSocket = listeningSocket.accept();

                threadCount++;
                System.out.println("The server has now accepted " + threadCount + " clients");

            /* Build the thread - need to give the thread
               1) Group to be stored in
               2) A name
               3) A socket to communicate through
               4) How many clients are connected
            */
                ComboServiceThread newClient = new ComboServiceThread(clientThreadGroup, dataSocket.getInetAddress() + "", dataSocket, threadCount);
                newClient.start();
            }
            listeningSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
