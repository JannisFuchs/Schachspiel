package de.dhbw.schachspiel.classes;

public record Field(int row, int column) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Field p) {
            return p.row == row && p.column == column;
        }
        return false;
    }
    public boolean isInValid() {
        return row < 0 || row > 8 || column < 0 || column > 8;
    }
}
