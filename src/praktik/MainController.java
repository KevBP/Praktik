package praktik;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import praktik.model.Company;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainController implements Initializable {
    @FXML private TableView<Company> table;
    @FXML private TableColumn<Company, String> tc_company;
    @FXML private TableColumn<Company, String> tc_activities;
    @FXML private TableColumn<Company, String> tc_phone;
    @FXML private TableColumn<Company, String> tc_mail;
    @FXML private TableColumn<Company, String> tc_website;
    @FXML private TableColumn<Company, String> tc_street;
    @FXML private TableColumn<Company, String> tc_city;
    @FXML private TableColumn<Company, String> tc_province;
    @FXML private TableColumn<Company, String> tc_zip;
    @FXML private TableColumn<Company, String> tc_country;
    @FXML private TableColumn<Company, String> tc_state;
    @FXML private Button b_add;
    @FXML private Button b_delete;
    @FXML private Button b_save;
    @FXML private Label l_file;

    private Stage formStage;
    private CompanyController formController;
    private Stage about;
    private File fileOpen = null;
    private ObservableList<String> stateValues = FXCollections.observableArrayList("Uncontacted", "Contacted", "First reminder", "Second reminder", "Positive response", "Negative response");
    private Timer timer = new Timer();

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
                    l_file.setText(fileOpen.getName());
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
                l_file.setText(fileOpen.getName());
                b_save.setDisable(false);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    protected void action_about(ActionEvent actionEvent) {
        about.show();
        table.getChildrenUnmodifiable().stream().forEach(node -> node.getStyleClass().clear());
    }

    @FXML
    protected void action_add(ActionEvent actionEvent) throws IOException {
        formController.clean();
        formStage.show();
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
        table.setTableMenuButtonVisible(true);

        tc_company.setCellValueFactory(new PropertyValueFactory<Company, String>("companyName"));
        tc_activities.setCellValueFactory(new PropertyValueFactory<Company, String>("activities"));
        tc_phone.setCellValueFactory(new PropertyValueFactory<Company,String>("phone"));
        tc_mail.setCellValueFactory(new PropertyValueFactory<Company,String>("mail"));
        tc_website.setCellValueFactory(new PropertyValueFactory<Company,String>("website"));
        tc_street.setCellValueFactory(new PropertyValueFactory<Company, String>("street"));
        tc_city.setCellValueFactory(new PropertyValueFactory<Company, String>("city"));
        tc_province.setCellValueFactory(new PropertyValueFactory<Company, String>("province"));
        tc_zip.setCellValueFactory(new PropertyValueFactory<Company, String>("zip"));
        tc_country.setCellValueFactory(new PropertyValueFactory<Company, String>("country"));
        tc_state.setCellValueFactory(new PropertyValueFactory<Company, String>("state"));

        // Setting cells
        tc_company.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_company.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCompanyName(t.getNewValue());
            }
        });
        tc_activities.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_activities.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setActivities(t.getNewValue());
            }
        });
        tc_phone.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_phone.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setPhone(t.getNewValue());
            }
        });
        tc_mail.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_mail.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setMail(t.getNewValue());
            }
        });
        tc_website.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_website.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setWebsite(t.getNewValue());
            }
        });
        tc_street.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_street.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setStreet_address(t.getNewValue());
            }
        });
        tc_city.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_city.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCity(t.getNewValue());
            }
        });
        tc_province.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_province.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setProvince(t.getNewValue());
            }
        });
        tc_zip.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_zip.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setZip_code(t.getNewValue());
            }
        });
        tc_country.setCellFactory(TextFieldTableCell.forTableColumn());
        tc_country.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company)t.getTableView().getItems().get(t.getTablePosition().getRow())).setCountry(t.getNewValue());
            }
        });
        //tc_state.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), stateValues));
        tc_state.setCellFactory(new Callback<TableColumn<Company, String>, TableCell<Company, String>>() {
            @Override
            public TableCell<Company, String> call(TableColumn<Company, String> param) {
                return new TableCell<Company, String>(){
                    private ComboBox comboBox;

                    @Override
                    public void startEdit() {
                        if (!isEmpty()) {
                            super.startEdit();

                            setText(null);
                            if(comboBox == null) {
                                createComboBox();
                            }
                            setGraphic(comboBox);
                            setText(null);
                        }
                    }

                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();

                        setText(getItem());
                        setGraphic(null);
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        this.getTableRow().getStyleClass().removeAll("uncontacted", "positive", "negative");

                        if (empty) {
                            setText(item);
                            setGraphic(null);
                        }
                        else {
                            if (isEditing()) {
                                comboBox.setValue(getItem());
                                setText(null);
                                setGraphic(comboBox);
                            }
                            else {
                                setText(item);
                                setGraphic(null);
                                switch (item) {
                                    case "Uncontacted":
                                        this.getTableRow().getStyleClass().add("uncontacted");
                                        break;
                                    case "Positive response":
                                        this.getTableRow().getStyleClass().add("positive");
                                        break;
                                    case "Negative response":
                                        this.getTableRow().getStyleClass().add("negative");
                                        break;
                                    default:
                                        //this.getTableRow().getStyleClass().removeAll("uncontacted", "positive", "negative");
                                        break;
                                }
                            }
                        }
                    }

                    private void createComboBox() {
                        comboBox = new ComboBox(stateValues);
                        comboBox.setValue(getItem());
                        comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent key) {
                                if (key.getCode() == KeyCode.ENTER) {
                                    commitEdit(comboBox.getValue().toString());
                                }
                                else if (key.getCode() == KeyCode.ESCAPE) {
                                    cancelEdit();
                                }
                            }
                        });
                    }
                };
            }
        });
        tc_state.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Company, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Company, String> t) {
                ((Company) t.getTableView().getItems().get(t.getTablePosition().getRow())).setState(t.getNewValue());
            }
        });


        // Open a window with a form to enter a new company
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/form_add.fxml"));
        try {
            Parent root = loader.load();
            formStage = new Stage();
            formStage.setTitle("Add a company");
            formStage.setScene(new Scene(root, 600, 500));
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        // About
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("view/about.fxml"));
        try {
            Parent root = loader2.load();
            about = new Stage();
            about.setTitle("About Praktik");
            about.setScene(new Scene(root, 400, 350));
            //about.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        // Add a listener to add new companies to the table's data
        formController = loader.getController();
        formController.getData().

        addListener(new ListChangeListener<Company>() {
            @Override
            public void onChanged(Change<? extends Company> c) {
                ObservableList<Company> data = table.getItems();
                data.addAll(c.getList());
                b_delete.setDisable(false);
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(fileOpen != null) {
                    action_save(null);
                }
            }
        }, 60000, 60000);
    }

    public void terminate () {
        timer.cancel();
    }
}
