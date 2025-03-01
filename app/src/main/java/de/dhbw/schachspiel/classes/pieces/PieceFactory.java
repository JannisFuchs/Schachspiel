package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

public class PieceFactory {

	private PieceFactory(){}
    public static AbstractPiece createPieceFromField(Field p, Color c){
        AbstractPiece piece;
        if (p.row()==1||p.row()==6){
            piece = new Pawn(c);
        }
        else if (p.row()<6&&p.row()>1){
            piece = new None(c);
        }
        else if (p.column()==0||p.column()==7){
            piece = new Rook(c);
        }
        else if (p.column()==1||p.column()==6){
            piece = new Knight(c);
        }
        else if (p.column()==2||p.column()==5){
            piece = new Bishop(c);
        }
        else if (p.column()==3){
            piece = new Queen(c);
        }
        else {
            piece = new King(c);
        }

        return piece;

    }
    public static AbstractPiece createPieceFromLetter(char letter, Color c){
        AbstractPiece piece;
		piece =  switch (letter) {
			case 'R' -> new Rook(c);
			case 'N' -> new Knight(c);
			case 'B' -> new Bishop(c);
			case 'Q' -> new Queen(c);
			case 'K' -> new King(c);
			case 'P' -> new Pawn(c);
			default -> new None(c);
		};
        return piece;

    }

}
