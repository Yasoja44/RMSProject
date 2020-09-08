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
import lk.nnj.rms.fx.service.IMachineService;
import lk.nnj.rms.fx.service.Impl.MachineServiceImpl;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageMachineController implements Initializable {

    @FXML
    private JFXTextField txt_id;

    @FXML
    private JFXTextField txt_name;

    @FXML
    private JFXTextField txt_score;

    @FXML
    private TableView<Machine> tbl_details;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_update;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXButton btn_machineplayer;

    @FXML
    private JFXButton btn_player;

    @FXML
    private JFXButton btn_back;

    @FXML
    void back(ActionEvent event) {
        Stage stage = (Stage) btn_back.getScene().getWindow();
        stage.close();
    }

    @FXML
    void goMachinePlayer(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/lk/nnj/rms/fx/view/ManagePlayerMachine.fxml"));
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
        String id,name,score;
        id = txt_id.getText();
        name = txt_name.getText();
        score = txt_score.getText();


        int scorenew = Integer.parseInt(score);
        Machine machine = new Machine(id,name,scorenew);

        IMachineService imachine = new MachineServiceImpl();

        try {
            imachine.add(machine);
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
        String id = txt_id.getText();

        IMachineService iMachineService = new MachineServiceImpl();

        try {
            iMachineService.delete(id);
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

        IMachineService iMachineService = new MachineServiceImpl();

        try {
            Machine machine = iMachineService.find(id);
            txt_id.setText(machine.getMachineID());
            txt_name.setText(machine.getGameName());
            int scorenew = machine.getScoreLimit();
            txt_score.setText(String.valueOf(scorenew));/////////////////////////////////////
            viewTable(id);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error!!! wrong");
        }
    }

    @FXML
    void update(ActionEvent event) {
        String id,name,score;
        id = txt_id.getText();
        name = txt_name.getText();
        score = txt_score.getText();

        int scorenew = Integer.parseInt(score);
        Machine machine = new Machine(id,name,scorenew);

        IMachineService iMachineService = new MachineServiceImpl();

        try {
            if(iMachineService.update(machine)){
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



    public void reset(){
        txt_id.setText("");
        txt_score.setText("");
        txt_name.setText("");
        txt_search.setText("");
    }

    public void viewTable(){
        tbl_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("machineID"));
        tbl_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("GameName"));
        tbl_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("scoreLimit"));


        IMachineService iMachineService = new MachineServiceImpl();

        try {
            List<Machine> allMachines = iMachineService.findAll();
            tbl_details.setItems(FXCollections.observableArrayList(allMachines));
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
        ArrayList<Machine> machineList = new ArrayList<>(tbl_details.getSelectionModel().getSelectedItems());

        for(Machine machine:machineList){
            txt_id.setText(machine.getMachineID());
            txt_name.setText(machine.getGameName());
            txt_score.setText(Integer.toString(machine.getScoreLimit()));

        }
    }

    public void viewTable(String id){
        tbl_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("machineID"));
        tbl_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("GameName"));
        tbl_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("scoreLimit"));


        IMachineService iMachineService = new MachineServiceImpl();

        try {
            Machine machine = iMachineService.find(id);
            tbl_details.setItems(FXCollections.observableArrayList(machine));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
