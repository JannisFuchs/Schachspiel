package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public class Pawn implements AbstractPiece {
    private Color color;


    @Override
    public char getSymbol() {
        return '♟';
    }



    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Field calculateStartField(Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
        /*
         * 4 possible start squares from each color
         * 2 from capture
         * 2 from normal moving (pawn can move two square at a time)
         */
        Field endField = move.end;
        List<Field> candidateFields = new ArrayList<>();
        if (move.piece.getColor() == Color.WHITE) {

            candidateFields.add(new Field(endField.row()+1,endField.column()) ); //Pawn moves one square forward
            candidateFields.add(new Field(endField.row()+2,endField.column())); //Pawn starts with two squares forward
            candidateFields.add(new Field(endField.row()+1,endField.column()+1)); //Pawn captures right
            candidateFields.add(new Field(endField.row()+1,endField.column()-1)); //Pawn captures left
        }
        else { //Pawn is black
            candidateFields.add(new Field(endField.row()-1,endField.column())); //Pawn moves one square forward
            candidateFields.add(new Field(endField.row()-2,endField.column())); //Pawn starts with two squares forward
            candidateFields.add(new Field(endField.row()-1,endField.column()+1)); //Pawn captures left
            candidateFields.add(new Field(endField.row()-1,endField.column()-1));//Pawn captures right
        }
        if (move.isCapture){
            candidateFields.remove(0);
            candidateFields.remove(1);
        }
        else{
            candidateFields.remove(2);
            candidateFields.remove(2);
        }
        AbstractPiece firstCandidate = board[candidateFields.get(0).row()][candidateFields.get(0).column()];
        AbstractPiece secondCandidate = board[candidateFields.get(1).row()][candidateFields.get(1).column()];
        if (!(firstCandidate instanceof Pawn) && !(secondCandidate instanceof Pawn)){
            throw new Move.IllegalMoveException("Wrong piece");
        }
        if (firstCandidate instanceof Pawn && !(secondCandidate instanceof Pawn)) {
            return candidateFields.get(0);
        }
        else if (!(firstCandidate instanceof Pawn)) {
            return candidateFields.get(1);
        }
        else{ //both Fields have Pawns
            int correctColumn= move.start.column();
            if (correctColumn== -1) throw new Move.IllegalMoveException("Wrong piece");
            if (candidateFields.get(0).column() == candidateFields.get(1).column()) throw new Move.IllegalMoveException("Wrong piece");
            if (candidateFields.get(0).column() == )
        }
}
