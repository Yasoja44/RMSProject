package lk.nnj.rms.fx.view.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.nnj.rms.fx.model.Machine;
import lk.nnj.rms.fx.model.PlayerMachine;
import lk.nnj.rms.fx.service.IMachineService;
import lk.nnj.rms.fx.service.IPlayerMachineService;
import lk.nnj.rms.fx.service.Impl.MachineServiceImpl;
import lk.nnj.rms.fx.service.Impl.PlayerMachineServiceImpl;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagePlayerMachineController implements Initializable {
    @FXML
    private JFXTextField txt_playerID;

    @FXML
    private JFXTextField txt_machineID;

    @FXML
    private JFXTextField txt_datetime;

    @FXML
    private TableView<PlayerMachine> tbl_details;

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
    private JFXTextField txt_score;

    @FXML
    private JFXTextField txt_price;

    @FXML
    private JFXButton btn_player;

    @FXML
    private JFXButton btn_machine;

    @FXML
    private JFXButton btn_back;

    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) btn_back.getScene().getWindow();
        stage.close();
    }

    @FXML
    void goMachine(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lk/nnj/rms/fx/view/ManageMachine.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goPlayer(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lk/nnj/rms/fx/view/ManagePlayer.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("ABC");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void resetButton(ActionEvent event) {
        reset();
        viewTable();
    }


    @FXML
    void add(ActionEvent event) {
        String pid,mid,datetime,score,price;
        pid = txt_playerID.getText();
        mid = txt_machineID.getText();
        score = txt_score.getText();
        datetime =txt_datetime.getText();


        int scorenew = Integer.parseInt(score);
        Timestamp datetimenew = Timestamp.valueOf(datetime);

        PlayerMachine playerMachine = new PlayerMachine(pid,mid,datetimenew,scorenew);

        IPlayerMachineService iplayermachine = new PlayerMachineServiceImpl();

        try {
            iplayermachine.add(playerMachine);
            JOptionPane.showMessageDialog(null,"Success, added");
            viewTable();
            reset();
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null,"Error!!! can not add");
            e.printStackTrace();
        }
    }

    @FXML
    void delete(ActionEvent event) {
        String pid = txt_playerID.getText();
        String mid = txt_machineID.getText();

        IPlayerMachineService iPlayerMachineService = new PlayerMachineServiceImpl();

        try {
            iPlayerMachineService.delete(pid,mid);
            JOptionPane.showMessageDialog(null,"Success, deleted");
            viewTable();
            reset();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error!!! can not delete");
            e.printStackTrace();
        }
    }

    @FXML
    void search(ActionEvent event) {
        String id = txt_search.getText();

        IPlayerMachineService iPlayerMachineService = new PlayerMachineServiceImpl();

        try {
            PlayerMachine palyermachine = iPlayerMachineService.find(id);
            txt_playerID.setText(palyermachine.getPlayerID());
            txt_machineID.setText(palyermachine.getMachineID());
            txt_datetime.setText(String.valueOf(palyermachine.getDateTime()));
            txt_score.setText(String.valueOf(palyermachine.getScore()));


            viewTable(id);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error!!! wrong");
        }
    }

    @FXML
    void update(ActionEvent event) {
        String pid,mid,datetime,score,price;
        pid = txt_playerID.getText();
        mid = txt_machineID.getText();
        datetime = txt_datetime.getText();
        score = txt_score.getText();
        //price = txt_price.getText();

        int scorenew = Integer.parseInt(score);
        Timestamp datetimenew = Timestamp.valueOf(datetime);
        PlayerMachine playerMachine = new PlayerMachine(pid,mid,datetimenew,scorenew);

        IPlayerMachineService iPlayerMachineService = new PlayerMachineServiceImpl();

        try {
            if(iPlayerMachineService.update(playerMachine)){
                JOptionPane.showMessageDialog(null,"Updated, added");
                reset();
                viewTable();
            }else{
                JOptionPane.showMessageDialog(null,"Error!!! can not update");
            }
        } catch (Exception e) {


            e.printStackTrace();
        }
    }

    @FXML
    void viewSelect(MouseEvent event) {
        ArrayList<PlayerMachine> playermachineList = new ArrayList<>(tbl_details.getSelectionModel().getSelectedItems());

        for(PlayerMachine playerMachine:playermachineList){
            txt_playerID.setText(playerMachine.getPlayerID());
            txt_machineID.setText(playerMachine.getMachineID());
            txt_score.setText(Integer.toString(playerMachine.getScore()));
            txt_datetime.setText(String.valueOf(playerMachine.getDateTime()));



        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewTable();
    }

    public void reset(){
        txt_playerID.setText("");
        txt_machineID.setText("");
        txt_score.setText("");
        txt_datetime.setText("");

        txt_search.setText("");
    }

    public void viewTable(){
        tbl_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("PlayerID"));
        tbl_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("MachineID"));
        tbl_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("DateTime"));
        tbl_details.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Score"));
        tbl_details.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("PriceEligibilty"));


        IPlayerMachineService iPlayerMachineService = new PlayerMachineServiceImpl();

        try {
            List<PlayerMachine> allPlayerMachines = iPlayerMachineService.findAll();
            tbl_details.setItems(FXCollections.observableArrayList(allPlayerMachines));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void viewTable(String id){
        tbl_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("PlayerID"));
        tbl_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("MachineID"));
        tbl_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("DateTime"));
        tbl_details.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("Score"));
        tbl_details.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("PriceEligibilty"));


        IPlayerMachineService iPlayerMachineService = new PlayerMachineServiceImpl();

        try {
            PlayerMachine playerMachine = iPlayerMachineService.find(id);
            tbl_details.setItems(FXCollections.observableArrayList(playerMachine));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
