package models;

public class MoveInput {

    private int sourceX;
    private int sourceY;
    private int targetX;
    private int targetY;
    private boolean isResigned;

    public MoveInput(boolean isResigned) {
        this.isResigned = true;

    }

    public MoveInput(int sourceX, int sourceY, int targetX, int targetY) {
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.isResigned = false;
    }



    public int getTargetX() {
        return targetX;
    }

    public void setTargetX(int targetX) {
        this.targetX = targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public int getSourceX() {
        return sourceX;
    }

    public void setSourceX(int sourceX) {
        this.sourceX = sourceX;
    }

    public int getSourceY() {
        return sourceY;
    }

    public void setSourceY(int sourceY) {
        this.sourceY = sourceY;
    }

    public void setTargetY(int targetY) {
        this.targetY = targetY;
    }

    public boolean isResigned() {
        return isResigned;
    }

    public void setResigned(boolean resigned) {
        isResigned = resigned;
    }
}
