import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParkingLotTest {
    @Test
    void shouldReturnTrueForParking(){
        ParkingLot parkingLot = new ParkingLot(10);

        boolean result = parkingLot.parkCar();

        assertTrue(result);
    }

    @Test
    void shouldReturnFalseForParking(){
        ParkingLot parkingLot = new ParkingLot(0);

        boolean result = parkingLot.parkCar();

        assertFalse(result);
    }
}
