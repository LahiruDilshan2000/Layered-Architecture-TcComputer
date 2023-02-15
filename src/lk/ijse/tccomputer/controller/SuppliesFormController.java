package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lk.ijse.tccomputer.dto.*;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.ItemService;
import lk.ijse.tccomputer.service.custom.SupplierService;
import lk.ijse.tccomputer.service.custom.SuppliesService;
import lk.ijse.tccomputer.util.Navigation;
import lk.ijse.tccomputer.util.Routes;
import lk.ijse.tccomputer.view.tm.ItemDescriptionTm;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SuppliesFormController {
    public AnchorPane loadPane;
    public JFXButton btnAdd;
    public TableColumn clmItemCode;
    public TableColumn clmItemBrand;
    public TableColumn clmItemName;
    public TableColumn clmUnitPrice;
    public TableColumn clmQty;
    public TableColumn clmTotal;
    public TableColumn clmOption;
    public Label lblSuppliesCode;
    public Label lblDate;
    public TableView tblSupplies;
    public Label lblTime;
    public JFXComboBox cmbSupplierId;
    public Label lblSupplierName;
    public Label lblSupplierAddress;
    public Label lblSupplierContact;
    public JFXComboBox cmbItemCode;
    public Label lblBrandName;
    public Label lblItemName;
    public JFXTextField txtQty;
    public Label lblTotal;
    public JFXButton btnPlaceOrder;
    public ImageView picEdit;
    public JFXButton btnEdit;
    public JFXTextField txtUnitPrice;
    private String itemCode;

    private List<ItemDescriptionDTO> itemDescriptionDTOList;
    public SuppliesService suppliesService;
    public SupplierService supplierService;
    public ItemService itemService;

    public void initialize() {

        this.suppliesService = ServiceFactory.getInstances().getService(ServiceType.SUPPLIES);
        this.supplierService = ServiceFactory.getInstances().getService(ServiceType.SUPPLIER);
        this.itemService = ServiceFactory.getInstances().getService(ServiceType.ITEM);
        this.itemDescriptionDTOList=new ArrayList<>();
        setDateAndTime();
        getSuppliesCode();
        initializeTableData();
        setTableData();
        setComboDetails();
        clearButton(false);
    }

    private void setDateAndTime() {

        lblDate.setText(String.valueOf(LocalDate.now()));

        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH : mm : a");
                    lblTime.setText(LocalDateTime.now().format(formatter));

                }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void initializeTableData() {
        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        clmItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblSupplies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setText((ItemDescriptionTm) newValue);
                btnAdd.setText("Update Cart");
                btnAdd.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 20");
            }
        });
    }

    private void setComboDetails() {

        List<String> supplierList = supplierService.findAllSupplier().stream().map(supplierDTO -> supplierDTO.getId()).collect(Collectors.toList());
        cmbSupplierId.setItems(FXCollections.observableArrayList(supplierList));
        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setSupplierDetail((String) newValue);
            }
        });

        List<String> itemList = itemService.findAllItem().stream().map(itemDTO -> itemDTO.getItemCode()).collect(Collectors.toList());
        cmbItemCode.setItems(FXCollections.observableArrayList(itemList));
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setItemDetail((String) newValue);
            }
        });
    }

    private void setItemDetail(String pk) {

        ItemDTO itemDTO = itemService.getItemByPk(pk);
        lblBrandName.setText(itemDTO.getBrand());
        lblItemName.setText(itemDTO.getName());
        txtUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        cmbSupplierId.setDisable(true);
        clearButton(true);
        txtUnitPrice.requestFocus();
    }

    private void setSupplierDetail(String pk) {

        SupplierDTO supplierDTO = supplierService.getSupplierByPk(pk);
        lblSupplierName.setText(supplierDTO.getName());
        lblSupplierAddress.setText(supplierDTO.getAddress());
        lblSupplierContact.setText(supplierDTO.getContact());
    }

    private void getSuppliesCode() {

        String oldId = suppliesService.getNextSuppliesCode();
        if (oldId != null) {
            String[] split = oldId.split("[-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newRoomId = String.format("SE-%04d", lastDigits);
            lblSuppliesCode.setText(newRoomId);
        } else {
            lblSuppliesCode.setText("SE-0001");
        }
    }

    private void setText(ItemDescriptionTm itemDescriptionTm) {

        cmbItemCode.setValue(itemDescriptionTm.getItemCode());
        txtQty.setText(String.valueOf(itemDescriptionTm.getQty()));
        txtQty.requestFocus();
        lblBrandName.setText(itemDescriptionTm.getItemBrand());
        lblItemName.setText(itemDescriptionTm.getItemName());
        itemCode = itemDescriptionTm.getItemCode();
        txtUnitPrice.setText(String.valueOf(itemDescriptionTm.getUnitPrice()));
    }

    private void setTableData() {

        List<ItemDescriptionTm> tmList = itemDescriptionDTOList.stream().map(i -> new ItemDescriptionTm(i.getItemCode(), i.getItemBrand(), i.getItemName(), i.getUnitPrice(), i.getQty(), i.getTotal(), getBtn(i))).collect(Collectors.toList());
        tblSupplies.setItems(FXCollections.observableArrayList(tmList));
    }

    private JFXButton getBtn(ItemDescriptionDTO i) {

        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #e55039; -fx-background-radius: 20;-fx-text-fill: #ffffff");
        btn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to remove this item",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                itemDescriptionDTOList.remove(i);
                setButton();
            }
            setDetails();
        });
        return btn;
    }

    private void setDetails() {

        setTableData();
        setTotal();
        clear();
    }

    private void setTotal() {

        double total = 0;
        for (ItemDescriptionDTO itemDescriptionDTO : itemDescriptionDTOList) {
            total += itemDescriptionDTO.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void clear() {

        cmbItemCode.getSelectionModel().clearSelection();
        lblBrandName.setText("Item Brand...");
        lblItemName.setText("Item Name...");
        txtUnitPrice.clear();
        txtQty.clear();
    }

    public void tbnAddOnAction(ActionEvent actionEvent) {

        if (cmbSupplierId.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Supplier detail is empty", ButtonType.OK).show();
            cmbSupplierId.setFocusColor(Paint.valueOf("Red"));
            cmbSupplierId.requestFocus();
            return;
        } else if (cmbItemCode.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Item detail is empty", ButtonType.OK).show();
            cmbItemCode.setFocusColor(Paint.valueOf("Red"));
            cmbItemCode.requestFocus();
            return;
        } else if (txtUnitPrice.getText().isEmpty() || !txtUnitPrice.getText().matches("^[0-9|.]{1,}$")) {
            new Alert(Alert.AlertType.ERROR, "Item unit price is invalid or empty", ButtonType.OK).show();
            txtUnitPrice.setFocusColor(Paint.valueOf("Red"));
            txtUnitPrice.requestFocus();
            return;
        } else if (txtQty.getText().isEmpty() || !txtQty.getText().matches("[0-9|.]{1,}")) {
            new Alert(Alert.AlertType.ERROR, "Qty is invalid or empty", ButtonType.OK).show();
            txtQty.setFocusColor(Paint.valueOf("Red"));
            txtQty.requestFocus();
            return;
        } else if (btnAdd.getText().equalsIgnoreCase("Add to cart")) {
            if (!checkDuplicate()) {
                itemDescriptionDTOList.add(new ItemDescriptionDTO(cmbItemCode.getValue().toString(), lblBrandName.getText(), lblItemName.getText(), Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQty.getText()), (Double.parseDouble(txtUnitPrice.getText()) * Double.parseDouble(txtQty.getText()))));
                setDetails();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "You all ready add this item in the list.Do you want to update this item ?",
                        ButtonType.YES, ButtonType.NO);
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.get() == ButtonType.YES) {
                    getBeforeAddItemDetail();
                    btnAdd.setText("Update Cart");
                    btnAdd.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 20");
                } else {
                    clear();
                }
            }
        } else {
            updateItem();
            setButton();
            itemCode = "";
            setDetails();
        }
    }

    private void setButton() {
        btnAdd.setText("Add to Cart");
        btnAdd.setStyle("-fx-background-color:  #2f3542; -fx-background-radius: 20");
    }

    private void updateItem() {

        for (int i = 0; i < itemDescriptionDTOList.size(); i++) {
            if (itemDescriptionDTOList.get(i).getItemCode().equalsIgnoreCase(itemCode)) {
                itemDescriptionDTOList.get(i).setItemCode(cmbItemCode.getValue().toString());
                itemDescriptionDTOList.get(i).setItemBrand(lblBrandName.getText());
                itemDescriptionDTOList.get(i).setItemName(lblItemName.getText());
                itemDescriptionDTOList.get(i).setUnitPrice(Double.parseDouble(txtUnitPrice.getText()));
                itemDescriptionDTOList.get(i).setQty(Integer.parseInt(txtQty.getText()));
                itemDescriptionDTOList.get(i).setTotal(Double.parseDouble(txtUnitPrice.getText()) * Double.parseDouble(txtQty.getText()));
                break;
            }
        }
    }

    private void getBeforeAddItemDetail() {

        ItemDescriptionDTO itemDescriptionDTO = itemDescriptionDTOList.stream().filter(i -> i.getItemCode().equalsIgnoreCase(cmbItemCode.getValue().toString())).findAny().get();
        itemCode = itemDescriptionDTO.getItemCode();
        txtQty.setText(String.valueOf(itemDescriptionDTO.getQty()));
        txtUnitPrice.setText(String.valueOf(itemDescriptionDTO.getUnitPrice()));
    }

    private boolean checkDuplicate() {

        return itemDescriptionDTOList.stream().filter(itemDescriptionDTO -> itemDescriptionDTO.getItemCode().equalsIgnoreCase(cmbItemCode.getValue().toString())).findFirst().isPresent();
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

        if (itemDescriptionDTOList.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Supplies detail are empty !", ButtonType.OK).show();
            return;
        }
        List<SuppliesDetailDTO> tmList = itemDescriptionDTOList.stream().map(i -> new SuppliesDetailDTO(lblSuppliesCode.getText(), new ItemDTO(i.getItemCode(), i.getItemBrand(), i.getItemName(), i.getQty(), i.getUnitPrice()))).collect(Collectors.toList());
        if (suppliesService.saveSupplies(new SuppliesDTO(lblSuppliesCode.getText(), Date.valueOf(LocalDate.now()), cmbSupplierId.getValue().toString(), tmList, new OutComeDTO(lblSuppliesCode.getText(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear(), Double.parseDouble(lblTotal.getText())))) == null) {
            new Alert(Alert.AlertType.ERROR, "Failed to save supplies !", ButtonType.OK).show();
            return;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully save supplies !", ButtonType.OK).showAndWait();
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/SuppliesInvoice.jrxml"));
        Navigation.navigate(Routes.SuppliesForm, loadPane);

    }

    public void btnEditOnAction(ActionEvent actionEvent) {

        if (tblSupplies.getItems().isEmpty()) {
            cmbSupplierId.setDisable(false);
            clearButton(false);
            clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to clear supplies history",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                itemDescriptionDTOList.clear();
                cmbSupplierId.setDisable(false);
                clearButton(false);
                setDetails();
            }
        }
    }

    private void clearButton(boolean b) {
        picEdit.setVisible(b);
        btnEdit.setVisible(b);
    }
}
