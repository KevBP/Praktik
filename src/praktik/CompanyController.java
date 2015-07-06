package praktik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import praktik.model.Company;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanyController implements Initializable {
    @FXML private TextField tf_company;
    @FXML private TextField tf_activities;
    @FXML private TextField tf_phone;
    @FXML private TextField tf_website;
    @FXML private TextField tf_mail;
    @FXML private TextField tf_street;
    @FXML private TextField tf_city;
    @FXML private TextField tf_province;
    @FXML private TextField tf_zip;
    @FXML private TextField tf_country;
    @FXML private ComboBox cb_state;
    @FXML private Button b_cancel;
    @FXML private Button b_valid;

    private ObservableList<Company> data = FXCollections.observableArrayList();

    @FXML
    protected void action_state(ActionEvent actionEvent) {
    }

    @FXML
    protected void action_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) b_cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void action_valid(ActionEvent actionEvent) {
        Stage stage = (Stage) b_valid.getScene().getWindow();
        data.add(new Company(tf_company.getText(), tf_activities.getText(), tf_phone.getText(), tf_mail.getText(), tf_website.getText(), tf_street.getText(), tf_city.getText(), tf_province.getText(), tf_zip.getText(), tf_country.getText(), cb_state.getValue().toString(), null, null, null, null, null));
        stage.close();
    }

    public ObservableList<Company> getData() {
        return data;
    }

    public void clean() {
        tf_company.clear();
        tf_company.requestFocus();
        tf_activities.clear();
        tf_phone.clear();
        tf_website.clear();
        tf_mail.clear();
        tf_street.clear();
        tf_city.clear();
        tf_province.clear();
        tf_zip.clear();
        tf_country.clear();
        cb_state.setValue(cb_state.getItems().get(0));
        data.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clean();
    }
}
