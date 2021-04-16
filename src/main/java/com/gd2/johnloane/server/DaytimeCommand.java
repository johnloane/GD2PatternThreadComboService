package com.gd2.johnloane.server;

import java.util.Date;

public class DaytimeCommand implements Command
{
    @Override
    public String createResponse(String[] components)
    {
        return new Date().toString();
    }
}
