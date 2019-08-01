import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParkingLotAttendantTest {
    private Vehicle car;
    private Vehicle jeep;
    private Notifiable owner1;
    private Notifiable airportSecurity;
    private List<Notifiable> owner = new ArrayList<>();
    private List<Notifiable> all = new ArrayList<>();
    private ParkingLotAttendant attendant;
    private List<ParkingLot> parkingLots;
    private ParkingLot lotWithSize1;
    private ParkingLot otherLotWithSize1;
    private ParkingLot lotWithSize2;

    @BeforeEach
    void initialise() {
        car = new Vehicle();
        jeep = new Vehicle();
        owner1 = mock(Notifiable.class);
        airportSecurity = mock(Notifiable.class);
        owner.add(owner1);
        all.add(owner1);
        all.add(airportSecurity);
        parkingLots = new ArrayList<ParkingLot>();
        lotWithSize1 = new ParkingLot(1, owner);
        otherLotWithSize1 = new ParkingLot(1, owner);
        lotWithSize2 = new ParkingLot(2, owner);
    }

    @Nested
    class AttendantParkingTest {
        @Test
        void shouldParkSuccessfully(){
            parkingLots.add(lotWithSize1);
            attendant = new ParkingLotAttendant(parkingLots, ParkingLotAttendant.Strategy.SEQUENTIAL);

            assertDoesNotThrow(() -> attendant.valetPark(car));
        }

        @Test
        void shouldNotThrowAnExceptionForUnparking() throws UnableToUnparkException, UnableToParkException {
            parkingLots.add(lotWithSize1);
            attendant = new ParkingLotAttendant(parkingLots, ParkingLotAttendant.Strategy.SEQUENTIAL);
            attendant.valetPark(car);

            assertDoesNotThrow(() -> attendant.valetUnpark(car));
        }

        @Test
        void shouldThrowVehicleNotParkedExceptionForUnparking() throws UnableToUnparkException, UnableToParkException {
            parkingLots.add(lotWithSize1);
            attendant = new ParkingLotAttendant(parkingLots, ParkingLotAttendant.Strategy.SEQUENTIAL);
            attendant.valetPark(car);

            assertThrows(UnableToUnparkException.class, () -> attendant.valetUnpark(jeep));
        }
    }

    @Nested
    class SequentialParkingTest {
        @Test
        void shouldParkSuccessfully() throws UnableToParkException {
            parkingLots.add(lotWithSize1);
            parkingLots.add(otherLotWithSize1);
            attendant = new ParkingLotAttendant(parkingLots, ParkingLotAttendant.Strategy.SEQUENTIAL);

            attendant.valetPark(car);

            assertDoesNotThrow(() -> attendant.valetPark(jeep));
        }

        @Test
        void shouldNotThrowAnExceptionForUnparking() throws UnableToUnparkException, UnableToParkException {
            parkingLots.add(lotWithSize1);
            parkingLots.add(otherLotWithSize1);
            attendant = new ParkingLotAttendant(parkingLots, ParkingLotAttendant.Strategy.SEQUENTIAL);
            attendant.valetPark(car);

            assertDoesNotThrow(() -> attendant.valetUnpark(car));
        }

        @Test
        void shouldThrowUnableToParkExceptionForParkingSameCar() throws UnableToUnparkException, UnableToParkException {
            parkingLots.add(lotWithSize1);
            parkingLots.add(otherLotWithSize1);
            attendant = new ParkingLotAttendant(parkingLots, ParkingLotAttendant.Strategy.SEQUENTIAL);
            attendant.valetPark(car);

            assertThrows(UnableToParkException.class, () -> attendant.valetPark(car));
        }
    }

    @Nested
    class DistributedParkingTest {
        @Test
        void shouldParkInLotWithMoreAvailableSlots() throws UnableToParkException {
            parkingLots.add(lotWithSize1);
            parkingLots.add(lotWithSize2);
            attendant = new ParkingLotAttendant(parkingLots, ParkingLotAttendant.Strategy.DISTRIBUTED);

            attendant.valetPark(car);

            assertTrue(lotWithSize2.isParked(car));
        }
    }
}
