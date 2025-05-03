package de.dhbw.schachspiel.classes.logger;

import de.dhbw.schachspiel.interfaces.ILogger;

import static java.lang.System.out;

public class TestLogger implements ILogger
{
    @Override
    public void log(String message)
    {
        String formattedMessage = String.format("TEST: %s", message);
        out.println(formattedMessage);
        FileLogger.INSTANCE.writeToFile(formattedMessage);

    }
}
