package lk.nnj.rms.fx.model;

public class Player {

    private String PlayerID;
    private String PlayerName;
    private double Rank;

    public Player(String playerID, String playerName) {
        PlayerID = playerID;
        PlayerName = playerName;
        Rank = 0;
    }

    public Player(String playerID, String playerName, double rank) {
        PlayerID = playerID;
        PlayerName = playerName;
        Rank = rank;
    }

    public String getPlayerID() {
        return PlayerID;
    }

    public void setPlayerID(String playerID) {
        PlayerID = playerID;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public double getRank() {
        return Rank;
    }

    public void setRank(double rank) {
        Rank = rank;
    }

    @Override
    public String toString() {
        return "Player{" +
                "PlayerID='" + PlayerID + '\'' +
                ", PlayerName='" + PlayerName + '\'' +
                ", Rank=" + Rank +
                '}';
    }
}
