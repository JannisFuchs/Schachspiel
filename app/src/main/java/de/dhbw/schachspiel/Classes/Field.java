package de.dhbw.schachspiel.Classes;

public record Field(int row, int column) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Field p) {
            return p.row == row && p.column == column;
        }
        return false;
    }
}
