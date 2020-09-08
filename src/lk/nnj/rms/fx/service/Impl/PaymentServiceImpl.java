package lk.nnj.rms.fx.service.Impl;

import lk.nnj.rms.fx.db.DBConnection;
import lk.nnj.rms.fx.model.Payment;
import lk.nnj.rms.fx.service.IPaymentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PaymentServiceImpl implements IPaymentService {
    @Override
    public boolean add(Payment payment) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO paymentTable VALUES(?,?,?,?,?,?,?)");
        pstm.setObject(1,payment.getPID());
        pstm.setObject(2,payment.getAmount());
        pstm.setObject(3,payment.getDateTime());
        pstm.setObject(4,payment.getStatus());
        pstm.setObject(5,payment.getType());
        pstm.setObject(6,payment.getDescription());
        pstm.setObject(7,payment.getOID());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean update(Payment payment) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE paymentTable SET Amount=?,DateTime=?,Status=?,Type=?,Description=?,OID=? WHERE PID=?");
        pstm.setObject(1,payment.getAmount());
        pstm.setObject(2,payment.getDateTime());
        pstm.setObject(3,payment.getStatus());
        pstm.setObject(4,payment.getType());
        pstm.setObject(5,payment.getDescription());
        pstm.setObject(6,payment.getOID());
        pstm.setObject(7,payment.getPID());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean delete(int id) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM paymentTable WHERE PID=?");
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }

    @Override
    public Payment find(int pid) throws Exception {
        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM paymentTable WHERE PID=?");
        pstm.setObject(1,pid);

        ResultSet rst = pstm.executeQuery();

        if(rst.next()){
            return new Payment(
                    rst.getInt(1),
                    rst.getFloat(2),
                    rst.getTimestamp(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getInt(7)

            );
        }
        return null;
    }

    @Override
    public List<Payment> findAll() throws Exception {
        ArrayList<Payment> allPayment = new ArrayList<>();

        Connection connection = DBConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM paymentTable");

        ResultSet rst = pstm.executeQuery();

        while(rst.next()) {
            int id = rst.getInt(1);
            float amount = rst.getFloat(2);
            Timestamp datetime = rst.getTimestamp(3);
            String status = rst.getString(4);
            String type = rst.getString(5);
            String description = rst.getString(6);
            int oid = rst.getInt(7);


            Payment payment = new Payment(id,amount,datetime,status,type,description,oid);
            allPayment.add(payment);

        }
        return allPayment;
    }

}
