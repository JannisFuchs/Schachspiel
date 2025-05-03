package de.dhbw.schachspiel.classes.logger;

import de.dhbw.schachspiel.interfaces.ILogger;

import static java.lang.System.err;

public class ErrorLogger implements ILogger
{
    @Override
    public void log(String message)
    {
        String formattedMessage = String.format("ERROR: %s", message);
        err.println(formattedMessage);
        FileLogger.INSTANCE.writeToFile(formattedMessage);

    }
}
