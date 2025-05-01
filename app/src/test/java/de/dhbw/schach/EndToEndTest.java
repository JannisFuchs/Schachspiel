package de.dhbw.schach;

import de.dhbw.schachspiel.classes.Game;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

class EndToEndTest
{
    @Test
    void allWorking() throws IOException
    {
        String path= "/home/huchsle/Schachspiel/lichess_pgn_2025.05.01_FuerstAgus_vs_lichess_AI_level_8.xE1Ew23B.pgn";
        PgnConverter converter = new PgnConverter(path);
        Scanner testScanner=new Scanner(converter.getMoves());
        String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        Game game = new Game(startPosition,testScanner);
        game.play();
    }
}
