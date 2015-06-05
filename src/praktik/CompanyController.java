package praktik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CompanyController {
    public TextField tf_company;
    public TextArea tf_activities;
    public TextField tf_phone;
    public TextField tf_website;
    public TextArea tf_mail;
    public ComboBox cb_state;
    public Button b_cancel;
    public Button b_valid;

    private ObservableList<Company> data = FXCollections.observableArrayList();

    public ObservableList<Company> getData() {
        return data;
    }

    public void action_state(ActionEvent actionEvent) {
    }

    public void action_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) b_cancel.getScene().getWindow();
        stage.close();
    }

    public void action_valid(ActionEvent actionEvent) {
        Stage stage = (Stage) b_valid.getScene().getWindow();
        data.add(new Company(tf_company.getText(), tf_activities.getText(), tf_phone.getText(), tf_mail.getText(), tf_website.getText(), cb_state.getValue().toString()));
        stage.close();
    }
}
