package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.tccomputer.dto.UserDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.UserService;
import lk.ijse.tccomputer.util.Navigation;
import lk.ijse.tccomputer.util.Routes;
import lk.ijse.tccomputer.util.UserHolder;

import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtUserId;
    public JFXTextField txtPassword;
    public ImageView picHidePass;
    public JFXPasswordField txtPasswordField;
    public ImageView picShowPass;
    public JFXButton btnCancel;
    public JFXButton btnHideShow;
    public AnchorPane pane;
    public JFXButton btnSignup;
    public JFXButton btnSignIn;

    public UserService userService;

    public void initialize() {

        this.userService = ServiceFactory.getInstances().getService(ServiceType.USER);
        txtPassword.setVisible(false);
        picHidePass.setVisible(false);
    }

    public void btnCancelOnAction(ActionEvent actionEvent) {

        System.exit(0);
    }

    public void btnHideShowOnAction(ActionEvent actionEvent) {

        if (txtPasswordField.isVisible()) {
            txtPassword.setText(txtPasswordField.getText());
            txtPasswordField.setVisible(false);
            txtPassword.setVisible(true);
            picShowPass.setVisible(false);
            picHidePass.setVisible(true);
            return;
        }
        txtPasswordField.setText(txtPassword.getText());
        txtPassword.setVisible(false);
        txtPasswordField.setVisible(true);
        picHidePass.setVisible(false);
        picShowPass.setVisible(true);
    }

    public void btnSignupOnAction(ActionEvent actionEvent){

        Navigation.navigate(Routes.CreateAccount, pane);
    }

    public void btnSignInOnAction(ActionEvent actionEvent) {

        if (txtUserId.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "User ID is empty", ButtonType.OK).show();
            txtUserId.setFocusColor(Paint.valueOf("Red"));
            txtUserId.requestFocus();
            return;
        } else if (txtPassword.isVisible() && txtPassword.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password is empty", ButtonType.OK).show();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
            txtPassword.requestFocus();
            return;
        } else if (txtPasswordField.isVisible() && txtPasswordField.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Password is empty", ButtonType.OK).show();
            txtPasswordField.setFocusColor(Paint.valueOf("Red"));
            txtPasswordField.requestFocus();
            return;
        }
        UserDTO user = userService.getUserDetail(txtUserId.getText());
        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "User ID is invalid", ButtonType.OK).show();
            txtUserId.setFocusColor(Paint.valueOf("Red"));
            txtUserId.requestFocus();
            return;
        } else if (txtPassword.isVisible() && !user.getPassword().equalsIgnoreCase(txtPassword.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password is invalid", ButtonType.OK).show();
            txtPassword.setFocusColor(Paint.valueOf("Red"));
            txtPassword.requestFocus();
            return;
        } else if (txtPasswordField.isVisible() && !user.getPassword().equalsIgnoreCase(txtPasswordField.getText())) {
            new Alert(Alert.AlertType.ERROR, "Password is invalid", ButtonType.OK).show();
            txtPasswordField.setFocusColor(Paint.valueOf("Red"));
            txtPasswordField.requestFocus();
            return;
        } else {
            UserHolder.getInstance().setUserDTO(user);
            checkRole(user.getRole());
        }
    }

    private void checkRole(String role) {

        switch (role) {
            case "Admin":
                Navigation.navigate(Routes.AdminDashBoardFrom, pane);
                break;
            case "Reception":
                Navigation.navigate(Routes.ReceptionForm, pane);
                break;
            case "StockManager":
                Navigation.navigate(Routes.StockManageDashBoardForm, pane);
                break;
            case "null":
                break;
        }
    }
}
