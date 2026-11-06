package com.example.ui.controller;

import com.example.data.exception.StudentNotFoundException;
import com.example.data.exception.ValidationException;
import com.example.data.pojo.Student;
import com.example.data.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;

@Component
public class StudentController {

    @FXML private TableView<Student> tableView;
    @FXML private TextField searchField;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField ageField;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Label messageLabel;
    @FXML private Label statusLabel;

    private final StudentService studentService;

    private final ObservableList<Student> data =
            FXCollections.observableArrayList();

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @FXML
    public void initialize() {
        tableView.setItems(data);

        tableView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        fillForm(newSel);
                    }
                });

        var noSelection = tableView.getSelectionModel()
                .selectedItemProperty()
                .isNull();

        btnUpdate.disableProperty().bind(noSelection);
        btnDelete.disableProperty().bind(noSelection);

        loadData();
    }

    private void loadData() {
        data.setAll(studentService.findAll());
        statusLabel.setText(data.size() + " student(s)");
    }

    @FXML
    private void handleSearch() {
        data.setAll(
                studentService.search(searchField.getText())
        );

        statusLabel.setText(data.size() + " student(s)");
        messageLabel.setText("");
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        handleClear();
        loadData();
        showInfo("Data refreshed");
    }

    private Student readForm() {
        String name = nameField.getText();
        String email = emailField.getText();

        int age;

        try {
            age = Integer.parseInt(ageField.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Age must be a number"
            );
        }

        return new Student(name, email, age);
    }

    @FXML
    private void handleAdd() {
        try {
            Student student = readForm();
            Student saved = studentService.create(student);

            showInfo("Added: " + saved.getName());

            handleClear();
            loadData();

        } catch (ValidationException e) {
            showError(e.getMessage());

        } catch (Exception e) {
            showError("System error: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Student selected = tableView
                .getSelectionModel()
                .getSelectedItem();

        if (selected == null) {
            showError("Please select a student");
            return;
        }

        try {
            Student student = readForm();
            student.setId(selected.getId());

            Student updated =
                    studentService.update(student);

            showInfo("Updated: " + updated.getName());

            handleClear();
            loadData();

        } catch (ValidationException |
                 StudentNotFoundException e) {

            showError(e.getMessage());

        } catch (Exception e) {
            showError("System error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = tableView
                .getSelectionModel()
                .getSelectedItem();

        if (selected == null) {
            showError("Please select a student");
            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirm.setTitle("Confirm");
        confirm.setHeaderText("Confirm delete");
        confirm.setContentText(
                "Delete " + selected.getName() + "?"
        );

        confirm.initOwner(
                tableView.getScene().getWindow()
        );

        confirm.showAndWait()
                .filter(result ->
                        result == ButtonType.OK
                )
                .ifPresent(result -> {
                    try {
                        studentService.delete(
                                selected.getId()
                        );

                        showInfo(
                                "Deleted: " +
                                        selected.getName()
                        );

                        handleClear();
                        loadData();

                    } catch (StudentNotFoundException e) {
                        showError(e.getMessage());
                        loadData();

                    } catch (Exception e) {
                        showError(
                                "System error: " +
                                        e.getMessage()
                        );
                    }
                });
    }

    @FXML
    private void handleClear() {
        idField.clear();
        nameField.clear();
        emailField.clear();
        ageField.clear();

        tableView.getSelectionModel()
                .clearSelection();

        nameField.requestFocus();
    }

    private void fillForm(Student student) {
        idField.setText(
                String.valueOf(student.getId())
        );

        nameField.setText(student.getName());
        emailField.setText(student.getEmail());

        ageField.setText(
                String.valueOf(student.getAge())
        );
    }

    private void showInfo(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle(
                "-fx-text-fill: green;"
        );
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setStyle(
                "-fx-text-fill: red;"
        );
    }
}