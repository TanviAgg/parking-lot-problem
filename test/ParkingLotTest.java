import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotTest {
    private Vehicle car;
    private Vehicle jeep;
    private Notifiable owner1;
    private Notifiable airportSecurity;
    private List<Notifiable> owner = new ArrayList<>();
    private List<Notifiable> all = new ArrayList<>();
    private ParkingLotAttendant attendant;

    @BeforeEach
    void initialise() {
        car = new Vehicle();
        jeep = new Vehicle();
        owner1 = mock(Notifiable.class);
        airportSecurity = mock(Notifiable.class);
        owner.add(owner1);
        all.add(owner1);
        all.add(airportSecurity);
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
        void shouldNotifyIfParkingLotFull() throws AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            parkingLot.park(car);

            verify(owner1).notifyFull(parkingLot);
        }

        @Test
        void shouldNotCallIfParkingLotNotFull() throws AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(10, owner);

            parkingLot.park(car);

            verify(owner1, times(0)).notifyFull(parkingLot);
        }

        @Test
        void shouldCallBothNotifiablesIfParkingLotFull() throws AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(1, all);

            parkingLot.park(car);

            verify(owner1, times(1)).notifyFull(parkingLot);
            verify(airportSecurity, times(1)).notifyFull(parkingLot);
        }
    }

    @Nested
    class IsParkingLotEmptyTest {
        @Test
        void shouldNotifyTwiceIfParkingLotBecomesEmpty() throws AlreadyParkedException, ParkingLotFullException, VehicleNotParkedException {
            ParkingLot parkingLot = new ParkingLot(1, owner);

            parkingLot.park(car);
            parkingLot.unpark(car);
            parkingLot.park(car);
            parkingLot.unpark(car);

            verify(owner1, times(2)).notifyEmpty(parkingLot);
        }

        @Test
        void shouldNotCallIfParkingLotNotEmpty() throws AlreadyParkedException, ParkingLotFullException {
            ParkingLot parkingLot = new ParkingLot(10, owner);

            parkingLot.park(car);

            verify(owner1, times(0)).notifyEmpty(parkingLot);
        }

        @Test
        void shouldCallBothNotifiablesIfParkingLotBecomesEmpty() throws AlreadyParkedException, ParkingLotFullException, VehicleNotParkedException {
            ParkingLot parkingLot = new ParkingLot(1, all);

            parkingLot.park(car);
            parkingLot.unpark(car);

            verify(owner1, times(1)).notifyEmpty(parkingLot);
            verify(airportSecurity, times(1)).notifyEmpty(parkingLot);
        }
    }
}
