package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public record King (Color c) implements AbstractPiece {


    @Override
    public char getSymbol() {
        return '♚';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }


    @Override
    public Field calculateStartField(Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
        Field target =  move.target;
        List<Field> candidateFields = new ArrayList<>();

        candidateFields.add(new Field(target.row(), target.column()+1));
        candidateFields.add(new Field(target.row(), target.column()-1));
        candidateFields.add(new Field(target.row()+1, target.column()));
        candidateFields.add(new Field(target.row()-1, target.column()));

        candidateFields.add(new Field(target.row()+1, target.column()+1));
        candidateFields.add(new Field(target.row()-1, target.column()+1));
        candidateFields.add(new Field(target.row()+1, target.column()-1));
        candidateFields.add(new Field(target.row()-1, target.column()-1));


        candidateFields.removeIf(Field::isInValid);
        for (Field candidateField : candidateFields) {
            int row = candidateField.row();
            int column = candidateField.column();

            if (board[row][column].equals(move.piece)) {
                return candidateField;
            }
        }
        throw new Move.IllegalMoveException("Wrong piece");
    }
}
