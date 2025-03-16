package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

public class Visualisation {
    private Visualisation() {}
    public static void drawBoard(IBoard board) {
        Color currentFieldColor = Color.BLACK;
        for (int row = 0; row < board.getRowLength(); row++) {
            currentFieldColor = currentFieldColor == Color.WHITE ? Color.BLACK : Color.WHITE;
            for (int column = 0; column < board.getColumnLength(); column++) {
                //if the piece is None the color has to be the background
                Field currentField = new Field(row, column);
                IPiece currentPiece = board.getPiece(currentField);
                Color currentPieceColor = currentPiece.getColor();
                if (currentPiece.getPieceType() == PieceType.NONE){
                    currentPieceColor = currentFieldColor;
                }
                System.out.print(currentFieldColor.backGround + currentPieceColor.foreGround+ currentPiece.getSymbol());
                currentFieldColor = currentFieldColor == Color.WHITE ? Color.BLACK : Color.WHITE;
            }
            //rest of the line is black
            System.out.print(Color.RESET.foreGround + Color.RESET.backGround);
            System.out.println(board.getRowLength()-row);
        }
        System.out.println("ABCDEFGH");
    }
}
