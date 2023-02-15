package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import lk.ijse.tccomputer.dto.SupplierDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.SupplierService;
import lk.ijse.tccomputer.view.tm.SupplierTm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierFormController {
    public TableView tblSupplier;
    public JFXTextField txtSearch;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public JFXButton btnAddUpdate;
    public TableColumn clmSupplierId;
    public TableColumn clmSupplierName;
    public TableColumn clmSupplierAddress;
    public TableColumn clmSupplierContact;
    private static String supplierId;
    public TableColumn clmOption;

    public SupplierService supplierService;

    public void initialize() {

        this.supplierService = ServiceFactory.getInstances().getService(ServiceType.SUPPLIER);
        initializeTable();
        setTableData();
    }

    private void initializeTable() {

        clmSupplierId.setCellValueFactory(new PropertyValueFactory<>("SupId"));
        clmSupplierName.setCellValueFactory(new PropertyValueFactory<>("SupName"));
        clmSupplierAddress.setCellValueFactory(new PropertyValueFactory<>("SupAddress"));
        clmSupplierContact.setCellValueFactory(new PropertyValueFactory<>("SupContact"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setText((SupplierTm) newValue);
                btnAddUpdate.setText("Update");
                btnAddUpdate.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 20");
            }
        });
    }


    private void setText(SupplierTm supplierTm) {

        supplierId = supplierTm.getSupId();
        txtName.setText(supplierTm.getSupName());
        txtAddress.setText(supplierTm.getSupAddress());
        txtContact.setText(supplierTm.getSupContact());
    }

    private void clear() {

        supplierId = "";
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        txtSearch.clear();
    }

    private void setTableData() {

        List<SupplierTm> tmList = supplierService.findAllSupplier().stream().map(s -> new SupplierTm(s.getId(), s.getName(), s.getAddress(), s.getContact(), getBtn(s))).collect(Collectors.toList());
        tblSupplier.setItems(FXCollections.observableArrayList(tmList));
    }

    private JFXButton getBtn(SupplierDTO s) {

        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #e55039; -fx-background-radius: 20; -fx-text-fill: #ffffff;");
        btn.setOnAction(event -> {
            deleteSupplier(s.getId());
        });
        return btn;
    }

    public void txtSearchKeyReleaseEvent(KeyEvent keyEvent) {

        searchSupplier();
    }

    private void searchSupplier() {

        List<SupplierTm> tmList = supplierService.searchSupplierByText(txtSearch.getText()).stream().map(s -> new SupplierTm(s.getId(), s.getName(), s.getAddress(), s.getContact(), getBtn(s))).collect(Collectors.toList());
        tblSupplier.setItems(FXCollections.observableArrayList(tmList));
    }

    public void tbnAddUpdateOnAction(ActionEvent actionEvent) {

        if (txtName.getText().isEmpty() || !txtName.getText().matches("([\\w ]{1,})")) {
            new Alert(Alert.AlertType.ERROR, "Name invalid or empty",ButtonType.OK).show();
            txtName.setFocusColor(Paint.valueOf("Red"));
            txtName.requestFocus();
            return;
        }else if (txtAddress.getText().isEmpty() || !txtAddress.getText().matches("^[0-9a-zA-Z]{2,}")) {
            new Alert(Alert.AlertType.ERROR, "Address invalid or empty",ButtonType.OK).show();
            txtAddress.setFocusColor(Paint.valueOf("Red"));
            txtAddress.requestFocus();
            return;
        }else if (txtContact.getText().isEmpty() || !txtContact.getText().matches("^(075|077|071|074|078|076|070|072)([0-9]{7})$")) {
            new Alert(Alert.AlertType.ERROR, "Contact invalid or empty", ButtonType.OK).show();
            txtContact.setFocusColor(Paint.valueOf("Red"));
            txtContact.requestFocus();
            return;
        }else if (btnAddUpdate.getText().equalsIgnoreCase("Add")) {
            generateNextSupplierId();
            if(supplierService.saveSupplier(new SupplierDTO(supplierId, txtName.getText(), txtAddress.getText(), txtContact.getText()))==null){
                new Alert(Alert.AlertType.ERROR, "Failed to save the supplier !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully added supplier !", ButtonType.OK).show();
            setData();
        }else {
            if(supplierService.updateSupplier(new SupplierDTO(supplierId, txtName.getText(), txtAddress.getText(), txtContact.getText()))==null){
                new Alert(Alert.AlertType.ERROR, "Failed to update the supplier !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully update supplier !", ButtonType.OK).show();
            setData();
        }
    }

    private void generateNextSupplierId() {

        String oldId = supplierService.getNextSupplierId();
        if (oldId != null) {
            String[] split = oldId.split("[A]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newRoomId = String.format("A%04d", lastDigits);
            supplierId = newRoomId;
        } else {
            supplierId = "A0001";
        }
    }

    public void deleteSupplier(String supId) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete this Supplier?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            supplierService.deleteSupplier(supId);
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully delete supplier !", ButtonType.OK).show();
            clear();
            if (!txtSearch.getText().equalsIgnoreCase("")) {
                searchSupplier();
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
