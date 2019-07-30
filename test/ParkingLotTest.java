import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {
    private Vehicle car;
    private Vehicle jeep;

    @BeforeEach
    void initialise() {
        car = new Vehicle();
        jeep = new Vehicle();
    }

    @Nested
    class ParkingTest {
        @Test
        void shouldParkSuccessfully() throws ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(10);

            assertDoesNotThrow(() -> parkingLot.park(car));
        }

        @Test
        void shouldThrowParkingLotFullExceptionForParking() throws ParkingLotFullException, AlreadyParkedException {
            ParkingLot parkingLot = new ParkingLot(1);

            parkingLot.park(car);

            assertThrows(ParkingLotFullException.class, () -> parkingLot.park(jeep));
        }

        @Test
        void shouldThrowAlreadyParkedExceptionForParkingSameVehicle() throws
                AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(2);

            parkingLot.park(car);

            assertThrows(AlreadyParkedException.class, () -> parkingLot.park(car));
        }
    }

    @Nested
    class UnparkingTest {
        @Test
        void shouldThrowAnExceptionForUnparking() throws VehicleNotParkedException {
            ParkingLot parkingLot = new ParkingLot(1);

            assertThrows(VehicleNotParkedException.class, () -> parkingLot.unpark(car));
        }

        @Test
        void shouldNotThrowAnExceptionForUnparking() throws ParkingLotFullException, AlreadyParkedException {
            ParkingLot parkingLot = new ParkingLot(1);

            parkingLot.park(car);

            assertDoesNotThrow(() -> parkingLot.unpark(car));
        }

        @Test
        void shouldThrowVehicleNotParkedExceptionForUnparking() throws ParkingLotFullException,
                VehicleNotParkedException, AlreadyParkedException {
            ParkingLot parkingLot = new ParkingLot(1);

            parkingLot.park(car);

            assertThrows(VehicleNotParkedException.class, () -> parkingLot.unpark(jeep));
        }
    }
}
