import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {
    @Test
    void shouldReturnTrueForParking() throws ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(10);

        parkingLot.parkCar();
    }

    @Test
    void shouldReturnFalseForParking() throws ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.parkCar();
        assertThrows(ParkingLotFullException.class, () -> parkingLot.parkCar());
    }
}
