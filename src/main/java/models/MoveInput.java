package models;

public class MoveInput {

    private int currentX;
    private int currentY;
    private int targetX;
    private int targetY;
    private boolean isResigned;

    public MoveInput(boolean isResigned) {
        this.isResigned = true;

    }

    public MoveInput(int currentX, int currentY, int targetX, int targetY) {
        this.currentX = currentX;
        this.currentY = currentY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.isResigned = false;
    }

    public int getCurrentX() {
        return currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
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
