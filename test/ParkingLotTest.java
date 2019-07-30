import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {
    private Vehicle car;

    @BeforeEach
    void initialise(){
        Vehicle car = new Vehicle();
    }

    @Test
    void shouldParkSuccessfully() throws ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(10);

        assertDoesNotThrow(()->parkingLot.park(car));
    }

    @Test
    void shouldThrowAnExceptionForParking() throws ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(1);

        parkingLot.park(car);

        assertThrows(ParkingLotFullException.class, () -> parkingLot.park(car));
    }

    @Test
    void shouldThrowAnExceptionForUnparking() throws ParkingLotEmptyException {
        ParkingLot parkingLot = new ParkingLot(1);

        assertThrows(ParkingLotEmptyException.class, () -> parkingLot.unpark(car));
    }

    @Test
    void shouldNotThrowAnExceptionForUnparking() throws ParkingLotFullException {
        ParkingLot parkingLot = new ParkingLot(1);

        parkingLot.park(car);

        assertDoesNotThrow(() -> parkingLot.unpark(car));
    }
}
