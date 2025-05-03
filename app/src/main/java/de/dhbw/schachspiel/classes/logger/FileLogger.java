package de.dhbw.schachspiel.classes.logger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.*;

import static java.lang.System.*;

public enum FileLogger
{
    INSTANCE;
    final File logFile;
    
    FileLogger()
    {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String logDirPath = "logs";
        String fileName = "log_" + date + ".txt";
        
        // Create logs directory if it doesn't exist
        try
        {
            Files.createDirectories(Paths.get(logDirPath));
        }
        catch (IOException e)
        {
            err.println("Failed to create logs directory: " + e.getMessage());
        }
        
        logFile = new File(logDirPath, fileName);
        try
        {
            if (logFile.createNewFile())
            {
                out.println("Log file created: " + logFile.getName());
            }
            else
            {
                out.println("Log file already exists.");
            }
        }
        catch (Exception e)
        {
            err.println("An error occurred while creating the log file: " + e.getMessage());
        }
    }

    public void writeToFile(String message)
    {
        try
        {
            Files.write(
                    logFile.toPath(),
                    (message + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND,
                    StandardOpenOption.CREATE
            );
        }
        catch (IOException e)
        {
            err.println("Failed to write to log file: " + e.getMessage());
        }
    }

}