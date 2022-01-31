package main.view;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import lombok.Data;

import main.data.Car;
import main.data.ParkingSpace;
import main.data.User;

import main.utils.services.ParkingSpaceService;
import main.utils.services.ServiceFactory;
import main.utils.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class View {

    private ListView<ParkingSpace> spaceListView;
    private ListView<Car> carListView;

    private TableView<User> userTableForAdmin;

    private List<TextField> textFields = new ArrayList<>();

    public void update() {
        updateSpaceListView();
        updateUserTableForAdmin();
        updateCarListView();
        clearAllTextFields();
    }

    public void addTextFields(TextField... tf) {
        textFields.addAll(List.of(tf));
    }

    private void clearAllTextFields() {
        textFields.forEach(TextInputControl::clear);
    }

    private void updateUserTableForAdmin() {
//        UserService us = (UserService) ServiceFactory.getService(User.class);
        userTableForAdmin.setItems(FXCollections.observableList(Objects.requireNonNull(ServiceFactory.getService(User.class)).findAll()));
    }

    private void updateSpaceListView() {
//        ParkingSpaceService pss = (ParkingSpaceService) ServiceFactory.getService(ParkingSpace.class);
        spaceListView.setItems(FXCollections.observableList(Objects.requireNonNull(ServiceFactory.getService(ParkingSpace.class)).findAll()));
    }

    public void updateCarListView() {
        User user = userTableForAdmin.getSelectionModel().getSelectedItem();
        if (user == null) {
            carListView.setItems(FXCollections.observableList(Objects.requireNonNull(ServiceFactory.getService(Car.class)).findAll()));
        } else {
            carListView.setItems(FXCollections.observableList(Objects.requireNonNull(ServiceFactory.getService(Car.class)).findByFK(user.getId())));
        }
    }

}