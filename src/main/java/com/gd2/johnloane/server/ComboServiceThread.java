package com.gd2.johnloane.server;

import com.dkit.gd2.johnloane.core.ComboServiceDetails;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ComboServiceThread extends Thread
{
    private Socket dataSocket;
    private Scanner input;
    private PrintWriter output;
    private int number;

    public ComboServiceThread(ThreadGroup group, String name, Socket dataSocket, int number)
    {
        super(group, name);
        try
        {
            this.dataSocket = dataSocket;
            this.number = number;
            input = new Scanner(new InputStreamReader(this.dataSocket.getInputStream()));
            output = new PrintWriter(this.dataSocket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        /*  Loop
            Wait for a message
            Process message
            Send response
         */
        String incomingMessage = "";
        String response;

        try
        {
            //Loop while the client does not want to end the session
            while(!incomingMessage.equals(ComboServiceDetails.END_SESSION))
            {
                response = null;

                //Take the input from the client
                incomingMessage = input.nextLine();
                System.out.println("Received message: " + incomingMessage);

                //Break the input into components
                String[] components = incomingMessage.split(ComboServiceDetails.COMMAND_SEPARATOR);

                CommandFactory factory = new CommandFactory();
                Command command = factory.createCommand(components[0]);

                if(command != null)
                {
                    response = command.createResponse(components);
                }
                else if(components[0].equals(ComboServiceDetails.END_SESSION))
                {
                    response = ComboServiceDetails.SESSION_TERMINATED;
                }
                else
                {
                    response = ComboServiceDetails.UNRECOGNISED;
                }

                //Send back the response to the client
                output.println(response);
                output.flush();
            }
        }
        catch(NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        }
        finally
        {
            try
            {
                System.out.println("\nClosing the connect with client #"+ number + "...");
                dataSocket.close();
            }
            catch (IOException e)
            {
                System.out.println("Unable to disconnect " + e.getMessage());
                System.exit(1);
            }
        }
    }
}
