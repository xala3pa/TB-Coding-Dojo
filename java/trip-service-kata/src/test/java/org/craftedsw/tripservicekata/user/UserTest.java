package org.craftedsw.tripservicekata.user;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserTest {
    private static final User GORSKI = new User();
    private static final User UNCLE_BOB = new User();

    @Test
    public void should_inform_when_users_are_not_friends() throws Exception {
        User user = UserBuilder.aUser()
                .friendsWith(GORSKI)
                .build();

        assertThat(user.isFriendWith(UNCLE_BOB), is(false));
    }

    @Test
    public void should_inform_when_users_are_friends() throws Exception {
        User user = UserBuilder.aUser()
                .friendsWith(GORSKI, UNCLE_BOB)
                .build();

        assertThat(user.isFriendWith(UNCLE_BOB), is(true));
    }
}
