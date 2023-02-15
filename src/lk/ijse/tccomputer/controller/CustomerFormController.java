package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import lk.ijse.tccomputer.dto.CustomerDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.CustomerService;
import lk.ijse.tccomputer.view.tm.CustomerTm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerFormController {
    public TableView tblCustomer;
    public TableColumn clmCusId;
    public TableColumn clmName;
    public TableColumn clmAddress;
    public TableColumn clmContact;
    public JFXTextField txtSearch;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXButton btnAddUpdate;
    public Label lblCusId;
    public TableColumn clmOption;
    public Pane cusIdPane;

    public CustomerService customerService;

    public void initialize() {
        this.customerService = ServiceFactory.getInstances().getService(ServiceType.CUSTOMER);
        initializeTableData();
        setTableData();
        cusIdPane.setVisible(false);
    }

    private void setTableData() {
        List<CustomerTm> tmList = customerService.findAllCustomer().stream().map(c -> new CustomerTm(c.getId(), c.getName(), c.getAddress(), c.getContact(), getBtn(c))).collect(Collectors.toList());
        tblCustomer.setItems(FXCollections.observableArrayList(tmList));
    }

    private JFXButton getBtn(CustomerDTO c) {
        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #e55039; -fx-background-radius: 20; -fx-text-fill: #ffffff;");
        btn.setOnAction(event -> {
            deleteCustomer(c.getId());
        });
        return btn;
    }

    private void initializeTableData() {
        clmCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        clmAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("cusContact"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setText((CustomerTm) newValue);
                btnAddUpdate.setText("Update");
                btnAddUpdate.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 20");
                cusIdPane.setVisible(false);
            }
        });
    }

    private void setText(CustomerTm customerTm) {
        lblCusId.setText(customerTm.getCusId());
        txtName.setText(customerTm.getCusName());
        txtAddress.setText(customerTm.getCusAddress());
        txtContact.setText(customerTm.getCusContact());
    }

    private void clear() {
        lblCusId.setText("");
        txtName.clear();
        txtAddress.clear();
    }

    public void txtSearchKeyReleaseEvent(KeyEvent keyEvent) {
        searchCustomer();
    }

    private void searchCustomer() {
        List<CustomerTm> tmList = customerService.searchCustomerByText(txtSearch.getText()).stream().map(c -> new CustomerTm(c.getId(), c.getName(), c.getAddress(), c.getContact(), getBtn(c))).collect(Collectors.toList());
        tblCustomer.setItems(FXCollections.observableArrayList(tmList));

    }

    public void tbnAddUpdateOnAction(ActionEvent actionEvent) {
        if (txtName.getText().isEmpty() || !txtName.getText().matches("([\\w ]{1,})")) {
            new Alert(Alert.AlertType.ERROR, "Name text invalid or empty",ButtonType.OK).show();
            txtName.setFocusColor(Paint.valueOf("Red"));
            txtName.requestFocus();
            return;
        }else if (txtAddress.getText().isEmpty() || !txtAddress.getText().matches("^[0-9a-zA-Z]{2,}")) {
            new Alert(Alert.AlertType.ERROR, "Address text invalid or empty",ButtonType.OK).show();
            txtAddress.setFocusColor(Paint.valueOf("Red"));
            txtAddress.requestFocus();
            return;
        }else if (txtContact.getText().isEmpty() || !txtContact.getText().matches("^(075|077|071|074|078|076|070|072)([0-9]{7})$")) {
            new Alert(Alert.AlertType.ERROR, "Contact text invalid or empty",ButtonType.OK).show();
            txtContact.setFocusColor(Paint.valueOf("Red"));
            txtContact.requestFocus();
            return;
        }else if (btnAddUpdate.getText().equalsIgnoreCase("Add")) {
            generateNextItemCode();
            if (customerService.saveCustomer(new CustomerDTO(lblCusId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText())) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully added customer !", ButtonType.OK).show();
            setData();
        } else {
            if (customerService.updateCustomer(new CustomerDTO(lblCusId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText())) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully update customer !", ButtonType.OK).show();
            setData();
        }
    }

    private void generateNextItemCode() {

        String oldId = customerService.getNextCustomerId();
        if (oldId != null) {
            String[] split = oldId.split("[C]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newRoomId = String.format("C%04d", lastDigits);
            lblCusId.setText(newRoomId);
        } else {
            lblCusId.setText("C0001");
        }
    }

    private void deleteCustomer(String customerId) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete this Customer ?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            customerService.deleteCustomer(customerId);
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully delete customer !", ButtonType.OK).show();
            clear();
            if (!txtSearch.getText().equalsIgnoreCase("")) {
                searchCustomer();
                return;
            }
            setData();
        }
    }

    private void setData() {
        btnAddUpdate.setText("Add");
        btnAddUpdate.setStyle("-fx-background-color:   #2f3542 ;-fx-background-radius: 20");
        setTableData();
        clear();
    }
}
