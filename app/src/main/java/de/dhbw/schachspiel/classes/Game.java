package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.Scanner;

public class Game {
  private static final Scanner SCANNER = new Scanner(System.in);
  int currentPlayer ;
  Player[] players = new Player[2];

	public Game() {
    players[0] = new Player(Color.WHITE);
    players[1] = new Player(Color.BLACK);
    currentPlayer = 0;
    IBoard board = new Board(8,8);
    Visualisation.drawBoard(board);
    for(int i=0;i<50;i++){
      //this is a duplication see Move class
      boolean isValid = false;
      while(!isValid){
        try{
          Move m = players[currentPlayer].readMove(SCANNER);
          board.makeMove(m);
          isValid = true;
        }
        catch(Move.IllegalMoveException e){
          System.out.println(e.getMessage());
        }
      }


      Visualisation.drawBoard(board);
      currentPlayer = (currentPlayer + 1) % 2;
    }


  }



  public boolean checkForLegalMove() {
    return false;
  }





}
