package praktik;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class MainController {
    public TableView<Company> table;
    public TableColumn<Company, String> tc_company;
    public TableColumn<Company, String> tc_activities;
    public TableColumn<Company, String> tc_phone;
    public TableColumn<Company, String> tc_mail;
    public TableColumn<Company, String> tc_website;
    public TableColumn<Company, String> tc_state;
    public Button b_add;
    public Button b_delete;
    public Button b_save;
    private ObservableList<Company> data = FXCollections.observableArrayList();
    private File fileOpen = null;

    public void action_save_as(ActionEvent actionEvent) {
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

    public void action_save(ActionEvent actionEvent) {
        FileOutputStream fos = null;
        try {
            fileOpen.createNewFile();
            fos = new FileOutputStream(fileOpen);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void action_load(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load File");
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            data.clear();
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(file);
                ois = new ObjectInputStream(fis);
                while(fis.available() > 0) {
                    data.add((Company) ois.readObject());
                }
                tc_company.setCellValueFactory(new PropertyValueFactory<Company,String>("companyName"));
                tc_activities.setCellValueFactory(new PropertyValueFactory<Company,String>("activities"));
                tc_phone.setCellValueFactory(new PropertyValueFactory<Company,String>("phone"));
                tc_mail.setCellValueFactory(new PropertyValueFactory<Company,String>("mail"));
                tc_website.setCellValueFactory(new PropertyValueFactory<Company,String>("website"));
                tc_state.setCellValueFactory(new PropertyValueFactory<Company,String>("state"));
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

    public void action_about(ActionEvent actionEvent) {
        // TODO
    }

    public void action_add(ActionEvent actionEvent) throws IOException {
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
                data.addAll(c.getList());
                b_delete.setDisable(false);
            }
        });

        tc_company.setCellValueFactory(new PropertyValueFactory<Company,String>("companyName"));
        tc_activities.setCellValueFactory(new PropertyValueFactory<Company,String>("activities"));
        tc_phone.setCellValueFactory(new PropertyValueFactory<Company,String>("phone"));
        tc_mail.setCellValueFactory(new PropertyValueFactory<Company,String>("mail"));
        tc_website.setCellValueFactory(new PropertyValueFactory<Company,String>("website"));
        tc_state.setCellValueFactory(new PropertyValueFactory<Company,String>("state"));
        table.setItems(data);
    }

    public void action_delete(ActionEvent actionEvent) {
        data.remove(table.getSelectionModel().getSelectedItem());
        table.setItems(data);
        if(data.isEmpty()) {
            b_delete.setDisable(true);
        }
    }
}
