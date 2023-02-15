package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lk.ijse.tccomputer.dto.ItemDTO;
import lk.ijse.tccomputer.dto.UserDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.ItemService;
import lk.ijse.tccomputer.service.custom.UserService;
import lk.ijse.tccomputer.util.Navigation;
import lk.ijse.tccomputer.util.Routes;
import lk.ijse.tccomputer.util.UserHolder;
import lk.ijse.tccomputer.view.tm.ItemTm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StockManageDashBordFormController {
    public JFXButton btnExit;
    public AnchorPane loadPane;
    public JFXButton btnDashboard;
    public JFXButton btnItem;
    public JFXButton btnSupplier;
    public JFXButton btnSupplies;
    public Label lblTime;
    public Label lblDate;
    public JFXButton btnLogOut;
    public LineChart lineChart;
    public Label lblQty;
    public TableView tblItem;
    public TableColumn clmCode;
    public TableColumn clmName;
    public TableColumn clmQty;
    public Label lblStockCount;
    public Label lblStockPercentage;
    public JFXButton btnUpdateStock;
    public JFXButton btnAddNewStock;
    public JFXComboBox cmbItemName;
    public JFXButton btnUpdate;
    public Label lblUser;
    public JFXTextField txtPass;
    public JFXPasswordField txtPasswordField;
    public ImageView picHide;
    public ImageView picShow;
    public JFXButton btnSave;
    public Pane editPane;
    public JFXButton btnReport;
    public Pane ReportPane1;
    public Pane ReportPane2;
    public Label lblId;
    private Button selectedButton;

    public UserDTO userDTO;
    public UserService userService;
    public ItemService itemService;

    public void initialize() {

        this.userService=ServiceFactory.getInstances().getService(ServiceType.USER);
        this.itemService = ServiceFactory.getInstances().getService(ServiceType.ITEM);
        this.userDTO= UserHolder.getInstance().getUserDTO();
        setDateAndTime();
        initializePane();
        initializeBarChart();
        setButtonStyle(btnDashboard, true);
        initializeTable();
        setItemDetails();
        setPane();
    }

    private void initializeTable() {

        clmCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        clmQty.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
    }

    private void setPane() {

        ReportPane1.setVisible(false);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20), ReportPane2);
        translateTransition.setCycleCount(-600);
        translateTransition.play();

        ReportPane1.setOnMouseClicked(event -> {
            reSetButton();
            ReportPane1.setVisible(false);
            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), ReportPane2);
            translateTransition1.setCycleCount(-600);
            translateTransition1.play();
            setButton();
        });

    }

    private void setDateAndTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd / MM / yyyy");
        lblDate.setText(String.valueOf(LocalDate.now().format(formatter)));

        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, event -> {
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH : mm   a");
                    lblTime.setText(LocalDateTime.now().format(formatter1));

                }), new KeyFrame(Duration.seconds(1)));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void initializePane() {

        picHide.setVisible(false);
        editPane.setVisible(false);
        txtPass.setVisible(false);
        btnSave.setVisible(false);
        setText();
        setEdit(false);
        editPane.setOnMouseClicked(event -> {
            setText();
            editPane.setVisible(false);
            setEdit(false);
            txtPass.setVisible(false);
            txtPasswordField.setVisible(true);
            picShow.setVisible(true);
            picHide.setVisible(false);
            btnSave.setVisible(false);
        });
    }

    private void setEdit(boolean b) {

        txtPass.setEditable(b);
        txtPasswordField.setEditable(b);
    }

    private void setItemDetails() {

        List<String> tmList = itemService.findAllItem().stream().map(itemDTO -> itemDTO.getName()).collect(Collectors.toList());
        cmbItemName.setItems(FXCollections.observableArrayList(tmList));
        cmbItemName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null!=newValue){
                for (ItemDTO itemDTO : itemService.findAllItem()) {
                    if (itemDTO.getName().equalsIgnoreCase(newValue.toString())){
                        lblQty.setText(String.valueOf(itemDTO.getQtyOnHand()));
                    }
                }
            }
        });
        lblStockCount.setText(String.valueOf(itemService.getItemCount()));
        lblStockPercentage.setText(String.valueOf(Integer.parseInt(lblStockCount.getText()) / 1000 * 100));
        addTableData();
    }

    private void addTableData() {

        List<ItemTm> tmList = itemService.getRemainingItem().stream().map(itemDTO -> new ItemTm(itemDTO.getItemCode(), itemDTO.getBrand(), itemDTO.getName(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice(), new JFXButton())).collect(Collectors.toList());
        tblItem.setItems(FXCollections.observableArrayList(tmList));
    }

    private void initializeBarChart() {

        List<ItemDTO> tmList = itemService.findAllItem();

        XYChart.Series series = new XYChart.Series();
        for (ItemDTO itemDTO : tmList) {
            String x = itemDTO.getName();
            int y = itemDTO.getQtyOnHand();
            series.getData().add(new XYChart.Data(x, y));
        }
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.getNode().setStyle("-fx-stroke: #00b894");
    }

    public void btnExitOnAction(ActionEvent actionEvent) {

        System.exit(0);
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.StockManageDashBoardForm, loadPane);
        setButtonStyle(btnDashboard, true);

    }

    public void btnItemOnAction(ActionEvent actionEvent) {

        addStock();
    }

    private void addStock() {

        reSetButton();
        Navigation.navigate(Routes.StockManageForm, loadPane);
        setButtonStyle(btnItem, true);

    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.SupplierForm, loadPane);
        setButtonStyle(btnSupplier, true);

    }

    public void btnSuppliesOnAction(ActionEvent actionEvent) {

        updateStock();
    }

    private void updateStock() {

        reSetButton();
        Navigation.navigate(Routes.SuppliesForm, loadPane);
        setButtonStyle(btnSupplies, true);

    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.LoginBack, loadPane);

    }

    private void reSetButton() {

        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(btnDashboard);
        buttons.add(btnSupplier);
        buttons.add(btnItem);
        buttons.add(btnSupplies);
        buttons.add(btnReport);

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setStyle("-fx-background-color: transparent;\n" +
                    "    -fx-text-fill: #dcdde1;");
        }
    }

    private void setButtonStyle(Button btn, boolean flag) {

        if (flag) {
            selectedButton = btn;
            setButton();
        } else {
            btn.setStyle("-fx-background-color: #5352ed;\n" +
                    "    -fx-border-color: WHITE;\n" +
                    "    -fx-border-width: 0px 0px 0px 5px;\n" +
                    "    -fx-text-fill: WHITE;");
        }
    }

    private void setButton() {

        selectedButton.setStyle("-fx-background-color: #5352ed;\n" +
                "    -fx-border-color: WHITE;\n" +
                "    -fx-border-width: 0px 0px 0px 5px;\n" +
                "    -fx-text-fill: WHITE;");
    }

    public void btnUpdateStockOnAction(ActionEvent actionEvent) {

        updateStock();
    }

    public void btnAddNewStockOnAction(ActionEvent actionEvent) {

        addStock();
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

        updateStock();
    }

    public void btnEditUserOnAction(ActionEvent actionEvent) {

        setText();
        btnSave.setVisible(false);
        editPane.setVisible(true);
    }

    private void setText() {

        lblUser.setText(userDTO.getUserID());
        lblId.setText(userDTO.getUserID());
        txtPass.setText(userDTO.getPassword());
        txtPasswordField.setText(userDTO.getPassword());
    }

    public void btnEditFieldOnAction(ActionEvent actionEvent) {

        setEdit(true);
        btnSave.setVisible(true);
    }

    public void btnShowHideOnAction(ActionEvent actionEvent) {

        if (picShow.isVisible()) {
            txtPass.setText(txtPasswordField.getText());
            txtPasswordField.setVisible(false);
            txtPass.setVisible(true);
            picShow.setVisible(false);
            picHide.setVisible(true);
        } else {
            txtPasswordField.setText(txtPass.getText());
            txtPass.setVisible(false);
            txtPasswordField.setVisible(true);
            picHide.setVisible(false);
            picShow.setVisible(true);
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {

        if (txtPasswordField.isVisible()) {
            if (txtPasswordField.getText().isEmpty() || !txtPasswordField.getText().matches("^[a-zA-Z0-9_]{8,}$")) {
                new Alert(Alert.AlertType.ERROR, "Password is invalid or empty", ButtonType.OK).show();
                txtPasswordField.setFocusColor(Paint.valueOf("Red"));
                txtPasswordField.requestFocus();
                return;
            }
            updateUser();
            return;
        } else if (txtPass.getText().isEmpty() || !txtPass.getText().matches("^[a-zA-Z0-9_]{8,}$")) {
            new Alert(Alert.AlertType.ERROR, "Password is invalid or empty", ButtonType.OK).show();
            txtPass.setFocusColor(Paint.valueOf("Red"));
            txtPass.requestFocus();
            return;
        }
        updateUser();
    }

    private void updateUser() {

        UserDTO userDTO=new UserDTO(lblId.getText(), txtPasswordField.isVisible() ? txtPasswordField.getText() : txtPass.getText(), this.userDTO.getRole());
        if(userService.updateUser(userDTO)==null){
            new Alert(Alert.AlertType.ERROR, "Failed to update the user detail !", ButtonType.OK).show();
            return;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully update user detail !", ButtonType.OK).show();
        this.userDTO=userDTO;
        setText();
        setEdit(false);
        txtPass.setVisible(false);
        txtPasswordField.setVisible(true);
        picShow.setVisible(true);
        picHide.setVisible(false);
        btnSave.setVisible(false);
    }

    public void btnReportOnAction(ActionEvent actionEvent) {

        reSetButton();
        setButtonStyle(btnReport, false);
        ReportPane1.setVisible(true);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(20), ReportPane2);
        translateTransition1.setCycleCount(+600);
        translateTransition1.play();
    }

    public void btnSupplierReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/SupplierReport.jrxml"));
    }

    public void btnItemReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/ItemReport.jrxml"));
    }

    public void btnSuppliesReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/SuppliesReport.jrxml"));
    }
}
