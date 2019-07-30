import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {
    private Vehicle car;
    private Vehicle jeep;
    private ParkingLotOwner owner;

    @BeforeEach
    void initialise() {
        car = new Vehicle();
        jeep = new Vehicle();
        owner = new ParkingLotOwner();
    }

    @Nested
    class ParkingTest {
        @Test
        void shouldParkSuccessfully() throws ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(10, owner);

            assertDoesNotThrow(() -> parkingLot.park(car));
        }

        @Test
        void shouldThrowParkingLotFullExceptionForParking() throws ParkingLotFullException, AlreadyParkedException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            parkingLot.park(car);

            assertThrows(ParkingLotFullException.class, () -> parkingLot.park(jeep));
        }

        @Test
        void shouldThrowAlreadyParkedExceptionForParkingSameVehicle() throws
                AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(2, owner);

            parkingLot.park(car);

            assertThrows(AlreadyParkedException.class, () -> parkingLot.park(car));
        }
    }

    @Nested
    class UnparkingTest {
        @Test
        void shouldThrowAnExceptionForUnparking() throws VehicleNotParkedException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            assertThrows(VehicleNotParkedException.class, () -> parkingLot.unpark(car));
        }

        @Test
        void shouldNotThrowAnExceptionForUnparking() throws ParkingLotFullException, AlreadyParkedException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            parkingLot.park(car);

            assertDoesNotThrow(() -> parkingLot.unpark(car));
        }

        @Test
        void shouldThrowVehicleNotParkedExceptionForUnparking() throws ParkingLotFullException,
                VehicleNotParkedException, AlreadyParkedException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            parkingLot.park(car);

            assertThrows(VehicleNotParkedException.class, () -> parkingLot.unpark(jeep));
        }
    }

    @Nested
    class IsParkedTest {
        @Test
        void shouldReturnTrueForParkedVehicle() throws AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            parkingLot.park(car);

            assertTrue(parkingLot.isParked(car));
        }

        @Test
        void shouldReturnFalseForUnparkedVehicle() throws AlreadyParkedException, ParkingLotFullException, VehicleNotParkedException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            assertFalse(parkingLot.isParked(car));

            parkingLot.park(car);
            parkingLot.unpark(car);

            assertFalse(parkingLot.isParked(car));
        }

        @Test
        void shouldReturnFalseForDifferentVehicle() throws ParkingLotFullException, AlreadyParkedException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            parkingLot.park(car);

            assertFalse(parkingLot.isParked(jeep));
        }
    }

    @Nested
    class IsParkingLotFullTest {
        @Test
        void shouldReturnTrueIfParkingLotFull() throws AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            parkingLot.park(car);

            assertTrue(owner.isParkingLotFull);
        }

        @Test
        void shouldReturnFalseIfParkingLotNotFull() throws AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(10, owner);

            parkingLot.park(car);

            assertFalse(owner.isParkingLotFull);
        }
    }
}

class ParkingLotOwner implements Notifiable {
    boolean isParkingLotFull;

    public void notifyFull() {
        this.isParkingLotFull = true;
    }
}