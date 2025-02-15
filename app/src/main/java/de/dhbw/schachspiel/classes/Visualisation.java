package de.dhbw.schachspiel.classes;

import de.dhbw.schachspiel.interfaces.AbstractPiece;

public class Visualisation {
    private Visualisation() {}
    public static void drawBoard(AbstractPiece[][] board) {
        Color currentFieldColor = Color.BLACK;
        for (int i = 0; i < board.length; i++) {
            currentFieldColor = currentFieldColor == Color.WHITE ? Color.BLACK : Color.WHITE;
            AbstractPiece[]row = board[i];
            for (AbstractPiece piece: row) {
                //if the piece is None the color has to be the background
                Color currentPieceColor = piece.getColor() == Color.RESET ? currentFieldColor : piece.getColor();
                System.out.print(currentFieldColor.backGround + currentPieceColor.foreGround+ piece.getSymbol());
                currentFieldColor = currentFieldColor == Color.WHITE ? Color.BLACK : Color.WHITE;
            }
            //rest of the line is black
            System.out.print(Color.RESET.foreGround + Color.RESET.backGround);
            System.out.println(board.length-i);
        }
        System.out.println("ABCDEFGH");
    }
}
