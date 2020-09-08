package lk.nnj.rms.fx.service.Impl;

import lk.nnj.rms.fx.db.DBConnection;
import lk.nnj.rms.fx.model.Machine;
import lk.nnj.rms.fx.service.IMachineService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MachineServiceImpl implements IMachineService {
    @Override
    public boolean add(Machine machine) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO machineTable VALUES(?,?,?)");
        pstm.setObject(1,machine.getMachineID());
        pstm.setObject(2,machine.getGameName());
        pstm.setObject(3,machine.getScoreLimit());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(Machine machine) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE machineTable SET GameName=?,scoreLimit=? WHERE machineID=?");
        pstm.setObject(1,machine.getGameName());
        pstm.setObject(2,machine.getScoreLimit());
        pstm.setObject(3,machine.getMachineID());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean delete(String machineID) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM machineTable WHERE machineID=?");
        pstm.setObject(1,machineID);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public Machine find(String machineID) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM machineTable WHERE machineID=?");
        pstm.setObject(1,machineID);

        ResultSet rst = pstm.executeQuery();

        if(rst.next()){
            return new Machine(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3)
            );
        }
        return null;
    }

    @Override
    public List<Machine> findAll() throws Exception {
        ArrayList<Machine> allMachine = new ArrayList<>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM machineTable");

        ResultSet rst = pstm.executeQuery();

        while(rst.next()){
            String id = rst.getString(1);
            String name = rst.getString(2);
            int rank = rst.getInt(3);


            Machine machine = new Machine(id,name,rank);
            allMachine.add(machine);

        }
        return allMachine;
    }
}
