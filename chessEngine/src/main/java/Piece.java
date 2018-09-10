import java.util.*;

public class Piece {

    public enum Value {
        whitePawn("p") ,whiteRook ("r"), whiteKnight ("n"), whiteBishop ("b"), whiteQueen ("q"), whiteKing ("k"),
        blackPawn ("P"), blackRook ("R"), blackKnight ("N"), blackBishop ("B"), blackQueen ("Q"), blackKing ("K");

        String  repStr;
        Value(String repStr){
            this.repStr = repStr;
        }
    }




}
