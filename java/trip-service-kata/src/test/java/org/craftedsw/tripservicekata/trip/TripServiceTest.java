package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TripServiceTest {

    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTER_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_SANFRANCISCO = new Trip();
    private User loggedInUser;
    private TripService tripService;

    @Before
    public void setup(){
        tripService = new TestableTripService();
    }

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() throws Exception {
        loggedInUser = GUEST;

        tripService.getTripsByUser(UNUSED_USER);
    }

    @Test
    public void should_no_return_any_trips_when_users_are_not_friends() {
        loggedInUser = REGISTER_USER;

        User friend = new User();
        friend.addFriend(ANOTHER_USER);
        friend.addTrip(TO_SANFRANCISCO);

        List<Trip> friendTrips = tripService.getTripsByUser(friend);

        assertThat(friendTrips.size(), is(0));
    }



    private class TestableTripService extends TripService {
        @Override
        protected User getLoggedUser() {
            return loggedInUser;
        }
    }
}
