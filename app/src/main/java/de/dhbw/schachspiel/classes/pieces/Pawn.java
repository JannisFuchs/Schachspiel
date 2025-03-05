package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.classes.Move;
import de.dhbw.schachspiel.classes.PieceType;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

import java.util.ArrayList;
import java.util.List;

public record Pawn (Color c) implements AbstractPiece {

    @Override
    public char getSymbol() {
        return '♟';
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }


    @Override
    public Field calculateStartField(Move move, AbstractPiece[][] board) throws Move.IllegalMoveException {
        /*
         * 4 possible start squares from each color
         * 2 from capture
         * 2 from normal moving (pawn can move two square at a time)
         */
        Field endField = move.target;
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
            candidateFields.remove(0);
            candidateFields.removeIf(Field::isInValid);
            if (candidateFields.size()==2)
            return calculateCapture(move,board,candidateFields.get(0),candidateFields.get(1));
            else  if (candidateFields.size()==1)
                return calculateCapture(move,board,candidateFields.get(0));
            else
                throw new Move.IllegalMoveException("No candidate fields");
        }
        else{
            candidateFields.remove(2);
            candidateFields.remove(2);
            candidateFields.removeIf(Field::isInValid);
            return calculateNormalMove(move, board,candidateFields.get(0),candidateFields.get(1));
        }
}
private Field calculateCapture(Move move,AbstractPiece[][] board, Field candidateField) throws Move.IllegalMoveException {
    Field endField = move.target;
    AbstractPiece target = board[endField.row()][endField.column()];
    if(target instanceof None) throw new Move.IllegalMoveException("No piece to capture");
    AbstractPiece c = board[candidateField.row()][candidateField.column()];
    if(!(c instanceof Pawn)) throw new Move.IllegalMoveException("Wrong piece");
    if (candidateField.column()==endField.column()&&c.getColor()==move.piece.getColor()){
        return candidateField;
    }
    else{
        throw new Move.IllegalMoveException("Wrong piece");
    }
}
private Field calculateCapture(Move move, AbstractPiece[][] board,Field candidateField1,Field candidateField2) throws Move.IllegalMoveException {
    Field endField = move.target;
    int correctColumn = move.start.column();
    AbstractPiece enemyPiece = board[endField.row()][endField.column()];
    if(enemyPiece instanceof None) throw new Move.IllegalMoveException("No piece to capture");
    AbstractPiece c1 = board[candidateField1.row()][candidateField1.column()];
    AbstractPiece c2 = board[candidateField2.row()][candidateField2.column()];
    //the if-statements below always result in a throw or an return so the else is not needed
    if(!(c1 instanceof Pawn) && !(c2 instanceof Pawn)) throw new Move.IllegalMoveException("Wrong piece");
    if (c1 instanceof Pawn && !(c2 instanceof Pawn)) {
        if (move.piece.getColor()==c1.getColor()){
            return candidateField1;
        }
        else{
            throw new Move.IllegalMoveException("Wrong piece");
        }
    }
    if (!(c1 instanceof Pawn)) {
        if (move.piece.getColor()==c2.getColor()){
            return candidateField2;
        }
        else{
            throw new Move.IllegalMoveException("Wrong piece");
        }
    }
    if (candidateField1.column()==correctColumn&&c1.getColor()==move.piece.getColor()){
        return candidateField1;
    }
    else if (candidateField2.column()==correctColumn&&c2.getColor()==move.piece.getColor()){
        return candidateField2;
    }
    else{
        throw new Move.IllegalMoveException("Wrong piece");
    }
}

private Field calculateNormalMove(Move move, AbstractPiece[][] board, Field movesOneSquare, Field movesTwoSquares) throws Move.IllegalMoveException {
    Field endField = move.target;
    AbstractPiece c1 = board[movesOneSquare.row()][movesOneSquare.column()];
    AbstractPiece c2 = board[ movesTwoSquares.row()][ movesTwoSquares.column()];
    if(!(c1 instanceof Pawn) && !(c2 instanceof Pawn)) throw new Move.IllegalMoveException("Wrong piece"); //violates DRY criteria might be refactored
    AbstractPiece target = board[endField.row()][endField.column()];
    if (!(target instanceof None)) throw new Move.IllegalMoveException("Field is occupied");
    if(c1 instanceof Pawn && c1.getColor() == move.piece.getColor())
        return movesOneSquare;
    if (!(c1 instanceof None)) throw new Move.IllegalMoveException("next Field is occupied");
    if (c2.getColor() != move.piece.getColor()) throw new Move.IllegalMoveException("Wrong piece");
    if(c2.getColor()==Color.WHITE && movesTwoSquares.row()==6) return movesTwoSquares;
    if(c2.getColor()==Color.BLACK && movesTwoSquares.row()==1) return movesTwoSquares;
    throw new Move.IllegalMoveException("Pawn can only move two squares from starting position");
    }

}
