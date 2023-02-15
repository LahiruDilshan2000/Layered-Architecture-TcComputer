package lk.ijse.tccomputer.util;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.tccomputer.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;


public class Navigation {
    private static AnchorPane anchorPane;

    public static void navigate(Routes route, AnchorPane anchorPane){
        Navigation.anchorPane=anchorPane;
        Navigation.anchorPane.getChildren().clear();
        Stage window = (Stage) Navigation.anchorPane.getScene().getWindow();

        switch (route){
            case CreateAccount  :window.setTitle("Create Account Form");
                                initUI("CreateAccountForm",true);
                                break;
            case Login          :window.setTitle("Login Form");
                                initUI("LoginForm",true);
                                 break;
            case LoginBack      :window.setTitle("Login Form");
                                initUI("LoginForm",false);
                                break;
            case AdminDashBoardFrom :window.setTitle("Admin Dashboard");
                                initUI("AdminDashBoardForm",false);
                                break;
            case EmployeesForm  :window.setTitle("Employee Form");
                                initUI("EmployeesForm",true);
                                break;
            case SupplierForm  :window.setTitle("Supplier Form");
                                initUI("SupplierForm",true);
                                break;
            case PlaceOrderForm :window.setTitle("Place Order Form");
                                initUI("PlaceOrderForm",true);
                                break;
            case PlaceRepairForm :window.setTitle("Place Repair Form");
                                initUI("PlaceRepairForm",true);
                                break;
            case SuppliesForm   :window.setTitle("Place Supplies Form");
                                initUI("SuppliesForm",true);
                                break;
            case ReceptionForm  :window.setTitle("Reception Form");
                                initUI("ReceptionDashBoardForm",false);
                                break;
            case StockManageForm:window.setTitle("Stock Mange Form");
                                initUI("StockForm",true);
                                break;
            case CustomerForm   :window.setTitle("Customer Mange Form");
                                initUI("CustomerForm",true);
                                break;
            case IncomeForm     :window.setTitle("Income Form");
                                initUI("IncomeForm",true);
                                break;
            case StockManageDashBoardForm:window.setTitle("Stock Manage Form");
                                initUI("StockManageDashBordForm",false);
                                break;
        }
    }
    public static void initUI(String location,boolean flag){
        try {
            if(flag) {
                Parent root=FXMLLoader.load(Navigation.class.getResource("/lk/ijse/tccomputer/resources/view/" +location+".fxml"));
                Navigation.anchorPane.getChildren().add(root);
                root.translateXProperty().set(Navigation.anchorPane.getWidth());

                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
                KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(t -> {
                    Navigation.anchorPane.getChildren().remove(anchorPane);
                });
                timeline.play();
                //Navigation.anchorPane.getChildren().add(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/techlacomputer/view/" +location+".fxml")));
            }else{
                Stage stage = (Stage) Navigation.anchorPane.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(Navigation.class.getResource("/lk/ijse/tccomputer/resources/view/" +location+".fxml"))));
                stage.centerOnScreen();
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void navigateReport(InputStream inputStream) {
        try {
            JasperReport compileReport = JasperCompileManager.compileReport(inputStream);
            JasperPrint jasperPrint = JasperFillManager
                    .fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}

