package de.dhbw.schachspiel.classes.logger;

import de.dhbw.schachspiel.interfaces.ILogger;

import static java.lang.System.out;

public class GameLogger implements ILogger
{
    @Override
    public void log(String message)
    {
        out.print(message);
    }
}
