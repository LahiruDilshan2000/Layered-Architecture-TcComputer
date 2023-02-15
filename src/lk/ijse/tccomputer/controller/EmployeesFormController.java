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
import lk.ijse.tccomputer.dto.EmployeeDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.EmployeeService;
import lk.ijse.tccomputer.view.tm.EmployeeTm;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeesFormController {
    public JFXTextField txtUserName;
    public JFXTextField txtMail;
    public JFXTextField txtContact;
    public JFXTextField txtSalary;
    public JFXTextField txtSearch;
    public TableColumn clmEmpId;
    public TableColumn clmName;
    public TableColumn clmMail;
    public TableColumn clmContact;
    public TableColumn clmSalary;
    public TableView tblEmployee;
    public TableColumn clmOption;
    public Label lblEmpId;
    public JFXButton btnAddUpdate;
    public Pane empIdPane;

    public EmployeeService employeeService;

    public void initialize() {
        this.employeeService= ServiceFactory.getInstances().getService(ServiceType.EMPLOYEE);
        initializeTable();
        setDataToTable();
        empIdPane.setVisible(false);
    }

    private void initializeTable() {
        clmEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        clmName.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        clmMail.setCellValueFactory(new PropertyValueFactory<>("employeeMail"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("employeeContact"));
        clmSalary.setCellValueFactory(new PropertyValueFactory<>("empSalary"));
        clmOption.setCellValueFactory(new PropertyValueFactory<>("option"));

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null != newValue) {
                setTexts((EmployeeTm) newValue);
                empIdPane.setVisible(true);
                btnAddUpdate.setText("Update");
                btnAddUpdate.setStyle("-fx-background-color: #706fd3; -fx-background-radius: 20");
            }
        });
    }

    private void setTexts(EmployeeTm employeeTm) {
        lblEmpId.setText(employeeTm.getEmployeeId());
        txtUserName.setText(employeeTm.getEmployeeName());
        txtMail.setText(employeeTm.getEmployeeMail());
        txtContact.setText(employeeTm.getEmployeeContact());
        txtSalary.setText(String.valueOf(employeeTm.getEmpSalary()));
    }

    private void setDataToTable() {
        List<EmployeeTm> tmList = employeeService.findAllEmployee().stream().map(e -> new EmployeeTm(e.getEmpID(), e.getName(), e.getMail(), e.getContact(), e.getSalary(), getBtn(e))).collect(Collectors.toList());
        tblEmployee.setItems(FXCollections.observableArrayList(tmList));
    }

    private JFXButton getBtn(EmployeeDTO e) {
        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-background-color: #e55039; -fx-background-radius: 20; -fx-text-fill: #ffffff;");
        btn.setOnAction(event -> {
            deleteEmployee(e.getEmpID());
        });
        return btn;
    }

    private void clear() {
        txtUserName.clear();
        txtMail.clear();
        txtContact.clear();
        txtSalary.clear();
    }

    public void deleteEmployee(String employeeId) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure do you want to delete this Employee?",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        if (buttonType.get() == ButtonType.YES) {
            employeeService.deleteEmployee(employeeId);
            new Alert(Alert.AlertType.CONFIRMATION, "Successfully delete employee !", ButtonType.OK).show();
            clear();
            if (!txtSearch.getText().equalsIgnoreCase("")) {
                searchEmployee();
                return;
            }
            setData();
        }
    }

    public void txtSearchKeyReleaseEvent(KeyEvent keyEvent) {
        searchEmployee();
    }

    private void searchEmployee() {
        List<EmployeeTm> tmList = employeeService.searchEmployeeByText(txtSearch.getText()).stream().map(e -> new EmployeeTm(e.getEmpID(), e.getName(), e.getMail(), e.getContact(), e.getSalary(), getBtn(e))).collect(Collectors.toList());
        tblEmployee.setItems(FXCollections.observableArrayList(tmList));
    }

    public void tbnAddUpdateOnAction(ActionEvent actionEvent) {
        if(txtUserName.getText().isEmpty() || !txtUserName.getText().matches("([\\w ]{1,})")) {
            new Alert(Alert.AlertType.ERROR, "Name invalid or empty", ButtonType.OK).show();
            txtUserName.setFocusColor(Paint.valueOf("Red"));
            txtUserName.requestFocus();
            return;
        }else if(txtMail.getText().isEmpty() || !txtMail.getText().matches("^([a-zA-Z|0-9]{3,})[@]([a-z]{2,})\\.(com|lk)$")) {
            new Alert(Alert.AlertType.ERROR, "Mail text invalid or empty", ButtonType.OK).show();
            txtMail.setFocusColor(Paint.valueOf("Red"));
            txtMail.requestFocus();
            return;
        }else if(txtContact.getText().isEmpty() || !txtContact.getText().matches("^(075|077|071|074|078|076|070|072)([0-9]{7})$")) {
            new Alert(Alert.AlertType.ERROR, "Contact text invalid or empty", ButtonType.OK).show();
            txtContact.setFocusColor(Paint.valueOf("Red"));
            txtContact.requestFocus();
            return;
        }else if(txtSalary.getText().isEmpty() || !txtSalary.getText().matches("^[0-9|.]{2,}$")) {
            new Alert(Alert.AlertType.ERROR, "Salary text invalid or empty", ButtonType.OK).show();
            txtSalary.setFocusColor(Paint.valueOf("Red"));
            txtSalary.requestFocus();
            return;
        }else if (btnAddUpdate.getText().equalsIgnoreCase("Add")) {
            getNextEmployeeId();
            addEmployee();
        } else {
            updateEmployee();
        }
    }

    private void getNextEmployeeId() {
        String oldId = employeeService.getNextEmployeeId();
        System.out.println(oldId);
        if (oldId != null) {
            String[] split = oldId.split("[E]");
            int lastDigits = Integer.parseInt(split[1]);
            lastDigits++;
            String newRoomId = String.format("E%04d", lastDigits);
            lblEmpId.setText(newRoomId);
        } else {
            lblEmpId.setText("E0001");
        }
    }

    public void addEmployee() {
        if(employeeService.saveEmployee(new EmployeeDTO(lblEmpId.getText(), txtUserName.getText(), txtMail.getText(), txtContact.getText(), Double.parseDouble(txtSalary.getText())))==null){
            new Alert(Alert.AlertType.ERROR, "Failed to save the employee !", ButtonType.OK).show();
            return;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully added employee !", ButtonType.OK).show();
        setData();
    }

    private void updateEmployee() {
        if(employeeService.updateEmployee(new EmployeeDTO(lblEmpId.getText(), txtUserName.getText(), txtMail.getText(), txtContact.getText(), Double.parseDouble(txtSalary.getText())))==null){
            new Alert(Alert.AlertType.ERROR, "Failed to update the employee !", ButtonType.OK).show();
            return;
        }
        new Alert(Alert.AlertType.CONFIRMATION, "Successfully update employee !", ButtonType.OK).show();
        setData();
    }

    private void setData() {
        empIdPane.setVisible(false);
        btnAddUpdate.setText("Add");
        btnAddUpdate.setStyle("-fx-background-color:   #2f3542 ;-fx-background-radius: 20");
        setDataToTable();
        clear();
    }
}
