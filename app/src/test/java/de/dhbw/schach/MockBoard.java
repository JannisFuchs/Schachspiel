package de.dhbw.schach;

import de.dhbw.schachspiel.classes.*;
import de.dhbw.schachspiel.classes.pieces.None;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.Map;

public class MockBoard implements IBoard {
    private final IPiece [][] board;


    public MockBoard(Map<Field,IPiece> importantPieces) {
        board = new IPiece[8][8];
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                board[row][column] = new None(Color.RESET);
            }
        }
        for (Map.Entry<Field, IPiece> entry : importantPieces.entrySet()) {
            board[entry.getKey().row()][entry.getKey().column()] = entry.getValue();
        }
    }


    @Override
    public void makeMove(Move move) throws Move.IllegalMoveException {

    }




    @Override
    public IPiece getPiece(Field currentField) {
        return board[currentField.row()][currentField.column()];
    }

    @Override
    public int getRowLength() {
        return board.length;
    }

    @Override
    public int getColumnLength() {
        return board[0].length;
    }

}
