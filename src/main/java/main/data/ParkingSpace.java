package main.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingSpace {
    private int id;
    private String code;
    private int occupied;

    public ParkingSpace(int id, String code) {
        this.id = id;
        this.code = code;
    }

    @Override
    public String toString() {
        return code + "   " + (occupied==0 ? "(   )" : "( + )");
    }
}
