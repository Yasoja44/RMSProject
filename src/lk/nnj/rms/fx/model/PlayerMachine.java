package lk.nnj.rms.fx.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PlayerMachine {

    private String PlayerID;
    private String MachineID;
    private Timestamp DateTime;
    private int Score;
    private String PriceEligibilty;

    public PlayerMachine(String playerID, String machineID, Timestamp dateTime, int score) {
        PlayerID = playerID;
        MachineID = machineID;
        DateTime = dateTime;
        Score = score;
        PriceEligibilty = "";
    }

    public PlayerMachine(String playerID, String machineID, Timestamp dateTime, int score, String priceEligibilty) {
        PlayerID = playerID;
        MachineID = machineID;
        DateTime = dateTime;
        Score = score;
        PriceEligibilty = priceEligibilty;
    }

    public String getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(String playerID) {
        PlayerID = playerID;
    }

    public String getMachineID() {
        return MachineID;
    }

    public void setMachineID(String machineID) {
        MachineID = machineID;
    }

    public Timestamp getDateTime() {
        return DateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        DateTime = dateTime;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getPriceEligibilty() {
        return PriceEligibilty;
    }

    public void setPriceEligibilty(String priceEligibilty) {
        PriceEligibilty = priceEligibilty;
    }

    @Override
    public String toString() {
        return "PlayerMachine{" +
                "PlayerID='" + PlayerID + '\'' +
                ", MachineID='" + MachineID + '\'' +
                ", DateTime=" + DateTime +
                ", Score=" + Score +
                ", PriceEligibilty='" + PriceEligibilty + '\'' +
                '}';
    }
}
