package com.example.ui.controller;

import com.example.data.exception.ValidationException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import com.example.data.pojo.Student;
import com.example.data.service.StudentService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class StudentController {

    private final StudentService studentService;

    private final ObservableList<Student> data =
            FXCollections.observableArrayList();

    @FXML
    private TableView<Student> table;

    @FXML
    private TableColumn<Student, Long> idColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private TableColumn<Student, Integer> ageColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField searchField;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(
                cell -> new SimpleObjectProperty<>(
                        cell.getValue().getId()
                )
        );

        nameColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getName()
                )
        );

        emailColumn.setCellValueFactory(
                cell -> new SimpleStringProperty(
                        cell.getValue().getEmail()
                )
        );

        ageColumn.setCellValueFactory(
                cell -> new SimpleObjectProperty<>(
                        cell.getValue().getAge()
                )
        );

        table.setItems(data);

        table.getSelectionModel()
                .selectedItemProperty()
                .addListener(
                        (observable, oldValue, newValue) ->
                                fillForm(newValue)
                );

        loadAll();
    }

    private void loadAll() {
        data.setAll(studentService.findAll());
    }

    @FXML
    private void handleSearch() {
        data.setAll(
                studentService.search(
                        searchField.getText()
                )
        );
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadAll();
        clearForm();
    }

    private void fillForm(Student student) {
        if (student == null) {
            return;
        }

        nameField.setText(student.getName());
        emailField.setText(student.getEmail());
        ageField.setText(
                String.valueOf(student.getAge())
        );
    }

    private void clearForm() {
        table.getSelectionModel().clearSelection();

        nameField.clear();
        emailField.clear();
        ageField.clear();
    }

    private Student readForm() {
        String name = nameField.getText();
        String email = emailField.getText();

        int age;

        try {
            age = Integer.parseInt(
                    ageField.getText().trim()
            );
        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "Age must be a number"
            );
        }

        return new Student(
                name,
                email,
                age
        );
    }

    @FXML
    private void handleAdd() {
        try {
            Student student = readForm();

            studentService.create(student);

            loadAll();
            clearForm();

            showInfo(
                    "Student added successfully"
            );
        } catch (RuntimeException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        Student selected =
                table.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {
            showError(
                    "Please select a student to update"
            );
            return;
        }

        try {
            Student edit = readForm();
            edit.setId(selected.getId());

            studentService.update(edit);

            loadAll();
            clearForm();

            showInfo(
                    "Student updated successfully"
            );
        } catch (RuntimeException e) {
            showError(e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        Student selected =
                table.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {
            showError(
                    "Please select a student to delete"
            );
            return;
        }

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText(
                "Delete selected student?"
        );

        confirm.setContentText(
                selected.getName()
        );

        Optional<ButtonType> result =
                confirm.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            try {
                studentService.delete(
                        selected.getId()
                );

                loadAll();
                clearForm();

                showInfo(
                        "Student deleted successfully"
                );
            } catch (RuntimeException e) {
                showError(e.getMessage());
            }
        }
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION
        );

        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(
                Alert.AlertType.ERROR
        );

        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}