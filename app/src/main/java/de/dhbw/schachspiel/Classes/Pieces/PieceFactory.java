package de.dhbw.schachspiel.Classes.Pieces;

import de.dhbw.schachspiel.Classes.Color;
import de.dhbw.schachspiel.Classes.Position;
import de.dhbw.schachspiel.Interfaces.AbstractPiece;

public class PieceFactory {
    public static AbstractPiece createPiece(Position p,Color c){
        AbstractPiece[] pieces= new AbstractPiece[]{
                new Bishop(),
                new Knight(),
                new Rook(),
                new Queen(),
                new King(),
                new Pawn()
        };
        for(AbstractPiece piece: pieces){

            if (piece.getStartingPosition().stream().anyMatch(position -> position.equals(p))){
                piece.setColor(c);
                return piece;
            }
        }
        return new None();

    }
}
