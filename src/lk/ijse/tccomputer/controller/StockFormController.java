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
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.tccomputer.dto.ItemDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.ItemService;
import lk.ijse.tccomputer.view.tm.ItemTm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StockFormController {
    public AnchorPane loadPane;
    public TableView tblStock;
    public TableColumn clmItemCode;
    public TableColumn clmBrand;
    public TableColumn clmItemName;
    public TableColumn clmQtyOnHand;
    public JFXTextField txtSearch;
    public JFXTextField txtBrand;
    public JFXTextField txtName;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public TableColumn clmUnitPrice;
    private static String itemCode = "";
    public TableColumn clmOption;
    public JFXButton btnAddUpdate;

    public ItemService itemService;

    public void initialize() {
        this.itemService = ServiceFactory.getInstances().getService(ServiceType.ITEM);
        initializeTableData();
        setTableData();
    }

    private void setTableData() {
        List<ItemTm> tmList = itemService.findAllItem().stream().map(i -> new ItemTm(i.getItemCode(), i.getBrand(), i.getName(), i.getQtyOnHand(), i.getUnitPrice(), getBtn(i))).collect(Collectors.toList());
        tblStock.setItems(FXCollections.observableArrayList(tmList));
    }

    private JFXButton getBtn(ItemDTO i) {
        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #e55039; -fx-background-radius: 20; -fx-text-fill: #ffffff;");
        btn.setOnAction(event -> {
            deleteItem(i.getItemCode());
        });
        return btn;
    }

    private void initializeTableData() {
        clmItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        clmItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        clmQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblStock.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setText((ItemTm) newValue);
                btnAddUpdate.setText("Update");
                btnAddUpdate.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 20");
            }
        });
    }

    private void setText(ItemTm itemtm) {
        itemCode = itemtm.getItemCode();
        txtName.setText(itemtm.getItemName());
        txtBrand.setText(itemtm.getItemBrand());
        txtQtyOnHand.setText(String.valueOf(itemtm.getQtyOnHand()));
        txtUnitPrice.setText(String.valueOf(itemtm.getUnitPrice()));
    }

    private void clear() {
        itemCode = "";
        txtName.clear();
        txtBrand.clear();
        txtQtyOnHand.clear();
        txtUnitPrice.clear();
        txtSearch.clear();
    }

    public void txtSearchKeyReleaseEvent(KeyEvent keyEvent) {
        searchItem();
    }

    private void searchItem() {

        List<ItemTm> tmList = itemService.searchItemByText(txtSearch.getText()).stream().map(i -> new ItemTm(i.getItemCode(), i.getBrand(), i.getName(), i.getQtyOnHand(), i.getUnitPrice(), getBtn(i))).collect(Collectors.toList());
        tblStock.setItems(FXCollections.observableArrayList(tmList));
    }

    private void generateNextItemCode() {

        String oldId = itemService.getNextItemCode();
        if (oldId != null) {
            String[] split = oldId.split("[S]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newRoomId = String.format("S%04d", lastDigits);
            itemCode = newRoomId;
        } else {
            itemCode = "S0001";
        }
    }

    public void deleteItem(String itemCode) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete this Item?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            itemService.deleteItem(itemCode);
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully delete item !", ButtonType.OK).show();
            clear();
            if (!txtSearch.getText().equalsIgnoreCase("")) {
                searchItem();
                return;
            }
            setData();
        }
    }

    private void setData() {
        btnAddUpdate.setText("Add");
        btnAddUpdate.setStyle("-fx-background-color:  #2f3542; -fx-background-radius: 20");
        setTableData();
        clear();
    }

    public void btnAddUpdateOnAction(ActionEvent actionEvent) {
        if (txtBrand.getText().isEmpty() || !txtBrand.getText().matches("([\\w ]{1,})")) {
            new Alert(Alert.AlertType.ERROR, "Brand name invalid or empty",ButtonType.OK).show();
            txtBrand.setFocusColor(Paint.valueOf("Red"));
            txtBrand.requestFocus();
            return;
        }else if (txtName.getText().isEmpty() || !txtName.getText().matches("([\\w ]{1,})")) {
            new Alert(Alert.AlertType.ERROR, "Item name invalid or empty",ButtonType.OK).show();
            txtName.setFocusColor(Paint.valueOf("Red"));
            txtName.requestFocus();
            return;
        }else if (txtQtyOnHand.getText().isEmpty() || !txtQtyOnHand.getText().matches("[0-9]{1,}")) {
            new Alert(Alert.AlertType.ERROR, "Qty text invalid or empty",ButtonType.OK).show();
            txtQtyOnHand.setFocusColor(Paint.valueOf("Red"));
            txtQtyOnHand.requestFocus();
            return;
        }else if (txtUnitPrice.getText().isEmpty() || !txtUnitPrice.getText().matches("^[0-9|.]{1,}$")) {
            new Alert(Alert.AlertType.ERROR, "Unit price text invalid or empty",ButtonType.OK).show();
            txtUnitPrice.setFocusColor(Paint.valueOf("Red"));
            txtUnitPrice.requestFocus();
            return;
        }else if (btnAddUpdate.getText().equalsIgnoreCase("Add")) {
            generateNextItemCode();
            if (itemService.saveItem(new ItemDTO(itemCode, txtBrand.getText(), txtName.getText(), Integer.parseInt(txtQtyOnHand.getText()), Double.parseDouble(txtUnitPrice.getText()))) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the item !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully added item !", ButtonType.OK).show();
            setData();
        } else {
            if (itemService.updateItem(new ItemDTO(itemCode, txtBrand.getText(), txtName.getText(), Integer.parseInt(txtQtyOnHand.getText()), Double.parseDouble(txtUnitPrice.getText()))) == null) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the item !", ButtonType.OK).show();
                return;
            }
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully update item !", ButtonType.OK).show();
            setData();
        }
    }
}
