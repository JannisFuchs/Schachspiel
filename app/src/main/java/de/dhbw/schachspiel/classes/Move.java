package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.classes.pieces.PieceFactory;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Move {
  public final Field start;
  public final Field target;
  public final AbstractPiece piece;
  public final boolean isCheck;
  public final boolean isMate;
  public final boolean isCapture;
  private enum Columns {
    A, B, C, D, E, F, G, H
  }
  /**
   * @param stringRepresentation : Move in algebraic notation
   * String should match pattern : ([RBQKN])?([a-h])?([1-8])?(x)?([a-h])([1-8]([+#])?)
   * ([RBQKN])? - Piece optional because Pawns have no indicator
   * ([a-h])? - Start column optional
   * ([1-8])? - Start row
   * (x)? -  capture
   * ([a-h]) - End column
   * ([1-8]) - End row
   * ([+#])? - check or mate
   */

  public Move(String stringRepresentation, Color c) throws IllegalMoveException {

    Matcher matcher = Pattern.compile("([RBQKN])?([a-h])?([1-8])?(x)?([a-h])([1-8])([+#])?").matcher(stringRepresentation);
    if (!matcher.matches()) {
      throw new IllegalMoveException("incorrect Notation");
    }
    String pieceLabel = matcher.group(1);
    if (pieceLabel == null)
      piece = PieceFactory.createPieceFromLetter('P', c);
    else
      piece = PieceFactory.createPieceFromLetter(pieceLabel.charAt(0), c);
    int startRow = -1;
    int startCol = -1;

    if (matcher.group(2) != null) {
      startCol = Columns.valueOf(matcher.group(2).toUpperCase()).ordinal();
    }
    if (matcher.group(3) != null) {
      startRow = 8-Integer.parseInt(matcher.group(3));
    }
    start = new Field(startRow, startCol );

    isCapture = matcher.group(4) != null;

    int endRow = 8-Integer.parseInt(matcher.group(6));
    int endCol = Columns.valueOf(matcher.group(5).toUpperCase()).ordinal();
    target = new Field(endRow, endCol );

    if (Objects.equals(matcher.group(7), "+")){
      isCheck = true;
      isMate = false;
    }
    else if (Objects.equals(matcher.group(7), "#")){
      isMate = true;
      isCheck = false;
    }
    else{
      isCheck = false;
      isMate = false;
    }
  }
  public static class IllegalMoveException extends Exception {
    public IllegalMoveException(String message) {

      super("Illegal move : "+message);
    }
  }

}
