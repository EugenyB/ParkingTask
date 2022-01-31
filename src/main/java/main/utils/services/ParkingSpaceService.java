package main.utils.services;

import main.data.ParkingSpace;
import main.utils.DataBaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParkingSpaceService extends AbstractService {

    private final String FIND_ALL_PLACES = "select * from parking_space";
    private final String FIND_FREE_PLACES = "select * from parking_space where occupied=0";
    private final String ADD_CODE = "insert into parking_space (code) values (?)";
    private final String FIND_BY_ID = "select * from parking_space where id = ? limit 1";

    private List<ParkingSpace> findByQuery(String query) {
        List<ParkingSpace> result = new ArrayList<>();
        try {
            Connection connection = DataBaseManager.getInstance().getConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                int occupied = rs.getInt("occupied");
                result.add(new ParkingSpace(id, code, occupied));
            }
        } catch (SQLException ignored) {}
        return result;
    }

    public List<ParkingSpace> findAll() {
        return findByQuery(FIND_ALL_PLACES);
    }

    public List<ParkingSpace> findFree() {
        return findByQuery(FIND_FREE_PLACES);
    }

    public boolean add(String code) {
        try (PreparedStatement ps = DataBaseManager.getInstance().getConnection().prepareStatement(ADD_CODE)) {
            ps.setString(1, code);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeIfItPossible(ParkingSpace parkingSpace) {
        if (parkingSpace.getOccupied() == 0) {
            try (PreparedStatement ps = DataBaseManager.getInstance().getConnection().prepareStatement("delete from parking_space where id = ?")) {
                ps.setInt(1, parkingSpace.getId());
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public ParkingSpace findById(Object id) {
        int pId = (Integer)id;
        try (PreparedStatement ps = DataBaseManager.getInstance().getConnection().prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, pId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String code = rs.getString("code");
                int occupied = rs.getInt("occupied");
                return new ParkingSpace(pId, code, occupied);
            } else return null;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public boolean persist(Object o) {
        ParkingSpace ps = (ParkingSpace) o;
        return add(ps.getCode());
    }

    @Override
    public void delete(Object o) {
        ParkingSpace ps = (ParkingSpace) o;
        removeIfItPossible(ps);
    }
}
