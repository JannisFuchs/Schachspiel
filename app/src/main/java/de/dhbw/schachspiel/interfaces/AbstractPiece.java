package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;

public interface AbstractPiece {
  // gets the piece as a unicode character
  char getSymbol();
  // gets color of the piece
  Color getColor();
  // sets the color of the piece
  void setColor(Color color);

  /**
   *
   * @param move the move this piece is about to make
   * @param board the whole board before the piece moves
   * @return the field on which the piece currently stands
   */
  Field calculateStartField(Move move,AbstractPiece[][] board) throws Move.IllegalMoveException;
}
