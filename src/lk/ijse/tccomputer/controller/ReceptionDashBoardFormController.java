package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import lk.ijse.tccomputer.dto.IncomeDTO;
import lk.ijse.tccomputer.dto.UserDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.*;
import lk.ijse.tccomputer.util.Navigation;
import lk.ijse.tccomputer.util.Routes;
import lk.ijse.tccomputer.util.UserHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReceptionDashBoardFormController {
    public JFXButton btnExit;
    public AnchorPane loadPane;
    public JFXButton btnDashboard;
    public JFXButton btnCustomer;
    public JFXButton btnOrders;
    public JFXButton btnRepair;
    public JFXButton btnSupplier;
    public JFXButton btnSupplies;
    public Label lblTime;
    public Label lblDate;
    public JFXButton btnLogOut;
    public Label lblUser;
    public LineChart lineChart;
    public PieChart pieChart;
    public Label lblOrder;
    public Label lblRepair;
    public Label lblCustomer;
    public Label lblSupplier;
    public Label lblOrderTotal;
    public Label lblRepairTotal;
    public Label lblOrderMonthlyTotal;
    public Label lblRepairMonthlyTotal;
    public Pane ReportPane1;
    public Pane ReportPane2;
    public JFXButton btnReport;
    public Pane editPane;
    public JFXTextField txtPass;
    public JFXPasswordField txtPasswordField;
    public ImageView picHide;
    public ImageView picShow;
    public JFXButton btnSave;
    public Button selectedButton;
    public Label lblId;

    public UserDTO userDTO;
    public UserService userService;
    public CustomerService customerService;
    public SupplierService supplierService;
    public OrdersService ordersService;
    public RepairService repairService;
    public IncomeService incomeService;

    public void initialize() {

        this.userService = ServiceFactory.getInstances().getService(ServiceType.USER);
        this.customerService = ServiceFactory.getInstances().getService(ServiceType.CUSTOMER);
        this.supplierService = ServiceFactory.getInstances().getService(ServiceType.SUPPLIER);
        this.ordersService = ServiceFactory.getInstances().getService(ServiceType.ORDER);
        this.repairService = ServiceFactory.getInstances().getService(ServiceType.REPAIR);
        this.incomeService = ServiceFactory.getInstances().getService(ServiceType.INCOME);
        this.userDTO= UserHolder.getInstance().getUserDTO();
        initializePane();
        setDateAndTime();
        initializeAllCharts();
        initializeLabel();
        setButtonStyle(btnDashboard, true);
        setPane();
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

    private void initializeLabel() {

        lblOrder.setText(String.valueOf(ordersService.getTodayOrderCount()));
        lblRepair.setText(String.valueOf(repairService.getTotalRepairCount()));
        lblCustomer.setText(String.valueOf(customerService.getCustomerCount()));
        lblSupplier.setText(String.valueOf(supplierService.getSupplierCount()));
        lblOrderTotal.setText(String.valueOf(incomeService.getMonthlyTotalSeparately("OD")));
        lblRepairTotal.setText(String.valueOf(incomeService.getMonthlyTotalSeparately("RR")));
    }

    private void initializeAllCharts() {

        double order = 0, repair = 0;
        lineChart.setTitle(String.valueOf(LocalDate.now().getYear()) + "  " + (LocalDate.now().getMonth().toString()));
        List<IncomeDTO> tmList = incomeService.getMonthlyIncomeWithoutGroup();

        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("0", 0));
        XYChart.Series series1 = new XYChart.Series();
        series1.getData().add(new XYChart.Data("0", 0));
        for (IncomeDTO incomeDTO : tmList) {
            if (incomeDTO.getIncomeID().startsWith("OD")) {
                order += incomeDTO.getTotal();
                String x = String.valueOf(incomeDTO.getDay());
                int y = (int) incomeDTO.getTotal();
                series.getData().add(new XYChart.Data(x, y));
            } else {
                repair += incomeDTO.getTotal();
                String x = String.valueOf(incomeDTO.getDay());
                int y = (int) incomeDTO.getTotal();
                series1.getData().add(new XYChart.Data(x, y));
            }
        }
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.getNode().setStyle("-fx-stroke: #8e44ad");
        lineChart.getData().addAll(series1);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series1.getNode().setStyle("-fx-stroke: #0fb9b1");

        lblOrderMonthlyTotal.setText(String.valueOf(order));
        lblRepairMonthlyTotal.setText(String.valueOf(repair));
        initializePieChart(order, repair);
    }

    private void initializePieChart(double order, double repair) {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Orders", order),
                new PieChart.Data("Repairs", repair)
        );
        pieChart.setData(pieChartData);
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

    public void btnExitOnAction(ActionEvent actionEvent) {

        System.exit(0);
    }

    public void btnDashboardOnAction(ActionEvent actionEvent) {

        Navigation.navigate(Routes.ReceptionForm, loadPane);

    }

    public void btnCustomerOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.CustomerForm, loadPane);
        setButtonStyle(btnCustomer, true);

    }

    public void btnOrdersOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.PlaceOrderForm, loadPane);
        setButtonStyle(btnOrders, true);
    }

    public void btnRepairOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.PlaceRepairForm, loadPane);
        setButtonStyle(btnRepair, true);

    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.SupplierForm, loadPane);
        setButtonStyle(btnSupplier, true);

    }

    public void btnSuppliesOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.SuppliesForm, loadPane);
        setButtonStyle(btnSupplies, true);
    }

    private void reSetButton() {

        List<Button> buttons = new ArrayList<>();
        buttons.add(btnDashboard);
        buttons.add(btnCustomer);
        buttons.add(btnSupplier);
        buttons.add(btnOrders);
        buttons.add(btnRepair);
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

    public void btnLogOutOnAction(ActionEvent actionEvent) {

        Navigation.navigate(Routes.LoginBack, loadPane);
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

    public void btnReportOnAction(ActionEvent actionEvent) {

        reSetButton();
        setButtonStyle(btnReport, false);
        ReportPane1.setVisible(true);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(20), ReportPane2);
        translateTransition1.setCycleCount(+600);
        translateTransition1.play();
    }

    public void btnCustomerReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/CustomerReport.jrxml"));
    }

    public void btnSupplierReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/SupplierReport.jrxml"));
    }

    public void btnOrderReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/OrderReport.jrxml"));
    }

    public void btnRepairReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/RepairReport.jrxml"));
    }

    public void btnSuppliesReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/SuppliesReport.jrxml"));
    }

    public void editPaneOnAction(MouseEvent mouseEvent) {

        editPane.setVisible(false);
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
}
