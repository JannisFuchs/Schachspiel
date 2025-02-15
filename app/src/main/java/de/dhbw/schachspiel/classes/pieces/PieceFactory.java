package de.dhbw.schachspiel.classes.pieces;

import de.dhbw.schachspiel.classes.Color;
import de.dhbw.schachspiel.classes.Field;
import de.dhbw.schachspiel.interfaces.AbstractPiece;

public class PieceFactory {

	private PieceFactory(){}
    public static AbstractPiece createPieceFromField(Field p, Color c){
        AbstractPiece piece;
        if (p.row()==1||p.row()==6){
            piece = new Pawn();
        }
        else if (p.row()<6&&p.row()>1){
            piece = new None();
        }
        else if (p.column()==0||p.column()==7){
            piece = new Rook();
        }
        else if (p.column()==1||p.column()==6){
            piece = new Knight();
        }
        else if (p.column()==2||p.column()==5){
            piece = new Bishop();
        }
        else if (p.column()==3){
            piece = new Queen();
        }
        else {
            piece = new King();
        }
        piece.setColor(c);
        return piece;

    }
    public static AbstractPiece createPieceFromLetter(char letter, Color c){
        AbstractPiece piece;
		piece =  switch (letter) {
			case 'R' -> new Rook();
			case 'N' -> new Knight();
			case 'B' -> new Bishop();
			case 'Q' -> new Queen();
			case 'K' -> new King();
			case 'P' -> new Pawn();
			default -> new None();
		};
        piece.setColor(c);
        return piece;

    }

}
