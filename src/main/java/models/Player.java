package models;

public class Player {

    private String name;
    private Color color;
    private King king;
    private Queen queen;
    private Knight[] knights;
    private Bishop[] bishops;
    private Rook[] rooks;
    private Pawn[] pawns;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
        initializePieces();
    }

    private void initializePieces() {
        this.king = new King(color);
        this.queen= new Queen(color);
        this.rooks = new Rook[2];
        this.bishops = new Bishop[2];
        this.knights = new Knight[2];
        this.pawns = new Pawn[8];

        for(int i=0;i<2;i++){
            rooks[i] = new Rook(color);
        }

        for(int i=0;i<2;i++){
            bishops[i] = new Bishop(color);
        }

        for(int i=0;i<2;i++){
            knights[i] = new Knight(color);
        }

        for(int i=0;i<8;i++){
            pawns[i] = new Pawn(color);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public models.King getKing() {
        return king;
    }

    public void setKing(models.King king) {
        this.king = king;
    }

    public models.Queen getQueen() {
        return queen;
    }

    public void setQueen(models.Queen queen) {
        this.queen = queen;
    }

    public Knight[] getKnights() {
        return knights;
    }

    public void setKnights(Knight[] knights) {
        this.knights = knights;
    }

    public Bishop[] getBishops() {
        return bishops;
    }

    public void setBishops(Bishop[] bishops) {
        this.bishops = bishops;
    }

    public Rook[] getRooks() {
        return rooks;
    }

    public void setRooks(Rook[] rooks) {
        this.rooks = rooks;
    }

    public Pawn[] getPawns() {
        return pawns;
    }

    public void setPawns(Pawn[] pawns) {
        this.pawns = pawns;
    }

}
