package net.perspilling.asset;

import net.perspilling.asset.api.Saying;
import com.google.common.base.Optional;
import net.perspilling.asset.resources.HelloWorldResource;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Copied from: http://nbsoftsolutions.com/blog/getting-started-with-dropwizard-testing
 */
public class HelloWorldResourceTest {

    private HelloWorldResource resource;

    @Before
    public void setup() {
        // Before each test, we re-instantiate our resource so it will reset
        // the counter. It is good practice when dealing with a class that
        // contains mutable data to reset it so tests can run independently
        // of each other.
        resource = new HelloWorldResource("Hello, %s", "Stranger");
    }

    @Test
    public void idStartsAtOne() {
        Saying result = resource.sayHello(Optional.of("dropwizard"));
        assertThat(result.getId()).isEqualTo(1);
    }

    @Test
    public void idIncrementsByOne() {
        Saying result = resource.sayHello(Optional.of("dropwizard"));
        Saying result2 = resource.sayHello(Optional.of("dropwizard2"));

        assertThat(result2.getId()).isEqualTo(result.getId() + 1);
    }

    @Test
    public void absentNameReturnsDefaultName() {
        Saying result = resource.sayHello(Optional.<String>absent());
        assertThat(result.getContent()).contains("Stranger");
    }

    @Test
    public void nameReturnsName() {
        Saying result = resource.sayHello(Optional.of("dropwizard"));
        assertThat(result.getContent()).contains("dropwizard");
    }
}
