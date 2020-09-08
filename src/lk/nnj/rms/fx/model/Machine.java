package lk.nnj.rms.fx.model;

public class Machine {

    private String MachineID;
    private String GameName;
    private int ScoreLimit;


    public Machine(String machineID, String gameName, int scoreLimit) {
        MachineID = machineID;
        GameName = gameName;
        ScoreLimit = scoreLimit;
    }

    public String getMachineID() {
        return MachineID;
    }

    public void setMachineID(String machineID) {
        MachineID = machineID;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public int getScoreLimit() {
        return ScoreLimit;
    }

    public void setScoreLimit(int scoreLimit) {
        ScoreLimit = scoreLimit;
    }

    @Override
    public String toString() {
        return "Machine{" +
                "MachineID='" + MachineID + '\'' +
                ", GameName='" + GameName + '\'' +
                ", ScoreLimit=" + ScoreLimit +
                + '\'' +
                '}';
    }
}
