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
    class AttendantParkingTest {
        @Test
        void shouldParkSuccessfully(){
            ParkingLot parkingLot = new ParkingLot(1, owner);
            attendant = new ParkingLotAttendant(parkingLot);

            assertDoesNotThrow(() -> attendant.valetPark(car));
        }

        @Test
        void shouldNotThrowAnExceptionForUnparking() throws UnableToUnparkException, UnableToParkException {
            ParkingLot parkingLot = new ParkingLot(1, owner);
            attendant = new ParkingLotAttendant(parkingLot);
            attendant.valetPark(car);

            assertDoesNotThrow(() -> attendant.valetUnpark(car));
        }

        @Test
        void shouldThrowVehicleNotParkedExceptionForUnparking() throws UnableToUnparkException, UnableToParkException {
            ParkingLot parkingLot = new ParkingLot(1, owner);
            attendant = new ParkingLotAttendant(parkingLot);
            attendant.valetPark(car);

            assertThrows(UnableToUnparkException.class, () -> attendant.valetUnpark(jeep));
        }
    }
}
