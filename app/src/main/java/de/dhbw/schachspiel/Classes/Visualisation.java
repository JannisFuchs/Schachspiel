package de.dhbw.schachspiel.Classes;

import de.dhbw.schachspiel.Interfaces.AbstractPiece;

public class Visualisation {
    public static void drawBoard(AbstractPiece[][] board) {
        Color currentFieldColor = Color.WHITE;
        for (AbstractPiece[] line : board) {
            currentFieldColor = currentFieldColor == Color.WHITE ? Color.BLACK : Color.WHITE;
            for (AbstractPiece piece: line) {
                Color currentPieceColor = piece.getColor() == Color.RESET ? currentFieldColor : piece.getColor();
                System.out.print(currentFieldColor.backGround + currentPieceColor.foreGround+ piece.getSymbol());
                currentFieldColor = currentFieldColor == Color.WHITE ? Color.BLACK : Color.WHITE;
            }
            System.out.println(Color.RESET.foreGround + Color.RESET.backGround);
        }
    }
}
