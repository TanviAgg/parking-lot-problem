import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.AbstractList;
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
    private ParkingLot parkingLot1 = new ParkingLot(1, owner);
    private ParkingLot parkingLot2 = new ParkingLot(1, owner);

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
        parkingLot1 = new ParkingLot(1, owner);
        parkingLot2 = new ParkingLot(1, owner);
    }

    @Nested
    class AttendantParkingTest {
        @Test
        void shouldParkSuccessfully(){
            parkingLots.add(parkingLot1);
            attendant = new ParkingLotAttendant(parkingLots);

            assertDoesNotThrow(() -> attendant.valetPark(car));
        }

        @Test
        void shouldNotThrowAnExceptionForUnparking() throws UnableToUnparkException, UnableToParkException {
            parkingLots.add(parkingLot1);
            attendant = new ParkingLotAttendant(parkingLots);
            attendant.valetPark(car);

            assertDoesNotThrow(() -> attendant.valetUnpark(car));
        }

        @Test
        void shouldThrowVehicleNotParkedExceptionForUnparking() throws UnableToUnparkException, UnableToParkException {
            parkingLots.add(parkingLot1);
            attendant = new ParkingLotAttendant(parkingLots);
            attendant.valetPark(car);

            assertThrows(UnableToUnparkException.class, () -> attendant.valetUnpark(jeep));
        }
    }

    @Nested
    class MultipleParkingLotsTest {
        @Test
        void shouldParkSuccessfully() throws UnableToParkException {
            parkingLots.add(parkingLot1);
            parkingLots.add(parkingLot2);
            attendant = new ParkingLotAttendant(parkingLots);

            attendant.valetPark(car);

            assertDoesNotThrow(() -> attendant.valetPark(jeep));
        }

        @Test
        void shouldNotThrowAnExceptionForUnparking() throws UnableToUnparkException, UnableToParkException {
            parkingLots.add(parkingLot1);
            parkingLots.add(parkingLot2);
            attendant = new ParkingLotAttendant(parkingLots);
            attendant.valetPark(car);

            assertDoesNotThrow(() -> attendant.valetUnpark(car));
        }

        @Test
        void shouldThrowUnableToParkExceptionForParking() throws UnableToUnparkException, UnableToParkException {
            parkingLots.add(parkingLot1);
            parkingLots.add(parkingLot2);
            attendant = new ParkingLotAttendant(parkingLots);
            attendant.valetPark(car);

            assertThrows(UnableToParkException.class, () -> attendant.valetPark(car));
        }
    }
}
