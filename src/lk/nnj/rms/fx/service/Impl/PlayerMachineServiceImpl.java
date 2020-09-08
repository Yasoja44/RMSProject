package lk.nnj.rms.fx.service.Impl;

import com.mysql.cj.conf.PropertyDefinitions;
import lk.nnj.rms.fx.db.DBConnection;
import lk.nnj.rms.fx.model.Machine;
import lk.nnj.rms.fx.model.PlayerMachine;
import lk.nnj.rms.fx.service.IPlayerMachineService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PlayerMachineServiceImpl implements IPlayerMachineService {
    @Override
    public boolean add(PlayerMachine playerMachine) throws Exception {
        int scorenew2 = 0;
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO playermachineTable VALUES(?,?,?,?,?)");
        pstm.setObject(1,playerMachine.getPlayerID());
        pstm.setObject(2,playerMachine.getMachineID());
        pstm.setObject(3,playerMachine.getDateTime());
        pstm.setObject(4,playerMachine.getScore());
        //pstm.setObject(5, "Yes");

        String x = playerMachine.getMachineID();

        PreparedStatement pstm2 = connection.prepareStatement("SELECT DISTINCT m.scoreLimit FROM machineTable m,playermachineTable p WHERE ?=m.machineID ");
        pstm2.setObject(1,x);
        ResultSet score = pstm2.executeQuery();

        while(score.next()){
            String scorenew = score.getString(1);
            scorenew2 = Integer.parseInt(scorenew);
            System.out.println(score.getString(1));
        }

        if(playerMachine.getScore() >= scorenew2) {
            pstm.setObject(5, "Yes");
        }else {
            pstm.setObject(5, "No");
        }

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(PlayerMachine playerMachine) throws Exception {
        int scorenew2 = 0;

        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE playermachineTable SET datetime=?,Score=?,PriceEligibility=? WHERE MachineID=? AND PlayerID=?");
        pstm.setObject(1,playerMachine.getDateTime());
        pstm.setObject(2,playerMachine.getScore());

        pstm.setObject(4,playerMachine.getMachineID());
        pstm.setObject(5,playerMachine.getPlayerID());

        String x = playerMachine.getMachineID();

        PreparedStatement pstm2 = connection.prepareStatement("SELECT DISTINCT m.scoreLimit FROM machineTable m,playermachineTable p WHERE ?=m.machineID ");
        pstm2.setObject(1,x);
        ResultSet score = pstm2.executeQuery();

        while(score.next()){
            String scorenew = score.getString(1);
            scorenew2 = Integer.parseInt(scorenew);
            System.out.println(score.getString(1));
        }

        if(playerMachine.getScore() >= scorenew2) {
            pstm.setObject(3, "Yes");
        }else {
            pstm.setObject(3, "No");
        }

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String pid,String mid) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM playermachineTable WHERE MachineID=? AND PlayerID=?");
        pstm.setObject(1,mid);
        pstm.setObject(2,pid);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public PlayerMachine find(String pid) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM playermachineTable WHERE PlayerID=?");
        pstm.setObject(1,pid);

        ResultSet rst = pstm.executeQuery();

        if(rst.next()){
            return new PlayerMachine(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getTimestamp(3),
                    rst.getInt(4),
                    rst.getString(5)

            );
        }
        return null;
    }

    @Override
    public List<PlayerMachine> findAll() throws Exception {
        ArrayList<PlayerMachine> allPlayerMachine = new ArrayList<>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM playermachineTable");

        ResultSet rst = pstm.executeQuery();

        while(rst.next()){
            String pid = rst.getString(1);
            String mid = rst.getString(2);
            Timestamp datetime = rst.getTimestamp(3);
            int score = rst.getInt(4);
            String eligibility = rst.getString(5);


            PlayerMachine playerMachine = new PlayerMachine(pid,mid,datetime,score,eligibility);
            allPlayerMachine.add(playerMachine);

        }
        return allPlayerMachine;
    }

}
