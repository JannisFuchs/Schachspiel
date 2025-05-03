package de.dhbw.schachspiel.classes.logger;

import de.dhbw.schachspiel.interfaces.ILogger;

public class LogHandler
{
    private final ILogger gameLogger = new GameLogger();
    private final ILogger errorLogger = new ErrorLogger();
    private final ILogger infoLogger = new InfoLogger();
    private final ILogger testLogger = new TestLogger();
    public final boolean isTestEnabled;
    public LogHandler(boolean isTestEnabled){
        this.isTestEnabled = isTestEnabled;
    }
    public void log(String message,LogType type)
    {
        switch (type){
            case ERROR -> errorLogger.log(message);
            case GAME -> gameLogger.log(message);
            case INFO -> infoLogger.log(message);
            case TEST -> {
                if (isTestEnabled)testLogger.log(message);
            }
        }
    }

}
