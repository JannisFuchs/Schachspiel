package de.dhbw.schach;

import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceColor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PgnConverter
{
    private final String movesOfGame;
    public PgnConverter(String filePath) throws IOException
    {
        String moveString = getMoveString(filePath);
        movesOfGame = generateMove(moveString);
    }

    private String getMoveString(String filePath) throws IOException
    {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;

        while ((line = reader.readLine()) != null)
        {
            if ((!line.isEmpty())&&line.charAt(0) == '1')
            {
                return line;
            }
        }
        throw new IOException("PGN-Error: Moves not found");

    }

    private String generateMove(String moveString)
    {
        String onlyMoves = moveString.replaceAll(" [0-9]*\\. "," ");
        onlyMoves =onlyMoves.replaceAll("1. ","");
        onlyMoves = onlyMoves.replaceAll("1-0","");
        return onlyMoves.replaceAll(" ","\n");
    }
    public String getMoves(){
     return movesOfGame;
    }
}
