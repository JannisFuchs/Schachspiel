package de.dhbw.schachspiel.classes;

public enum PieceType {
	PAWN, KNIGHT, ROOK, BISHOP, QUEEN, KING, NONE;
	public static PieceType PieceTypeFromChar(char piece) {
		return switch (piece) {
			case 'P' -> PieceType.PAWN;
			case 'K' -> PieceType.KNIGHT;
			case 'R' -> PieceType.ROOK;
			case 'B' -> PieceType.BISHOP;
			case 'Q' -> PieceType.QUEEN;
			default -> PieceType.NONE;
		};

	}
}
