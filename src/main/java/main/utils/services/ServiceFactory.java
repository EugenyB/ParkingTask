package main.utils.services;

import main.data.Car;
import main.data.ParkingSpace;
import main.data.User;

public class ServiceFactory {
    public static AbstractService getService(Class<?> clazz) {
        if (clazz == ParkingSpace.class) {
            return new ParkingSpaceService();
        } else if (clazz == User.class) {
            return new UserService();
        } else if (clazz == Car.class) {
            return new CarService();
        }
        return null;
    }
}
