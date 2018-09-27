package engine;

public class State {
    private String boardRep;
    private boolean isCheckMate;
    private boolean isCheck;

    public State(String boardRep, boolean isCheckMate, boolean isCheck) {
        this.boardRep = boardRep;
        this.isCheckMate = isCheckMate;
        this.isCheck = isCheck;
    }

    public State() {
        this.boardRep = "";
        this.isCheckMate = false;
        this.isCheck = false;
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
        isCheckMate = checkMate;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
