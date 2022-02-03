package main.model.utils.services.data;

import main.model.data.Car;
import main.model.data.ParkingOrder;
import main.model.data.ParkingSpace;
import main.model.data.User;

public class ServiceFactory {
    public static AbstractService getService(Class<?> clazz) {
        if (clazz == ParkingSpace.class) {
            return new ParkingSpaceService();
        } else if (clazz == User.class) {
            return new UserService();
        } else if (clazz == Car.class) {
            return new CarService();
        } else if (clazz == ParkingOrder.class) {
            return new ParkingOrderService();
        }
        return null;
    }
}
