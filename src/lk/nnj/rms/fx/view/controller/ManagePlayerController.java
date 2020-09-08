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
import lk.nnj.rms.fx.model.Player;
import lk.nnj.rms.fx.model.PlayerMachine;
import lk.nnj.rms.fx.service.IPlayerMachineService;
import lk.nnj.rms.fx.service.IPlayerService;
import lk.nnj.rms.fx.service.Impl.PlayerMachineServiceImpl;
import lk.nnj.rms.fx.service.Impl.PlayerServiceImpl;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagePlayerController implements Initializable {
    @FXML
    private JFXTextField txt_search;

    @FXML
    private JFXButton btn_search;

    @FXML
    private JFXTextField txt_playerID;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXTextField txt_name;

    @FXML
    private TableView<Player> tbl_details;

    @FXML
    private JFXButton btn_update;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private JFXButton btn_machineplayer;

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
    void resetButton(ActionEvent event) {
        reset();
        viewTable();
    }


    @FXML
    void add(ActionEvent event) {
        String pid,name,rank;
        pid = txt_playerID.getText();
        name = txt_name.getText();

        Player player = new Player(pid,name);

        IPlayerService iplayer = new PlayerServiceImpl();

        try {
            iplayer.add(player);
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

        IPlayerService iPlayerService = new PlayerServiceImpl();

        try {
            iPlayerService.delete(pid);
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

        IPlayerService iPlayerService = new PlayerServiceImpl();

        try {
            Player palyer = iPlayerService.find(id);
            txt_playerID.setText(palyer.getPlayerID());
            txt_name.setText(palyer.getPlayerName());

            viewTable(id);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error!!! wrong");
        }
    }

    @FXML
    void update(ActionEvent event) {
        String pid,name,rank;
        pid = txt_playerID.getText();
        name = txt_name.getText();


        Player player = new Player(pid,name);

        IPlayerService iPlayerService = new PlayerServiceImpl();

        try {
            if(iPlayerService.update(player)){
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
        ArrayList<Player> playerList = new ArrayList<>(tbl_details.getSelectionModel().getSelectedItems());

        for(Player player:playerList){
            txt_playerID.setText(player.getPlayerID());
            txt_name.setText(player.getPlayerName());

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewTable();
    }

    public void reset(){
        txt_playerID.setText("");
        txt_name.setText("");
        txt_search.setText("");
    }

    public void viewTable(){
        tbl_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Rank"));
        tbl_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("PlayerName"));
        tbl_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("PlayerID"));



        IPlayerService iPlayerService = new PlayerServiceImpl();

        try {
            List<Player> allPlayer = iPlayerService.findAll();
            tbl_details.setItems(FXCollections.observableArrayList(allPlayer));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void viewTable(String id){
        tbl_details.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("PlayerID"));
        tbl_details.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("PlayerName"));
        tbl_details.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("Rank"));

        IPlayerService iPlayerService = new PlayerServiceImpl();

        try {
            Player player = iPlayerService.find(id);
            tbl_details.setItems(FXCollections.observableArrayList(player));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
