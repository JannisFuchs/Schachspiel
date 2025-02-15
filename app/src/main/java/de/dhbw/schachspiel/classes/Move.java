package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move {
  public final Field start;
  public final Field end;
  public final AbstractPiece piece;
  private enum Columns {
    A, B, C, D, E, F, G, H
  }
  /**
   * @param stringRepresentation : Move in algebraic notation
   * String should match pattern : ([RBQKNP])([a-h])([1-8])([a-h])([1-8])
   * ([RBQKNP]) - Piece
   * ([a-h]) - Start column
   * ([1-8]) - Start row
   * ([a-h]) - End column
   * ([1-8]) - End row
   */

  public Move(String stringRepresentation, Color c) throws IllegalMoveException {

    Matcher matcher = Pattern.compile("([RBQKNP])([a-h])([1-8])([a-h])([1-8])").matcher(stringRepresentation);
    if (!matcher.matches()) {
      throw new IllegalMoveException("Illegal move incorrect Notation");
    }
    char pieceLabel = matcher.group(1).charAt(0);
    piece = PieceFactory.createPieceFromLetter(pieceLabel, c);
    int startRow = 8-Integer.parseInt(matcher.group(3));
    int startCol = Columns.valueOf(matcher.group(2).toUpperCase()).ordinal();
    start = new Field(startRow, startCol );
    int endRow = 8-Integer.parseInt(matcher.group(5));
    int endCol = Columns.valueOf(matcher.group(4).toUpperCase()).ordinal();
    end = new Field(endRow, endCol );

  }


  public static class IllegalMoveException extends Exception {
    public IllegalMoveException(String message) {
      super(message);
    }
  }

}
