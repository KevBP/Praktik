package praktik;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML private TableView<Company> table;
    @FXML private TableColumn<Company, String> tc_company;
    @FXML private TableColumn<Company, String> tc_activities;
    @FXML private TableColumn<Company, String> tc_phone;
    @FXML private TableColumn<Company, String> tc_mail;
    @FXML private TableColumn<Company, String> tc_website;
    @FXML private TableColumn<Company, String> tc_state;
    @FXML private Button b_add;
    @FXML private Button b_delete;
    @FXML private Button b_save;

    private File fileOpen = null;

    @FXML
    protected void action_save_as(ActionEvent actionEvent) {
        ObservableList<Company> data = table.getItems();
        if(!data.isEmpty()) {
            // Select the path to save the file
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save File");
            Stage stage = new Stage();
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                // create and write the json in the file selected
                FileOutputStream fos = null;
                try {
                    file.createNewFile();
                    fos = new FileOutputStream(file);
                    final ObjectOutputStream oos = new ObjectOutputStream(fos);
                    data.stream().forEach(company -> {
                        try {
                            oos.writeObject(company);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    oos.close();
                    fos.close();

                    fileOpen = file;
                    b_save.setDisable(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @FXML
    protected void action_save(ActionEvent actionEvent) {
        FileOutputStream fos = null;
        try {
            fileOpen.createNewFile();
            fos = new FileOutputStream(fileOpen);
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            ObservableList<Company> data = table.getItems();
            data.stream().forEach(company -> {
                try {
                    oos.writeObject(company);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void action_load(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File");
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            ObservableList<Company> data = table.getItems();
            data.clear();
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                while(fis.available() > 0) {
                    data.add((Company) ois.readObject());
                }
                table.setItems(data);
                if(data.size() > 0) {
                    b_delete.setDisable(false);
                }
                ois.close();
                fis.close();

                fileOpen = file;
                b_save.setDisable(false);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void action_about(ActionEvent actionEvent) {
        // TODO
    }

    @FXML
    protected void action_add(ActionEvent actionEvent) throws IOException {
        // Open a window with a form to enter a new company
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_add.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add a company");
        stage.setScene(new Scene(root, 600, 330));
        stage.show();

        // Add the new company to the table's data
        CompanyController controller = loader.getController();
        controller.getData().addListener(new ListChangeListener<Company>() {
            @Override
            public void onChanged(Change<? extends Company> c) {
                ObservableList<Company> data = table.getItems();
                data.addAll(c.getList());
                b_delete.setDisable(false);
            }
        });
    }

    @FXML
    protected void action_delete(ActionEvent actionEvent) {
        ObservableList<Company> data = table.getItems();
        data.remove(table.getSelectionModel().getSelectedItem());
        table.setItems(data);
        if(data.isEmpty()) {
            b_delete.setDisable(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tc_company.setCellValueFactory(new PropertyValueFactory<Company,String>("companyName"));
        tc_activities.setCellValueFactory(new PropertyValueFactory<Company,String>("activities"));
        tc_phone.setCellValueFactory(new PropertyValueFactory<Company,String>("phone"));
        tc_mail.setCellValueFactory(new PropertyValueFactory<Company,String>("mail"));
        tc_website.setCellValueFactory(new PropertyValueFactory<Company,String>("website"));
        tc_state.setCellValueFactory(new PropertyValueFactory<Company, String>("state"));
    }
}
