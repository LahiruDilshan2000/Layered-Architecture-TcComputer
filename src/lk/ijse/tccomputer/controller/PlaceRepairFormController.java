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
import lk.ijse.tccomputer.service.custom.CustomerService;
import lk.ijse.tccomputer.service.custom.ItemService;
import lk.ijse.tccomputer.service.custom.RepairService;
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

public class PlaceRepairFormController {
    public AnchorPane loadPane;
    public JFXButton btnAdd;
    public TableView tblRepair;
    public TableColumn clmItemCode;
    public TableColumn clmItemBrand;
    public TableColumn clmItemName;
    public TableColumn clmUnitPrice;
    public TableColumn clmQty;
    public TableColumn clmTotal;
    public TableColumn clmOption;
    public Label lblRepairId;
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cmbCusId;
    public Label lblCusName;
    public Label lblCusAddress;
    public Label lblCusContact;
    public JFXTextField txtItemName;
    public JFXTextField txtAmount;
    public Label lblTotal;
    public JFXButton btnPlaceRepair;
    public ImageView picEdit;
    public JFXButton btnEdit;
    public JFXComboBox cmbItemCode;
    public Label lblBrandName;
    public Label lblItemName;
    public Label lblQtyOnHand;
    public Label lblUnitPrice;
    public JFXTextField txtQty;
    public JFXComboBox cmbItemType;
    private String itemCode;

    private List<ItemDescriptionDTO> itemDescriptionList;
    public RepairService repairService;
    public CustomerService customerService;
    public ItemService itemService;

    public void initialize() {

        this.repairService = ServiceFactory.getInstances().getService(ServiceType.REPAIR);
        this.customerService = ServiceFactory.getInstances().getService(ServiceType.CUSTOMER);
        this.itemService = ServiceFactory.getInstances().getService(ServiceType.ITEM);
        this.itemDescriptionList=new ArrayList<>();
        setDateAndTime();
        getRepairId();
        initializeTableData();
        setTableData();
        setCmbDetails();
        picEdit.setVisible(false);
        btnEdit.setVisible(false);
    }

    private void initializeTableData() {

        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        clmItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblRepair.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setText((ItemDescriptionTm) newValue);
                btnAdd.setText("Update Cart");
                btnAdd.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 20");
            }
        });
    }

    private void setCmbDetails() {

        List<String> cusList = customerService.findAllCustomer().stream().map(customerDTO -> customerDTO.getId()).collect(Collectors.toList());
        cmbCusId.setItems(FXCollections.observableArrayList(cusList));
        cmbCusId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setCustomerDetail((String) newValue);
            }
        });

        List<String> itemList = itemService.findAllItem().stream().map(itemDTO -> itemDTO.getItemCode()).collect(Collectors.toList());
        cmbItemCode.setItems(FXCollections.observableArrayList(itemList));
        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setItemDetail((String) newValue);
            }
        });
        cmbItemType.getItems().addAll("Laptop", "Computer", "Another accessories");

        txtAmount.setOnKeyReleased(event -> {
            setTotal();
        });
    }

    private void setItemDetail(String pk) {

        ItemDTO itemDTO = itemService.getItemByPk(pk);
        lblBrandName.setText(itemDTO.getBrand());
        lblItemName.setText(itemDTO.getName());
        lblQtyOnHand.setText(String.valueOf(itemDTO.getQtyOnHand()));
        lblUnitPrice.setText(String.valueOf(itemDTO.getUnitPrice()));
        cmbCusId.setDisable(true);
        picEdit.setVisible(true);
        btnEdit.setVisible(true);
    }

    private void setCustomerDetail(String pk) {

        CustomerDTO customerDTO = customerService.getCustomerByPk(pk);
        lblCusName.setText(customerDTO.getName());
        lblCusAddress.setText(customerDTO.getAddress());
        lblCusContact.setText(customerDTO.getContact());
    }

    private void setText(ItemDescriptionTm itemDescriptionTm) {

        cmbItemCode.setValue(itemDescriptionTm.getItemCode());
        txtQty.setText(String.valueOf(itemDescriptionTm.getQty()));
        txtQty.requestFocus();
        itemCode = itemDescriptionTm.getItemCode();
    }

    private void setTableData() {

        List<ItemDescriptionTm> tmList = itemDescriptionList.stream().map(i -> new ItemDescriptionTm(i.getItemCode(), i.getItemBrand(), i.getItemName(), i.getUnitPrice(), i.getQty(), i.getTotal(), getBtn(i))).collect(Collectors.toList());
        tblRepair.setItems(FXCollections.observableArrayList(tmList));
    }

    private JFXButton getBtn(ItemDescriptionDTO i) {

        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #e55039; -fx-background-radius: 20; -fx-text-fill: #ffffff");
        btn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete this item",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                itemDescriptionList.remove(i);
                btnAdd.setText("Add to Cart");
                btnAdd.setStyle("-fx-background-color:  #2f3542 ; -fx-background-radius: 20");
            }
            setDetails();
        });
        return btn;
    }

    private void setDateAndTime() {

        lblDate.setText(String.valueOf(LocalDate.now()));
        setTime();
    }

    private void setTime() {

        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH : mm : a");
                    lblTime.setText(LocalDateTime.now().format(formatter));

                }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void getRepairId() {

        String oldId = repairService.getNextRepairId();
        if (oldId != null) {
            String[] split = oldId.split("[-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newRoomId = String.format("RR-%04d", lastDigits);
            lblRepairId.setText(newRoomId);
        } else {
            lblRepairId.setText("RR-0001");
        }
    }

    private void setTotal() {

        double total = !txtAmount.getText().equalsIgnoreCase("") ? Double.parseDouble(txtAmount.getText()) : 0;
        for (ItemDescriptionDTO itemDescriptionDTO : itemDescriptionList) {
            total += itemDescriptionDTO.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
    }

    private void clear() {

        cmbItemCode.getSelectionModel().clearSelection();
        lblBrandName.setText("Item Brand...");
        lblItemName.setText("Item Name...");
        lblQtyOnHand.setText("Qty On Hand...");
        lblUnitPrice.setText("Unit Price...");
        txtQty.clear();
    }

    private void setDetails() {

        setTableData();
        setTotal();
        clear();
    }

    public void tbnAddOnAction(ActionEvent actionEvent) {

        if (cmbCusId.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Customer detail is empty", ButtonType.OK).show();
            cmbCusId.setFocusColor(Paint.valueOf("Red"));
            cmbCusId.requestFocus();
            return;
        } else if (cmbItemType.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Repair Item Type detail is empty", ButtonType.OK).show();
            cmbItemType.setFocusColor(Paint.valueOf("Red"));
            cmbItemType.requestFocus();
            return;
        } else if (txtItemName.getText().isEmpty() || !txtItemName.getText().matches("([\\w ]{1,})")) {
            new Alert(Alert.AlertType.ERROR, "Repair item name empty or invalid", ButtonType.OK).show();
            txtItemName.setFocusColor(Paint.valueOf("Red"));
            txtItemName.requestFocus();
            return;
        } else if (txtAmount.getText().isEmpty() || !txtAmount.getText().matches("^[0-9|.]{1,}$")) {
            new Alert(Alert.AlertType.ERROR, "Repair charge empty or invalid", ButtonType.OK).show();
            txtAmount.setFocusColor(Paint.valueOf("Red"));
            txtAmount.requestFocus();
            return;
        } else if (cmbItemCode.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Item detail is empty", ButtonType.OK).show();
            cmbItemCode.setFocusColor(Paint.valueOf("Red"));
            cmbItemCode.requestFocus();
            return;
        } else if (txtQty.getText().isEmpty() || !txtQty.getText().matches("[0-9|.]{1,}")) {
            new Alert(Alert.AlertType.ERROR, "Qty is empty", ButtonType.OK).show();
            txtQty.setFocusColor(Paint.valueOf("Red"));
            txtQty.requestFocus();
            return;
        } else if (Integer.parseInt(txtQty.getText()) > Integer.parseInt(lblQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Qty not that much !", ButtonType.OK).show();
            txtQty.setFocusColor(Paint.valueOf("Red"));
            txtQty.requestFocus();
            return;
        } else if (btnAdd.getText().equalsIgnoreCase("Add to cart")) {
            if (!checkDuplicate()) {
                itemDescriptionList.add(new ItemDescriptionDTO(cmbItemCode.getValue().toString(), lblBrandName.getText(), lblItemName.getText(), Double.parseDouble(lblUnitPrice.getText()), Integer.parseInt(txtQty.getText()), (Double.parseDouble(lblUnitPrice.getText()) * Double.parseDouble(txtQty.getText()))));
                setDetails();
            } else if (!updateExistsItem()) {
                new Alert(Alert.AlertType.ERROR, "No this qty on hand !", ButtonType.OK).show();
                txtQty.setFocusColor(Paint.valueOf("Red"));
                txtQty.requestFocus();
                return;
            }
            setDetails();
        } else {
            updateItem();
            btnAdd.setText("Add to Cart");
            btnAdd.setStyle("-fx-background-color:  #2f3542 ; -fx-background-radius: 20");
            itemCode = "";
            setDetails();
        }
    }

    private void updateItem() {

        for (int i = 0; i < itemDescriptionList.size(); i++) {
            if (itemDescriptionList.get(i).getItemCode().equalsIgnoreCase(itemCode)) {
                itemDescriptionList.get(i).setItemCode(cmbItemCode.getValue().toString());
                itemDescriptionList.get(i).setItemBrand(lblBrandName.getText());
                itemDescriptionList.get(i).setItemName(lblItemName.getText());
                itemDescriptionList.get(i).setUnitPrice(Double.parseDouble(lblUnitPrice.getText()));
                itemDescriptionList.get(i).setQty(Integer.parseInt(txtQty.getText()));
                itemDescriptionList.get(i).setTotal(Double.parseDouble(lblUnitPrice.getText()) * Double.parseDouble(txtQty.getText()));
                break;
            }
        }
    }

    private boolean updateExistsItem() {

        for (int i = 0; i < itemDescriptionList.size(); i++) {
            if (itemDescriptionList.get(i).getItemCode().equalsIgnoreCase(cmbItemCode.getValue().toString())) {
                if (itemDescriptionList.get(i).getQty() + Integer.parseInt(txtQty.getText()) <= Integer.parseInt(lblQtyOnHand.getText())) {
                    itemDescriptionList.get(i).setQty(itemDescriptionList.get(i).getQty() + Integer.parseInt(txtQty.getText()));
                    itemDescriptionList.get(i).setTotal(itemDescriptionList.get(i).getTotal() + (Double.parseDouble(lblUnitPrice.getText()) * Double.parseDouble(txtQty.getText())));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDuplicate() {

        return itemDescriptionList.stream().filter(itemDescriptionDTO -> itemDescriptionDTO.getItemCode().equalsIgnoreCase(cmbItemCode.getValue().toString())).findFirst().isPresent();
    }

    public void btnPlaceRepairOnAction(ActionEvent actionEvent) {

        if (cmbCusId.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Customer detail is empty", ButtonType.OK).show();
            cmbCusId.setFocusColor(Paint.valueOf("Red"));
            cmbCusId.requestFocus();
            return;
        } else if (cmbItemType.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Repair item type is empty", ButtonType.OK).show();
            cmbItemType.setFocusColor(Paint.valueOf("Red"));
            cmbItemType.requestFocus();
            return;
        } else if (txtItemName.getText().isEmpty() || !txtItemName.getText().matches("([\\w ]{1,})")) {
            new Alert(Alert.AlertType.ERROR, "Repair item name invalid or empty", ButtonType.OK).show();
            txtItemName.setFocusColor(Paint.valueOf("Red"));
            txtItemName.requestFocus();
            return;
        } else if (txtAmount.getText().isEmpty() || !txtAmount.getText().matches("^[0-9|.]{1,}$")) {
            new Alert(Alert.AlertType.ERROR, "Repair charge invalid or empty", ButtonType.OK).show();
            txtAmount.setFocusColor(Paint.valueOf("Red"));
            txtAmount.requestFocus();
            return;
        } else if (itemDescriptionList.isEmpty()) {
            if (repairService.saveRepairWithOutItem(new RepairDTO(lblRepairId.getText(), cmbItemType.getValue().toString(), txtItemName.getText(), Date.valueOf(LocalDate.now()), Double.parseDouble(txtAmount.getText()), cmbCusId.getValue().toString(), new IncomeDTO(lblRepairId.getText(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear(), Double.parseDouble(lblTotal.getText())))) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to save repair !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully save repair", ButtonType.OK).showAndWait();
            Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/RepairInvoice.jrxml"));
            Navigation.navigate(Routes.PlaceRepairForm, loadPane);
            return;
        } else {
            List<RepairReducesItemDetailDTO> tmList = itemDescriptionList.stream().map(itemDescriptionDTO -> new RepairReducesItemDetailDTO(lblRepairId.getText(), new ItemDTO(itemDescriptionDTO.getItemCode(), itemDescriptionDTO.getItemBrand(), itemDescriptionDTO.getItemName(), itemDescriptionDTO.getQty(), itemDescriptionDTO.getUnitPrice()))).collect(Collectors.toList());
            if (repairService.saveRepairWithItem(new RepairDTO(lblRepairId.getText(), cmbItemType.getValue().toString(), txtItemName.getText(), Date.valueOf(LocalDate.now()), Double.parseDouble(txtAmount.getText()), cmbCusId.getValue().toString(), tmList, new IncomeDTO(lblRepairId.getText(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear(), Double.parseDouble(lblTotal.getText())))) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to save repair !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully save repair", ButtonType.OK).showAndWait();
            Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/RepairInvoice.jrxml"));
            Navigation.navigate(Routes.PlaceRepairForm, loadPane);
            return;
        }
    }

    public void btnEditOnAction(ActionEvent actionEvent) {

        if (tblRepair.getItems().isEmpty()) {
            cmbCusId.setDisable(false);
            clear();
            cmbItemType.setValue("Item Type...");
            txtItemName.clear();
            txtAmount.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to clear order history",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                itemDescriptionList.clear();
                cmbCusId.setDisable(false);
                setDetails();
            }
        }
    }
}
