package main.model;

import lombok.Getter;
import lombok.Setter;
import main.model.data.User;

/**
 * This class implements Singleton pattern
 * contains Parking data about user.
 * Can be used for storing some extra data
 */
public class ParkingData {
    private static ParkingData instance;

    private ParkingData() {}

    public static ParkingData getInstance() {
        if (instance == null) instance = new ParkingData();
        return instance;
    }

    @Getter
    @Setter
    private User user;
}
