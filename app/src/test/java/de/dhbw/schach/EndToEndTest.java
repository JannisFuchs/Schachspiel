package de.dhbw.schach;

import de.dhbw.schachspiel.classes.Game;
import de.dhbw.schachspiel.classes.PieceColor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

class EndToEndTest
{
    @Test
    void gameMate() throws IOException //https://lichess.org/xE1Ew23BnHfS
    {
        String path= "/home/huchsle/Schachspiel/Checkmate.pgn";
        PgnConverter converter = new PgnConverter(path);
        System.out.println(converter.getMoves());
        Scanner testScanner=new Scanner(converter.getMoves());
        String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        Game game = new Game(startPosition,testScanner);
        boolean isOver = false;
        while(!isOver){
            isOver = game.play();
        }
        Assertions.assertSame(PieceColor.BLACK,game.getWinner() );
    }
    @Test
    void gameDraw() throws IOException //https://lichess.org/E4KBdoIC
    {
        String path= "/home/huchsle/Schachspiel/Stalemate.pgn";
        PgnConverter converter = new PgnConverter(path);
        System.out.println(converter.getMoves());
        Scanner testScanner=new Scanner(converter.getMoves());
        String startPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w";
        Game game = new Game(startPosition,testScanner);
        boolean isOver = false;
        while(!isOver){
            isOver = game.play();
        }
        Assertions.assertNull(game.getWinner());
    }
}
