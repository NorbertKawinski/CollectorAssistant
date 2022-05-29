package net.kawinski.collecting;

import lombok.extern.slf4j.Slf4j;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Arquillian.class)
@Slf4j
public class BasicContainerTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return CaTestHelper.createFullTestArchive();
    }

    @Test
    public void should_inject_bean_instance() {
        assertThat(log, notNullValue());
        log.info("Hello, container works!");
    }

}
