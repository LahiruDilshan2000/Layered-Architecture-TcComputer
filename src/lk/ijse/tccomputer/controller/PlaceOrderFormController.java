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
import lk.ijse.tccomputer.service.custom.OrdersService;
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

public class PlaceOrderFormController {
    public JFXButton btnAdd;
    public TableView tblOrder;
    public TableColumn clmItemCode;
    public TableColumn clmItemBrand;
    public TableColumn clmItemName;
    public TableColumn clmUnitPrice;
    public TableColumn clmQty;
    public TableColumn clmTotal;
    public TableColumn clmOption;
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTime;
    public JFXComboBox cmbCusId;
    public Label lblCusName;
    public Label lblCusAddress;
    public Label lblCusContact;
    public JFXComboBox cmbItemCode;
    public Label lblBrandName;
    public Label lblItemName;
    public Label lblQtyOnHand;
    public Label lblUnitPrice;
    public JFXTextField txtQty;
    public JFXButton btnPlaceOrder;
    public AnchorPane loadPane;
    public Label lblTotal;
    public ImageView picEdit;
    public JFXButton btnEdit;
    private String itemCode;

    private List<ItemDescriptionDTO> itemDescriptionList;
    public OrdersService ordersService;
    public CustomerService customerService;
    public ItemService itemService;

    public void initialize() {

        this.ordersService = ServiceFactory.getInstances().getService(ServiceType.ORDER);
        this.customerService = ServiceFactory.getInstances().getService(ServiceType.CUSTOMER);
        this.itemService = ServiceFactory.getInstances().getService(ServiceType.ITEM);
        this.itemDescriptionList=new ArrayList<>();
        setDateAndTime();
        getOrderId();
        initializeTableData();
        setTableData();
        setComboBox();
        picEdit.setVisible(false);
        btnEdit.setVisible(false);
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

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setText((ItemDescriptionTm) newValue);
                btnAdd.setText("Update Cart");
                btnAdd.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 20");
            }
        });
    }

    private void setComboBox() {

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
        tblOrder.setItems(FXCollections.observableArrayList(tmList));
    }

    private JFXButton getBtn(ItemDescriptionDTO i) {

        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #e55039; -fx-background-radius: 20;-fx-text-fill: #ffffff");
        btn.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete this item",
                    ButtonType.YES, ButtonType.NO);
            Optional<ButtonType> buttonType = alert.showAndWait();
            if (buttonType.get() == ButtonType.YES) {
                itemDescriptionList.remove(i);
                btnAdd.setText("Add to Cart");
                btnAdd.setStyle("-fx-background-color: #079992; -fx-background-radius: 20");
            }
            setDetails();
        });
        return btn;
    }

    private void getOrderId() {

        String oldId = ordersService.getNextOrderId();
        if (oldId != null) {
            String[] split = oldId.split("[-]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newRoomId = String.format("OD-%04d", lastDigits);
            lblOrderId.setText(newRoomId);
        } else {
            lblOrderId.setText("OD-0001");
        }
    }

    public void tbnAddOnAction(ActionEvent actionEvent) {

        if (cmbCusId.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Customer detail is empty", ButtonType.OK).show();
            cmbCusId.setFocusColor(Paint.valueOf("Red"));
            cmbCusId.requestFocus();
            return;
        } else if (cmbItemCode.getSelectionModel().isSelected(-1)) {
            new Alert(Alert.AlertType.ERROR, "Item detail is empty", ButtonType.OK).show();
            cmbItemCode.setFocusColor(Paint.valueOf("Red"));
            cmbItemCode.requestFocus();
            return;
        } else if (txtQty.getText().isEmpty() || !txtQty.getText().matches("[0-9]{1,}")) {
            new Alert(Alert.AlertType.ERROR, "Qty text invalid or empty", ButtonType.OK).show();
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
                return;
            } else if (!updateExistItem()) {
                new Alert(Alert.AlertType.ERROR, "Qty not that much !", ButtonType.OK).show();
                txtQty.setFocusColor(Paint.valueOf("Red"));
                txtQty.requestFocus();
                return;
            }
            setDetails();
        } else {
            updateOrders();
            btnAdd.setText("Add to Cart");
            btnAdd.setStyle("-fx-background-color: #079992; -fx-background-radius: 20");
            itemCode = "";
            setDetails();
        }

    }

    private boolean updateExistItem() {

        for (int i = 0; i < itemDescriptionList.size(); i++) {
            if (itemDescriptionList.get(i).getItemCode().equalsIgnoreCase(cmbItemCode.getValue().toString())) {
                if ((itemDescriptionList.get(i).getQty() + Integer.parseInt(txtQty.getText())) <= Integer.parseInt(lblQtyOnHand.getText())) {
                    itemDescriptionList.get(i).setQty(itemDescriptionList.get(i).getQty() + Integer.parseInt(txtQty.getText()));
                    itemDescriptionList.get(i).setTotal(itemDescriptionList.get(i).getTotal() + (Double.parseDouble(lblUnitPrice.getText()) * Double.parseDouble(txtQty.getText())));
                    return true;
                }
            }
        }
        return false;

    }

    private void updateOrders() {

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

    private void setDetails() {

        setTableData();
        setTotal();
        clear();

    }

    private boolean checkDuplicate() {

        return itemDescriptionList.stream().filter(itemDescriptionDTO -> itemDescriptionDTO.getItemCode().equalsIgnoreCase(cmbItemCode.getValue().toString())).findFirst().isPresent();

    }

    private void setTotal() {

        double total = 0;
        for (ItemDescriptionDTO itemDescription : itemDescriptionList) {
            total += itemDescription.getTotal();
        }
        lblTotal.setText(String.valueOf(total));

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

        if (itemDescriptionList.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Order are empty !", ButtonType.OK).show();
            return;
        }
        List<OrderDetailDTO> orderDetailList = itemDescriptionList.stream().map(i -> new OrderDetailDTO(lblOrderId.getText(),new ItemDTO(i.getItemCode(), i.getItemBrand(), i.getItemName(), i.getQty(), i.getUnitPrice()))).collect(Collectors.toList());

        if (ordersService.saveOrder(new OrdersDTO(lblOrderId.getText(), Date.valueOf(LocalDate.now()), cmbCusId.getValue().toString(), orderDetailList, new IncomeDTO(lblOrderId.getText(), LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear(), Double.parseDouble(lblTotal.getText())))) == null) {
            new Alert(Alert.AlertType.ERROR, "Failed to save order !", ButtonType.OK).show();
            return;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully save order !", ButtonType.OK).showAndWait();
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/OrderInvoice.jrxml"));
        Navigation.navigate(Routes.PlaceOrderForm, loadPane);

    }

    private void clear() {

        cmbItemCode.getSelectionModel().clearSelection();
        lblBrandName.setText("Item Brand...");
        lblItemName.setText("Item Name...");
        lblQtyOnHand.setText("Qty On Hand...");
        lblUnitPrice.setText("Unit Price...");
        txtQty.clear();

    }

    public void btnEditOnAction(ActionEvent actionEvent) {

        if (tblOrder.getItems().isEmpty()) {
            cmbCusId.setDisable(false);
            clear();
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
