package parkmate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ParkingBookingSystem {
    private int availableSpots;
    private int bookedSpots;
    private ArrayList<Booking> bookings;

    
    public ParkingBookingSystem(int totalSpots) {
        this.availableSpots = totalSpots;
        this.bookedSpots = 0;
        this.bookings = new ArrayList<>();
    }

    
    public class Booking {
        String userName;
        String plateNumber;
        String bookingTime;

        Booking(String userName, String plateNumber, String bookingTime) {
            this.userName = userName;
            this.plateNumber = plateNumber;
            this.bookingTime = bookingTime;
        }

        @Override
        public String toString() {
            return "User: " + userName + ", Plate: " + plateNumber + ", Time: " + bookingTime;
        }
    }
public boolean bookParking(String userName, String plateNumber, String bookingTime) {
    if (availableSpots > 0) {
        availableSpots--;
        bookedSpots++;

        
        bookings.add(new Booking(userName, plateNumber, bookingTime));
        return true;
    } else {
        return false;
    }
}
    
    public boolean bookParking(String userName, String plateNumber) {
        if (availableSpots > 0) {
            availableSpots--;
            bookedSpots++;

            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String bookingTime = sdf.format(new Date());

            
            bookings.add(new Booking(userName, plateNumber, bookingTime));
            return true;
        } else {
            return false;
        }
    }
 
public boolean cancelBooking(String userName, String plateNumber, String bookingTime) {
    for (Booking booking : bookings) {
        if (booking.userName.equals(userName) && booking.plateNumber.equals(plateNumber) && booking.bookingTime.equals(bookingTime)) {
            bookings.remove(booking);
            availableSpots++;
            bookedSpots--;
            return true;
        }
    }
    return false;
}

    
    public int getAvailableSpots() {
        return availableSpots;
    }

    
    public int getBookedSpots() {
        return bookedSpots;
    }

    
    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    
    public void resetSystem() {
        this.availableSpots = 10; 
        this.bookedSpots = 0;
        this.bookings.clear();
    }
}