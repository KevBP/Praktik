package praktik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {
    public TableView table;
    public TableColumn tc_company;
    public TableColumn tc_activities;
    public TableColumn tc_phone;
    public TableColumn tc_mail;
    public TableColumn tc_website;
    public TableColumn tc_state;
    private ObservableList<Company> data = FXCollections.observableArrayList();

    public void action_save(ActionEvent actionEvent) {
    }

    public void action_load(ActionEvent actionEvent) {
    }

    public void action_about(ActionEvent actionEvent) {
    }

    public void action_add(ActionEvent actionEvent) {
        data.add(new Company("Test", "Fait pleins de tests", "0666666666", "contact@test.tt", "test.tt", "non contacté"));
        tc_company.setCellValueFactory(new PropertyValueFactory<Company,String>("companyName"));
        tc_activities.setCellValueFactory(new PropertyValueFactory<Company,String>("activities"));
        tc_phone.setCellValueFactory(new PropertyValueFactory<Company,String>("phone"));
        tc_mail.setCellValueFactory(new PropertyValueFactory<Company,String>("mail"));
        tc_website.setCellValueFactory(new PropertyValueFactory<Company,String>("website"));
        tc_state.setCellValueFactory(new PropertyValueFactory<Company,String>("state"));
        table.setItems(data);
    }

    public void action_delete(ActionEvent actionEvent) {
    }
}
