package de.dhbw.schachspiel.Classes;

public record Position(int line, int column) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position p) {
            return p.line == line && p.column == column;
        }
        return false;
    }
}
