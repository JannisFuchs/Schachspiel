package de.dhbw.schachspiel.interfaces;

import de.dhbw.schachspiel.classes.*;

public interface IPiece {
  // gets the piece as a unicode character
  char getSymbol();
  Color getColor();
  PieceType getPieceType();
  FieldSet getCandidateFields(Field target, IBoard board);

  /**
   *
   * @param move the move this piece is about to make
   * @param board the whole board before the piece moves
   * @return the field on which the piece currently stands
   */
  Field calculateStartField(FieldSet list, Move move, IBoard board) throws Move.IllegalMoveException;
}
