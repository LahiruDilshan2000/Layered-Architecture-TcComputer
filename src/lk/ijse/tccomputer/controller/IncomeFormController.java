package lk.ijse.tccomputer.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import lk.ijse.tccomputer.dto.IncomeDTO;
import lk.ijse.tccomputer.dto.OutComeDTO;
import lk.ijse.tccomputer.service.ServiceFactory;
import lk.ijse.tccomputer.service.ServiceType;
import lk.ijse.tccomputer.service.custom.IncomeService;
import lk.ijse.tccomputer.service.custom.OutComeService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class IncomeFormController {
    public JFXComboBox cmbYear;
    public JFXComboBox cmbMonth;
    public JFXComboBox cmbOutcomeYear;
    public JFXComboBox cmbOutcomeMonth;
    public Label lblIncomeTotal;
    public LineChart lineChart;
    public LineChart outcomeLineChart;
    public Label lblEarning;
    public Label lblExpenses;
    public PieChart pieChart;
    public Label lblOutComeTotal;

    public IncomeService incomeService;
    public OutComeService outcomeService;

    public void initialize() {

        this.incomeService = ServiceFactory.getInstances().getService(ServiceType.INCOME);
        this.outcomeService = ServiceFactory.getInstances().getService(ServiceType.OUTCOME);
        initializeComboBox();
        initializeIncomeLineChart();
        initializeOutcomeLineChart();
        initializePieChart();
        setIncomeTotal();
        setOutComeTotal();
    }

    private void initializeComboBox() {

        List<Integer> incomeYearList = incomeService.getYear().stream().map(incomeDTO -> incomeDTO.getYear()).collect(Collectors.toList());
        cmbYear.setItems(FXCollections.observableArrayList(incomeYearList));
        cmbYear.getSelectionModel().selectLast();

        cmbMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cmbMonth.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
        cmbMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null!=newValue){
                initializeIncomeLineChart();
                initializePieChart();
                setIncomeTotal();
            }
        });

        List<Integer> outComeList = outcomeService.getYear().stream().map(outComeDTO -> outComeDTO.getYear()).collect(Collectors.toList());
        cmbOutcomeYear.setItems(FXCollections.observableArrayList(outComeList));
        cmbOutcomeYear.getSelectionModel().selectLast();

        cmbOutcomeMonth.getItems().addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        cmbOutcomeMonth.getSelectionModel().select(LocalDate.now().getMonthValue() - 1);
        cmbOutcomeMonth.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (null!=newValue){
                initializeOutcomeLineChart();
                initializePieChart();
                setOutComeTotal();
            }
        });
    }

    private void setIncomeTotal() {

        lblIncomeTotal.setText(String.valueOf(incomeService.getMonthlyTotal(Integer.parseInt(cmbYear.getSelectionModel().getSelectedItem().toString()), cmbMonth.getSelectionModel().getSelectedIndex()+1)));

    }

    private void setOutComeTotal() {

        lblOutComeTotal.setText(String.valueOf(outcomeService.getMonthlyTotal(Integer.parseInt(cmbOutcomeYear.getSelectionModel().getSelectedItem().toString()), cmbOutcomeMonth.getSelectionModel().getSelectedIndex()+1)));

    }

    private void initializePieChart() {

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Income", incomeService.getMonthlyTotal(Integer.parseInt(cmbYear.getSelectionModel().getSelectedItem().toString()), cmbMonth.getSelectionModel().getSelectedIndex() + 1)), new PieChart.Data("Expenses", outcomeService.getMonthlyTotal(Integer.parseInt(cmbOutcomeYear.getSelectionModel().getSelectedItem().toString()), cmbOutcomeMonth.getSelectionModel().getSelectedIndex() + 1)));
        pieChart.setData(pieChartData);

    }

    private void initializeOutcomeLineChart() {

        lblExpenses.setText(cmbOutcomeYear.getSelectionModel().getSelectedItem().toString() + "  " + cmbOutcomeMonth.getSelectionModel().getSelectedItem().toString());
        List<OutComeDTO> tmList = outcomeService.getMonthlyOutCome(Integer.parseInt(cmbOutcomeYear.getSelectionModel().getSelectedItem().toString()), cmbOutcomeMonth.getSelectionModel().getSelectedIndex()+1);

        XYChart.Series series = new XYChart.Series();
        for (OutComeDTO outcomeDTO : tmList) {
            String x = String.valueOf(outcomeDTO.getDay());
            int y = (int) outcomeDTO.getTotal();
            series.getData().add(new XYChart.Data(x, y));
        }
        outcomeLineChart.getData().clear();
        outcomeLineChart.getData().addAll(series);
        outcomeLineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.getNode().setStyle("-fx-stroke: #3ae374");

    }

    private void initializeIncomeLineChart() {

        lblEarning.setText(cmbYear.getSelectionModel().getSelectedItem().toString() + "  " + cmbMonth.getSelectionModel().getSelectedItem().toString());
        List<IncomeDTO> tmList = incomeService.getMonthlyIncome(Integer.parseInt(cmbYear.getSelectionModel().getSelectedItem().toString()), cmbMonth.getSelectionModel().getSelectedIndex()+1);

        XYChart.Series series = new XYChart.Series();
        for (IncomeDTO incomeDTO : tmList) {
            String x = String.valueOf(incomeDTO.getDay());
            int y = (int) incomeDTO.getTotal();
            series.getData().add(new XYChart.Data(x, y));
        }
        lineChart.getData().clear();
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent");
        series.getNode().setStyle("-fx-stroke: #EA2027");
    }
}
