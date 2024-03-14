package models;

public abstract class Move {
    int currentPosition;
    abstract void move(Cell cell);

}
