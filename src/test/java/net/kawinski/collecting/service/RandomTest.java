package net.kawinski.collecting.service;

import net.kawinski.collecting.CaTestHelper;
import net.kawinski.collecting.data.model.User;
import net.kawinski.collecting.service.user.UserRegistrationForm;
import net.kawinski.collecting.service.user.UserService;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Provides a place for making ad-hoc temporary tests.
 */
@RunWith(Arquillian.class)
public class RandomTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return CaTestHelper.createFullTestArchive();
    }

    @Inject
    private UserService userService;

    @Test
    public void you_shall_pass() {
        final User user1 = userService.register(new UserRegistrationForm("testUser", "testing@example.com", "123456", "123456", true));
        assertThat(user1.getId(), notNullValue());
        assertThat(user1.getName(), is("testUser"));
        //noinspection OptionalGetWithoutIsPresent
        final User user2 = userService.findByNameOrEmail("testUser").get();
        assertThat(user2, is(user1));
    }

}