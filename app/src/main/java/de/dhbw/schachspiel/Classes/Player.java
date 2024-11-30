package de.dhbw.schachspiel.Classes;

import de.dhbw.schachspiel.Interfaces.AbstractPiece;
import de.dhbw.schachspiel.Interfaces.AbstractPlayer;

import java.util.Scanner;

public class Player implements AbstractPlayer {
  public enum PlayerColor {
    BLACK, WHITE
  }
  private Color color;
  @Override
  public Move readMove() {
    Scanner s = new Scanner(System.in);
    System.out.println("Enter your move : ");
    String move = s.nextLine();
    s.close();
    try {
      return new Move(move,color);
    } catch (Move.IllegalMoveException e) {
      System.out.println(e.getMessage());
      return readMove();
    }
  }

  @Override
  public void setMove(AbstractPiece move) {

  }

  @Override
  public AbstractPiece getMove() {
    return null;
  }
}
