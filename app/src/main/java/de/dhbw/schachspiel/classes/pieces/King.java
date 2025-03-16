package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.IBoard;
import de.dhbw.schachspiel.interfaces.IPiece;

import java.util.ArrayList;
import java.util.List;

public record King (Color c) implements IPiece {


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
    public Field calculateStartField(Move move, IBoard board) throws Move.IllegalMoveException {
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


       List<Field> PiecesWithKing = board.getFieldsWithPiece(candidateFields,move.piece);
       if (PiecesWithKing.isEmpty()) {
           throw new Move.IllegalMoveException("Wrong piece");
       }
       if (PiecesWithKing.size()>1){
           throw new Move.IllegalMoveException("King exists somehow two times");
       }
       return PiecesWithKing.get(0);
    }
}
