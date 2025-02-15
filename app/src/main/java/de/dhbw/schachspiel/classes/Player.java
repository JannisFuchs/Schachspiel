package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.AbstractPiece;
import de.dhbw.schachspiel.interfaces.AbstractPlayer;

import java.util.Scanner;

public class Player implements AbstractPlayer {
  public enum PlayerColor {
    BLACK, WHITE
  }
  private Color color;
  @Override
  public Move readMove() {
    Scanner s = new Scanner(System.in);

    String move;
    boolean moveValid = false;
    Move result = null;
    while (!moveValid) {
      try {
        System.out.println("Enter your move : ");
        move = s.nextLine();
        result = new Move(move, color);
        moveValid = true;
      } catch (Move.IllegalMoveException e) {
        System.out.println(e.getMessage());
      }
    }
    return result;
  }

  @Override
  public void setMove(AbstractPiece move) {

  }

  @Override
  public AbstractPiece getMove() {
    return null;
  }
}
