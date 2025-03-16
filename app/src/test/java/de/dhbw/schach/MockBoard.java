package de.dhbw.schach;

import de.dhbw.schachspiel.classes.Board;
import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.List;

public class MockBoard implements IBoard {

    @Override
    public IPiece getPiece(Field currentField) {
        return null;
    }

    @Override
    public int getRowLength() {
        return 0;
    }

    @Override
    public int getColumnLength() {
        return 0;
    }

    @Override
    public boolean isOccupiedByColor(Color color, Field target) {
        return false;
    }

    @Override
    public void makeMove(Move move) throws Move.IllegalMoveException {

    }

    @Override
    public List<Field> getFieldsWithPiece(List<Field> candidateFields, IPiece piece) {
        return List.of();
    }
}
