package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.springframework.beans.factory.annotation.Autowired;

public class TripService {

    @Autowired
    private TripDAO tripDAO;

    public List<Trip> getFriendsTrips(User friend, User loggedInUser) {
        if (loggedInUser == null) {
            throw new UserNotLoggedInException();
        }

        return friend.isFriendWith(loggedInUser) ? getTripsBy(friend) : noTrips();
    }

    private ArrayList<Trip> noTrips() {
        return new ArrayList<>();
    }

    protected List<Trip> getTripsBy(User user) {
        return tripDAO.tripsBy(user);
    }
}
