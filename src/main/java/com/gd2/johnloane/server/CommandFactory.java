package com.gd2.johnloane.server;

import com.dkit.gd2.johnloane.core.ComboServiceDetails;

public class CommandFactory
{
    public Command createCommand(String command)
    {
        Command c = null;

        if(command.equals(ComboServiceDetails.ECHO))
        {
            c = new EchoCommand();
        }
        else if(command.equals(ComboServiceDetails.DAYTIME))
        {
            c = new DaytimeCommand();
        }
        return c;
    }
}
