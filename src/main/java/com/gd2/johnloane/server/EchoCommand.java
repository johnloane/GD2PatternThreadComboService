package com.gd2.johnloane.server;

import com.dkit.gd2.johnloane.core.ComboServiceDetails;

public class EchoCommand implements Command
{
    /*
    ECHO%%Tell me more about Sockets
     */
    @Override
    public String createResponse(String[] components)
    {
        StringBuffer echoMessage = new StringBuffer("");
        //ECHO%%Blah
        if(components.length > 1)
        {
            echoMessage.append(components[1]);
            //If the user %% in their message
            //ECHO%%I%%Wonder%%CAN%%I%%Crash%%This%%Server
            for(int i=2; i < components.length; ++i)
            {
                echoMessage.append(ComboServiceDetails.COMMAND_SEPARATOR);
                echoMessage.append(components[i]);
            }
        }
        return echoMessage.toString();
    }
}
