package example.giftcard.command;

import example.giftcard.api.IssueCardCmd;
import example.giftcard.api.CardIssuedEvt;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class MyCommandComponentTest {
    private FixtureConfiguration<GiftCard> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(GiftCard.class);
    }

    @Test
    public void testFirstFixture() {
        fixture.givenNoPriorActivity()
                .when(new IssueCardCmd("1", 10))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new CardIssuedEvt("1", 10));
    }
}