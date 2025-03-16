package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IPiece;
import de.dhbw.schachspiel.interfaces.IPlayer;

import java.util.Scanner;

public class Player implements IPlayer {
  
  private final Color color;
  public Player(Color color) {
    this.color = color;
  }
  @Override
  public Move readMove(Scanner s) { //dependency injection

    String move;
    boolean moveValid = false;
    Move result = null;
    while (!moveValid) {
      try {
        System.out.println(color.name()+" Enter your move : ");
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
  public IPiece getMove() {
    return null;
  }
}
