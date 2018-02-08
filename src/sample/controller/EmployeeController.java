package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.Employee;
import sample.model.EmployeeDAO;

import java.sql.Date;
import java.sql.SQLException;
import javafx.scene.input.MouseEvent;

/**
 * Created by ONUR BASKIRT on 23.02.2016.
 */
public class EmployeeController {

    @FXML
    private TextField empIdText;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextField newEmailText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField surnameText;
    @FXML
    private TextField emailText;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableColumn<Employee, Integer>  empIdColumn;
    @FXML
    private TableColumn<Employee, String>  empNameColumn;
    @FXML
    private TableColumn<Employee, String> empLastNameColumn;
    @FXML
    private TableColumn<Employee, String> empEmailColumn;
    @FXML
    private TableColumn<Employee, String> empPhoneNumberColumn;
    @FXML
    private TableColumn<Employee, Date> empHireDateColumn;

    //Search an employee
    @FXML
    private void searchEmployee (ActionEvent actionEvent) throws ClassNotFoundException, SQLException {
        try {
            //Get Employee information
            Employee emp = EmployeeDAO.searchEmployee(empIdText.getText());
            //Populate Employee on TableView and Display on TextArea
            populateAndShowEmployee(emp);
        } catch (SQLException e) {
            e.printStackTrace();
            resultArea.setText("Error occurred while getting employee information from DB.\n" + e);
            throw e;
        }
    }

     @FXML
    void mouseclick(MouseEvent event) {
 
        Employee selectedItem = employeeTable.getSelectionModel().getSelectedItem();
        
         System.out.println("name"+selectedItem.getFirstName());
             nameText.setText(selectedItem.getFirstName());
             surnameText.setText(selectedItem.getLastName());
              emailText.setText(selectedItem.getEmail());
//              String.parseString(selectedItem.getEmployeeId());
              empIdText.setText(selectedItem.getEmployeeId()+"");
             //empIdText.setText(selectedItem.getEmployeeId());
    }
    
//     @FXML
//    void mouseclickaction(MouseEvent event) throws SQLException, ClassNotFoundException {
////        Employee selectedItems = employeeTable.getSelectionModel().getSelectedItems();
////        nameText.setText(selectedItems.);
//        Employee selectedItem = employeeTable.getSelectionModel().getSelectedItem();
//        
//         System.out.println("name"+selectedItem.getFirstName());
//             nameText.setText(selectedItem.getFirstName());
//             surnameText.setText(selectedItem.getLastName());
//             emailText.setText(selectedItem.getEmail());
//             
//             
//             
////             resultArea.setText("First Name: " + selectedItems.getFirstName() + "\n" +
////                "Last Name: " + selectedItems.getLastName());
//            
//         
//    }
    //Search all employees
    @FXML
    private void searchEmployees(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            //Get all Employees information
            ObservableList<Employee> empData = EmployeeDAO.searchEmployees();
            //Populate Employees on TableView
            populateEmployees(empData);
        } catch (SQLException e){
            System.out.println("Error occurred while getting employees information from DB.\n" + e);
            throw e;
        }
    }

    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize () {
       
        empIdColumn.setCellValueFactory(cellData -> cellData.getValue().employeeIdProperty().asObject());
        empNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        empLastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
       empEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
//        empPhoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().phoneNumberProperty());
//        empHireDateColumn.setCellValueFactory(cellData -> cellData.getValue().hireDateProperty());
    }

    //Populate Employee
    @FXML
    private void populateEmployee (Employee emp) throws ClassNotFoundException {
        //Declare and ObservableList for table view
        ObservableList<Employee> empData = FXCollections.observableArrayList();
        //Add employee to the ObservableList
        empData.add(emp);
        //Set items to the employeeTable
        employeeTable.setItems(empData);
    }

    //Set Employee information to Text Area
    @FXML
    private void setEmpInfoToTextArea ( Employee emp) {
        resultArea.setText("First Name: " + emp.getFirstName() + "\n" +
                "Last Name: " + emp.getLastName());
        
        nameText.setText(emp.getFirstName());
        surnameText.setText(emp.getLastName());
        emailText.setText(emp.getEmail());
        //empIdText.setText(emp.);
    }
//set data to textfields
    
    
    @FXML
    private void setEmpInfoToTextFields ( Employee emp) {
        
        String Firstname=emp.getFirstName();
        nameText.setText(Firstname);
        
    }
    //Populate Employee for TableView and Display Employee on TextArea
    @FXML
    private void populateAndShowEmployee(Employee emp) throws ClassNotFoundException {
        if (emp != null) {
            populateEmployee(emp);
            setEmpInfoToTextArea(emp);
        } else {
            resultArea.setText("This employee does not exist!\n");
        }
    }

    //Populate Employees for TableView
    @FXML
    private void populateEmployees (ObservableList<Employee> empData) throws ClassNotFoundException {
        //Set items to the employeeTable
        employeeTable.setItems(empData);
    }
@FXML
    void editfunction(ActionEvent event) throws SQLException, ClassNotFoundException {
       // Employee selectedItem = employeeTable.getSelectionModel().getSelectedItem();
       //String a= selectedItem.getEmployeeId()+"";
EmployeeDAO.updateEmpEmail(empIdText.getText(),nameText.getText(),surnameText.getText(),emailText.getText());
 ObservableList<Employee> empData = EmployeeDAO.searchEmployees();
            //Populate Employees on TableView
            populateEmployees(empData);
    }
    //Update employee's email with the email which is written on newEmailText field
//    @FXML
//    private void updateEmployeeEmail (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
//        try {
//            EmployeeDAO.updateEmpEmail(empIdText.getText(),newEmailText.getText());
//            resultArea.setText("Email has been updated for, employee id: " + empIdText.getText() + "\n");
//        } catch (SQLException e) {
//            resultArea.setText("Problem occurred while updating email: " + e);
//        }
//    }

    //Insert an employee to the DB
    @FXML
    private void insertEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.insertEmp(nameText.getText(),surnameText.getText(),emailText.getText());
            resultArea.setText("Employee inserted! \n");
            ObservableList<Employee> empData = EmployeeDAO.searchEmployees();
            //Populate Employees on TableView
            populateEmployees(empData);
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while inserting employee " + e);
            throw e;
        }
    }

    //Delete an employee with a given employee Id from DB
    @FXML
    private void deleteEmployee (ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            EmployeeDAO.deleteEmpWithId(empIdText.getText());
             ObservableList<Employee> empData = EmployeeDAO.searchEmployees();
            //Populate Employees on TableView
            populateEmployees(empData);
            Employee selectedItem = employeeTable.getSelectionModel().getSelectedItem();
            resultArea.setText("Employee deleted! Employee id: " + empIdText.getText() + "\n");
            nameText.setText("");
            surnameText.setText("");
        emailText.setText("");
        empIdText.setText("");
            //employeeTable.setItems(empData);
            
        } catch (SQLException e) {
            resultArea.setText("Problem occurred while deleting employee " + e);
            throw e;
        }
    }
}