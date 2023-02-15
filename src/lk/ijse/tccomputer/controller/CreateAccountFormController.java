package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.tccomputer.dto.UserDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.UserService;
import lk.ijse.tccomputer.util.Navigation;
import lk.ijse.tccomputer.util.Routes;

import java.io.IOException;

public class CreateAccountFormController {
    public JFXButton btnBack;
    public JFXButton btnCancel;
    public JFXButton btnSigUp;
    public JFXTextField txtUserId;
    public JFXTextField txtPassword1;
    public JFXComboBox cmbRole;
    public JFXTextField txtPassword2;
    public AnchorPane logPane;
    public Label lblError;

    public UserService userService;

    public void initialize() {
        btnSigUp.setDisable(true);
        this.userService = ServiceFactory.getInstances().getService(ServiceType.USER);
        cmbRole.getItems().addAll("Reception", "StockManager");
        txtUserId.setOnKeyReleased(event -> {
            if (userService.isExitsByPk(txtUserId.getText())) {
                btnSigUp.setDisable(true);
                lblError.setText("ID already use try different one");
                txtUserId.setFocusColor(Paint.valueOf("Red"));
                txtUserId.requestFocus();
                return;
            }
            lblError.setText("");
            btnSigUp.setDisable(false);
        });
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.Login, logPane);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void btnSigUpOnAction(ActionEvent actionEvent) {
        if (txtUserId.getText().isEmpty() || !txtUserId.getText().matches("^[a-zA-Z0-9@]{4,}$")) {
            new Alert(Alert.AlertType.ERROR, "User ID invalid or empty", ButtonType.OK).show();
            txtUserId.setFocusColor(Paint.valueOf("Red"));
            txtUserId.requestFocus();
            return;
        } else if (txtPassword1.getText().isEmpty() || !txtPassword1.getText().matches("^[a-zA-Z0-9_]{8,}$")) {
            new Alert(Alert.AlertType.ERROR, "Password invalid or empty", ButtonType.OK).show();
            txtPassword1.setFocusColor(Paint.valueOf("Red"));
            txtPassword1.requestFocus();
            return;
        } else if (txtPassword2.getText().isEmpty() || !txtPassword1.getText().equalsIgnoreCase(txtPassword2.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password text dose not match or empty", ButtonType.OK).show();
            txtPassword2.setFocusColor(Paint.valueOf("Red"));
            txtPassword2.requestFocus();
            return;
        } else if (cmbRole.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Please select your role", ButtonType.OK).show();
            cmbRole.setFocusColor(Paint.valueOf("Red"));
            cmbRole.requestFocus();
            return;
        } else {
            CreateAccount();
        }
    }

    private void CreateAccount() {
        if (userService.saveUser(new UserDTO(txtUserId.getText(), txtPassword1.getText(), cmbRole.getValue().toString())) == null) {
            new Alert(Alert.AlertType.ERROR, "Failed to save the user !", ButtonType.OK).show();
            return;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully added User !", ButtonType.OK).showAndWait();
        Navigation.navigate(Routes.Login, logPane);
    }
}
