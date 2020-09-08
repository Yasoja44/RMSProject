package lk.nnj.rms.fx.view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.nnj.rms.fx.model.Payment;
import lk.nnj.rms.fx.service.IPaymentService;
import lk.nnj.rms.fx.service.Impl.PaymentServiceImpl;

import javax.swing.*;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagePaymentController implements Initializable {

    @FXML
    private JFXButton btn_reset;

    @FXML
    private JFXTextField txt_pid;

    @FXML
    private TableView<Payment> tbl_details;

    @FXML
    private JFXTextField txt_status;

    @FXML
    private JFXTextField txt_datetime;

    @FXML
    private JFXTextField txt_type;

    @FXML
    private JFXTextField txt_amount;

    @FXML
    private JFXTextField txt_description;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_update;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private JFXTextField txt_oid;

    @FXML
    private JFXButton btn_back;

    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) btn_back.getScene().getWindow();
        stage.close();
    }

    @FXML
    void resetButton(ActionEvent event) {
        reset();
        viewTable();
    }

    @FXML
    void add(ActionEvent event) {
        String pid, amount, datetime, status, type, description, oid;

        pid = txt_pid.getText();
        amount = txt_amount.getText();
        datetime = txt_datetime.getText();
        status = txt_status.getText();
        type = txt_type.getText();
        description = txt_description.getText();
        oid = txt_oid.getText();

        int pidnew = Integer.parseInt(pid);
        int oidnew = Integer.parseInt(oid);
        float amountnew = Float.parseFloat(amount);
        Timestamp datetimenew = Timestamp.valueOf(datetime);
        Payment payment = new Payment(pidnew, amountnew, datetimenew, status, type, description, oidnew);

        IPaymentService ipayment = new PaymentServiceImpl();

        try {
            ipayment.add(payment);
            JOptionPane.showMessageDialog(null, "Success, added");
            viewTable();
            reset();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Error!!! can not add");
            e.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        int pid = Integer.parseInt(txt_pid.getText());

        IPaymentService iPaymentService = new PaymentServiceImpl();

        try {
            iPaymentService.delete(pid);
            JOptionPane.showMessageDialog(null, "Success, deleted");
            viewTable();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error!!! can not delete");
            e.printStackTrace();
        }
    }

    @FXML
    void search(ActionEvent event) {
        String pid = txt_search.getText();
        int pidnew = Integer.parseInt(pid);

        IPaymentService iPaymentService = new PaymentServiceImpl();

        try {
            Payment payment = iPaymentService.find(Integer.parseInt(pid));
            txt_pid.setText(String.valueOf(payment.getPID()));
            txt_amount.setText(String.valueOf(payment.getAmount()));
            txt_datetime.setText(String.valueOf(payment.getDateTime()));
            txt_status.setText(payment.getStatus());
            txt_type.setText(payment.getType());
            txt_description.setText(payment.getDescription());
            txt_oid.setText(String.valueOf(payment.getOID()));
            viewTable(pidnew);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error!!! wrong");
        }
    }

    @FXML
    void update(ActionEvent event) {
        String pid, amount, datetime, status, type, description, oid;
        pid = txt_pid.getText();
        int pidnew = Integer.parseInt(pid);
        amount = txt_amount.getText();
        datetime = txt_datetime.getText();
        status = txt_datetime.getText();
        type = txt_datetime.getText();
        description = txt_datetime.getText();
        oid = txt_oid.getText();
        int oidnew = Integer.parseInt(oid);

        float amountnew = Float.parseFloat(amount);
        Timestamp datetimenew = Timestamp.valueOf(datetime);
        Payment payment = new Payment(pidnew, amountnew, datetimenew, status, type, description, oidnew);

        IPaymentService iPaymentService = new PaymentServiceImpl();

        try {
            if (iPaymentService.update(payment)) {
                JOptionPane.showMessageDialog(null, "Updated, added");
                reset();
                viewTable();
            } else {
                JOptionPane.showMessageDialog(null, "Error!!! can not update");
            }
        } catch (Exception e) {


            e.printStackTrace();
        }
    }


    public void reset() {
        txt_pid.setText("");
        txt_amount.setText("");
        txt_datetime.setText("");
        txt_search.setText("");
        txt_status.setText("");
        txt_type.setText("");
        txt_description.setText("");
        txt_oid.setText("");
    }

    public void viewTable() {
        tbl_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("PID"));
        tbl_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Amount"));
        tbl_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("DateTime"));
        tbl_details.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Status"));
        tbl_details.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Type"));
        tbl_details.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("Description"));
        tbl_details.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("OID"));


        IPaymentService iPaymentService = new PaymentServiceImpl();

        try {
            List<Payment> allPayments = iPaymentService.findAll();
            tbl_details.setItems(FXCollections.observableArrayList(allPayments));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewTable();
    }

    @FXML
    void viewSelect(MouseEvent event) {
        ArrayList<Payment> paymentList = new ArrayList<>(tbl_details.getSelectionModel().getSelectedItems());

        for (Payment payment : paymentList) {
            txt_pid.setText(String.valueOf(payment.getPID()));
            txt_amount.setText(Float.toString(payment.getAmount()));
            txt_datetime.setText(String.valueOf(payment.getDateTime()));
            txt_status.setText(payment.getStatus());
            txt_type.setText(payment.getType());
            txt_description.setText(payment.getDescription());
            txt_oid.setText(String.valueOf(payment.getOID()));

        }
    }

    public void viewTable(int id) {
        tbl_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("PID"));
        tbl_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("Amount"));
        tbl_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("DateTime"));
        tbl_details.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Status"));
        tbl_details.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("Type"));
        tbl_details.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("Description"));
        tbl_details.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("OID"));


        IPaymentService iPaymentService = new PaymentServiceImpl();

        try {
            Payment payment = iPaymentService.find(id);
            tbl_details.setItems(FXCollections.observableArrayList(payment));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}