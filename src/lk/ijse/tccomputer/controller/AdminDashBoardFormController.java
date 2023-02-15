package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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

public class AdminDashBoardFormController {
    public AnchorPane loadPane;
    public JFXButton btnDashboard;
    public JFXButton btnEmployee;
    public JFXButton btnSupplier;
    public JFXButton btnSupplies;
    public JFXButton btnItem;
    public JFXButton btnIncome;
    public Label lblTime;
    public Label lblDate;
    public JFXButton btnLogOut;
    public JFXButton btnExit;
    public LineChart lineChart;
    public Label lblIncome;
    public Label lblEmployee;
    public Label lblOrders;
    public Label lblRepairs;
    public Label lblItems;
    public Label lblTitle;
    public Label lblUser;
    public JFXTextField txtPass;
    public JFXPasswordField txtPasswordField;
    public ImageView picHide;
    public ImageView picShow;
    public Pane editPane;
    public JFXButton btnSave;
    public Pane ReportPane1;
    public Pane ReportPane2;
    public JFXButton btnReport;
    public Label lblId;
    private Button selectedButton;

    public UserDTO userDTO;
    public UserService userService;
    public IncomeService incomeService;
    public EmployeeService employeeService;
    public OrdersService ordersService;
    public RepairService repairService;

    public void initialize() {
        this.userService = ServiceFactory.getInstances().getService(ServiceType.USER);
        this.incomeService = ServiceFactory.getInstances().getService(ServiceType.INCOME);
        this.employeeService=ServiceFactory.getInstances().getService(ServiceType.EMPLOYEE);
        this.ordersService=ServiceFactory.getInstances().getService(ServiceType.ORDER);
        this.repairService=ServiceFactory.getInstances().getService(ServiceType.REPAIR);
        this.userDTO= UserHolder.getInstance().getUserDTO();
        initializePane();
        setDateAndTime();
        initializeLineChart();
        initializeLabel();
        setButtonStyle(btnDashboard, true);
        setPane();
    }

    private void setPane() {

        ReportPane1.setVisible(false);
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(20), ReportPane2);
        translateTransition.setCycleCount(-600);
        translateTransition.play();

    }

    public void btnReportOnAction(ActionEvent actionEvent) {

        reSetButton();
        setButtonStyle(btnReport, false);
        ReportPane1.setVisible(true);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(20), ReportPane2);
        translateTransition1.setCycleCount(+600);
        translateTransition1.play();
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

    private void initializeLineChart() {

        lblTitle.setText(String.valueOf(LocalDate.now().getYear()) + "  " + (LocalDate.now().getMonth().toString()));

        List<IncomeDTO> tmList = incomeService.getMonthlyIncome(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        XYChart.Series series = new XYChart.Series();
        for (IncomeDTO incomeDTO : tmList) {
            String x = String.valueOf(incomeDTO.getDay());
            int y = (int) incomeDTO.getTotal();
            series.getData().add(new XYChart.Data(x, y));
        }
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.getNode().setStyle("-fx-stroke: #0fb9b1");

    }

    private void initializeLabel() {

        lblIncome.setText(String.valueOf(incomeService.getMonthlyTotal(LocalDate.now().getYear(), LocalDate.now().getMonthValue())));
        lblEmployee.setText(String.valueOf(employeeService.getEmployeeCount()));
        lblOrders.setText(String.valueOf(ordersService.getTodayOrderCount()));
        lblRepairs.setText(String.valueOf(repairService.getTotalRepairCount()));

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

    public void btnDashboardOnAction(ActionEvent actionEvent) {

        loadDashBoard();
    }

    private void loadDashBoard() {

        reSetButton();
        Navigation.navigate(Routes.AdminDashBoardFrom, loadPane);
        setButtonStyle(btnDashboard, true);
    }

    public void btnEmployeeOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.EmployeesForm, loadPane);
        setButtonStyle(btnEmployee, true);
    }

    public void btnSuppliesOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.SuppliesForm, loadPane);
        setButtonStyle(btnSupplies, true);
    }

    public void btnItemOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.StockManageForm, loadPane);
        setButtonStyle(btnItem, true);
    }

    public void btnIncomeOnAction(ActionEvent actionEvent) {

        loadIncome();
    }

    private void loadIncome() {

        reSetButton();
        Navigation.navigate(Routes.IncomeForm, loadPane);
        setButtonStyle(btnIncome, true);
    }

    public void btnSupplierOnAction(ActionEvent actionEvent) {

        reSetButton();
        Navigation.navigate(Routes.SupplierForm, loadPane);
        setButtonStyle(btnSupplier, true);
    }

    public void btnLogOutOnAction(ActionEvent actionEvent) {

        Navigation.navigate(Routes.LoginBack, loadPane);
    }

    private void reSetButton() {

        List<Button> buttons = new ArrayList<>();
        buttons.add(btnDashboard);
        buttons.add(btnEmployee);
        buttons.add(btnSupplier);
        buttons.add(btnItem);
        buttons.add(btnIncome);
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

    public void btnExitOnAction(ActionEvent actionEvent) {

        System.exit(0);
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

    public void btnEditFieldONAction(ActionEvent actionEvent) {

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

    public void btnIncomesOnAction(ActionEvent actionEvent) {

        loadIncome();
    }

    public void ReportPane1MouseClickEvent(MouseEvent mouseEvent) {

        ReportPane1.setVisible(false);
        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), ReportPane2);
        translateTransition1.setCycleCount(-600);
        translateTransition1.play();
        reSetButton();
        setButton();
    }

    public void btnEmployeeReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/EmployeeReport.jrxml"));
    }

    public void btnCustomerReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/CustomerReport.jrxml"));
    }

    public void btnSupplierReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/SupplierReport.jrxml"));
    }

    public void btnItemReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/ItemReport.jrxml"));
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

    public void btnIncomeReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/IncomeReport.jrxml"));
    }

    public void btnExpensesReportOnAction(ActionEvent actionEvent) {
        Navigation.navigateReport(this.getClass().getResourceAsStream("/lk/ijse/tccomputer/resources/report/ExpensesReport.jrxml"));
    }
}