package praktik;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

public class CompanyController {
    public ComboBox cb_state;

    public void action_state(ActionEvent actionEvent) {
        ObservableList<String> options = FXCollections.observableArrayList("Option 1", "Option 2", "Option 3");
        cb_state.setItems(options);
    }

    public void action_cancel(ActionEvent actionEvent) {
    }

    public void action_valid(ActionEvent actionEvent) {
    }
}
