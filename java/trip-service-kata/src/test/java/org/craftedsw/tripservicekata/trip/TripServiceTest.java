package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.craftedsw.tripservicekata.user.UserBuilder.aUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

    private static final User GUEST = null;
    private static final User UNUSED_USER = null;
    private static final User REGISTER_USER = new User();
    private static final User ANOTHER_USER = new User();
    private static final Trip TO_SAN_FRANCISCO = new Trip();
    private static final Trip TO_SPAIN = new Trip();
    private List<Trip> friendTrips;

    @Mock
    private TripDAO tripDAO;

    @InjectMocks
    private TripService tripService = new TripService();

    @Test(expected = UserNotLoggedInException.class)
    public void should_throw_an_exception_when_user_is_not_logged_in() throws Exception {
        tripService.getFriendsTrips(UNUSED_USER, GUEST);
    }

    @Test
    public void should_no_return_any_trips_when_users_are_not_friends() {
        User friend = aUser()
                .friendsWith(ANOTHER_USER)
                .withTrips(TO_SAN_FRANCISCO)
                .build();

        friendTrips = tripService.getFriendsTrips(friend, REGISTER_USER);

        assertThat(friendTrips.size(), is(0));
    }

    @Test
    public void should_return_trips_when_users_are_friends() {
        User friend = aUser()
                .friendsWith(REGISTER_USER)
                .withTrips(TO_SAN_FRANCISCO, TO_SPAIN)
                .build();

        given(tripDAO.tripsBy(friend)).willReturn(friend.trips());

        friendTrips = tripService.getFriendsTrips(friend, REGISTER_USER);

        assertThat(friendTrips.size(), is(2));
    }
}
