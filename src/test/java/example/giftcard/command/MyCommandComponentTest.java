package example.giftcard.command;

import example.giftcard.api.CreateStatementCmd;
import example.giftcard.api.StatementCreatedEvt;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

public class MyCommandComponentTest {
    private FixtureConfiguration<Statement> fixture;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(Statement.class);
    }

    @Test
    public void testFirstFixture() {
        fixture.givenNoPriorActivity()
                .when(new CreateStatementCmd("1"))
                .expectSuccessfulHandlerExecution()
                .expectEvents(new StatementCreatedEvt("1"));
    }
}