package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

public class TripService {

    public List<Trip> getTripsByUser(User user, User loggedInUser) {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }

        return user.isFriendWith(loggedInUser) ? getTripsBy(user) : noTrips();
    }

    private ArrayList<Trip> noTrips() {
        return new ArrayList<>();
    }

    protected List<Trip> getTripsBy(User user) {
        return TripDAO.findTripsByUser(user);
    }
}
