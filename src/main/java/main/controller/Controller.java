package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.data.Car;
import main.data.ParkingOrder;
import main.data.ParkingSpace;
import main.data.User;
import main.model.ParkingData;
import main.utils.services.*;
import main.utils.StrategyFactory;
import main.view.View;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Controller {
    @FXML
    private ListView<Car> registeredCarListView;
    @FXML
    private ListView<ParkingSpace> freeSpacesListView;
    @FXML
    private ListView<ParkingSpace> mySpacesListView;
    @FXML
    private TableView<ParkingOrder> myOrdersTableView;
    @FXML
    private Label userLabel;
    @FXML
    private TextField markField;
    @FXML
    private TextField regNoField;
    @FXML
    private ListView<Car> carListView;
    @FXML
    private CheckBox adminCheckBox;
    @FXML
    private PasswordField passwordFieldForNewUser;
    @FXML
    private TextField nameFieldForNewUser;
    @FXML
    private TextField loginFieldForNewUser;
    @FXML
    private TableColumn<User, Integer> userIdColForAdmin;
    @FXML
    private TableColumn<User, String> userNameColForAdmin;
    @FXML
    private TableColumn<User, String> userLoginColForAdmin;
    @FXML
    private TableColumn<User, Integer> userLikesColForAdmin;
    @FXML
    private TableView<User> userTableForAdmin;
    @FXML
    private TextField newCodeField;
    @FXML
    private ListView<ParkingSpace> spacesListView;
    @FXML
    private TabPane tabPane;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;
    @FXML
    private Tab opTab;
    @FXML
    private Tab adminTab;

    private User user;

    private View view;

    public void initialize() {
        opTab.setDisable(true);
        adminTab.setDisable(true);
        view = new View();
        view.setSpaceListView(spacesListView);
        view.setUserTableForAdmin(userTableForAdmin);
        view.setCarListView(carListView);
        view.setMySpacesListView(mySpacesListView);
        view.setRegisteredCarListView(registeredCarListView);
        view.setFreeSpacesView(freeSpacesListView);
        userIdColForAdmin.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameColForAdmin.setCellValueFactory(new PropertyValueFactory<>("name"));
        userLoginColForAdmin.setCellValueFactory(new PropertyValueFactory<>("login"));
        userLikesColForAdmin.setCellValueFactory(new PropertyValueFactory<>("likes"));

        userTableForAdmin.getSelectionModel().selectedItemProperty().addListener((o,oldV,newV)->view.updateCarListView());

        view.addTextFields(nameFieldForNewUser, loginFieldForNewUser, passwordFieldForNewUser, markField, regNoField);
    }

    public void start() {
        LoginService loginService = new LoginService();
        Optional<User> optionalUser = loginService.login(loginField.getText().trim(), passwordField.getText().trim());
        if (optionalUser.isEmpty()) return;
        user = optionalUser.get();
        userLabel.setText("User: " + user.getName());
        ParkingData.getInstance().setUser(user);
        showTabs(StrategyFactory.getTabShowStrategy(user).tabNames());
    }

    private void showTabs(Set<String> tabNames) {
        List<Tab> tabsToClose = tabPane.getTabs().stream().filter(t -> !tabNames.contains(t.getId())).toList();
        for (Tab tab : tabsToClose) {
            tabPane.getTabs().remove(tab);
        }
        tabPane.getTabs().forEach(t->t.setDisable(false));
        tabPane.getSelectionModel().select(0);
        StrategyFactory.getTabShowStrategy(user).fillTabs(view);
    }

    public void addNewCode() {
        String text = newCodeField.getText();
        ParkingSpaceService pss = (ParkingSpaceService) ServiceFactory.getService(ParkingSpace.class);
        pss.add(text);
        newCodeField.clear();
        view.update();
    }

    public void removeCode() {
        ParkingSpace parkingSpace = spacesListView.getSelectionModel().getSelectedItem();
        if (parkingSpace != null) {
            ParkingSpaceService pss = (ParkingSpaceService) ServiceFactory.getService(ParkingSpace.class);
            if (pss.removeIfItPossible(parkingSpace)) {
                view.update();
            }
        }
    }

    public void addUser() {
        String login = loginFieldForNewUser.getText();
        String name = nameFieldForNewUser.getText();
        String password = passwordFieldForNewUser.getText();
        boolean admin = adminCheckBox.isSelected();
        UserService us = (UserService) ServiceFactory.getService(User.class);
        if (us.addUserIfItPossible(name, login, password, admin)) {
            view.update();
        }
    }

    public void deleteUser() {
        User u = userTableForAdmin.getSelectionModel().getSelectedItem();
        if (u != null) {
            UserService us = (UserService) ServiceFactory.getService(User.class);
            if (us.deleteUserIfItPossible(u)) {
                view.update();
            }
        }
    }

    public void addNewCar() {
        User u = userTableForAdmin.getSelectionModel().getSelectedItem();
        if (u != null) {
            String regno = regNoField.getText();
            String mark = markField.getText();
            Car car = new Car(regno, mark, u);
            AbstractService service = ServiceFactory.getService(Car.class);
            service.persist(car);
            view.update();
        }
    }

    public void deleteSelectedCar() {
        Car selectedCar = carListView.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            ServiceFactory.getService(Car.class).delete(selectedCar);
            view.update();
        }
    }

    public void clearUserSelection() {
        userTableForAdmin.getSelectionModel().clearSelection();
        view.update();
    }
}
