package de.dhbw.schachspiel.Classes;

import de.dhbw.schachspiel.Interfaces.AbstractPiece;

public class Move {
  public int startX;
  public int startY;
  public int endX;
  public int endY;
  public AbstractPiece piece;

  Move(String stringRepresentation) throws IllegalMoveExcetion {
    if (!isMoveValid())
      throw new IllegalMoveExcetion("Invalid Move");
  }

  private boolean isMoveValid() {
    return true;
  }

  public class IllegalMoveExcetion extends Exception {
    public IllegalMoveExcetion(String message) {
      super(message);
    }
  }

}
