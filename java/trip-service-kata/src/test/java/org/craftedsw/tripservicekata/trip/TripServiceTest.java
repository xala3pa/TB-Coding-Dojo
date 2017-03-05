package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import static org.craftedsw.tripservicekata.user.UserBuilder.aUser;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.craftedsw.tripservicekata.user.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TripServiceTest {

    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTER_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_SAN_FRANCISCO = new Trip();
    private static final Trip TO_SPAIN = new Trip();
    private TripService tripService;
    private List<Trip> friendTrips;

    @Before
    public void setup() {
        tripService = new TestableTripService();
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() throws Exception {
        tripService.getTripsByUser(UNUSED_USER, GUEST);
    }

    @Test
    public void should_no_return_any_trips_when_users_are_not_friends() {
        User friend = aUser()
                .friendsWith(ANOTHER_USER)
                .withTrips(TO_SAN_FRANCISCO)
                .build();

        friendTrips = tripService.getTripsByUser(friend, REGISTER_USER);

        assertThat(friendTrips.size(), is(0));
    }

    @Test
    public void should_return_trips_when_users_are_friends() {
        User friend = aUser()
                .friendsWith(REGISTER_USER)
                .withTrips(TO_SAN_FRANCISCO, TO_SPAIN)
                .build();

        friendTrips = tripService.getTripsByUser(friend, REGISTER_USER);

        assertThat(friendTrips.size(), is(2));
    }


    private class TestableTripService extends TripService {
        @Override
        protected List<Trip> getTripsBy(User user) {
            return user.trips();
        }
    }

}
