package engine;

public class State {
    private String boardRep;
    private boolean isCheckMate;
    private boolean isCheck;
    private boolean isPromotion;

    public State(String boardRep, boolean isCheckMate, boolean isCheck, boolean isPromotion) {
        this.boardRep = boardRep;
        this.isCheckMate = isCheckMate;
        this.isCheck = isCheck;
        this.isPromotion = isPromotion;
    }

    public State() {
        this.boardRep = "";
        this.isCheckMate = false;
        this.isCheck = false;
        this.isPromotion = false;
    }

    public String getBoardRep() {
        return boardRep;
    }

    public void setBoardRep(String boardRep) {
        this.boardRep = boardRep;
    }

    public boolean isCheckMate() {
        return isCheckMate;
    }

    public void setCheckMate(boolean checkMate) {
        this.isCheckMate = checkMate;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        this.isCheck = check;
    }

    public boolean getPromotion() {
        return isPromotion;
    }

    public void setPromotion(boolean promotion) {
        this.isPromotion = promotion;
    }

    public boolean isPromotion() {
        return isPromotion;
    }
}
