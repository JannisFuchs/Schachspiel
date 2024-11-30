package de.dhbw.schachspiel.Classes.Pieces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Field;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

public class PieceFactory {
    private static final AbstractPiece[] pieces= new AbstractPiece[]{
                new Bishop(),
                new Knight(),
                new Rook(),
                new Queen(),
                new King(),
                new Pawn()
        };

    public static AbstractPiece createPieceFromField(Field p, Color c){

        for(AbstractPiece piece: pieces){

            if (piece.getStartingPosition().stream().anyMatch(position -> position.equals(p))){
                piece.setColor(c);
                return piece;
            }
        }
        return new None();

    }
    public static AbstractPiece createPieceFromSymbol(String symbol, Color c){
       for(AbstractPiece piece: pieces){
           if (piece.getSymbol().equals(symbol)){
               piece.setColor(c);
               return piece;
           }
       }
       return new None();
    }

}
