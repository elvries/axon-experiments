package example.giftcard.command;

import example.giftcard.api.StatementCreatedEvt;
import example.giftcard.api.TransactionRegisteredEvt;
import example.giftcard.api.CreateStatementCmd;
import example.giftcard.api.RegisterTransactionCmd;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Profile("command")
public class Statement {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AggregateIdentifier
    private String cardId;

    private Instant settledDate;

    @AggregateMember // 1.
    private List<TransactionFee> transactionFees = new ArrayList<>();

    public Statement() {
        log.debug("empty constructor invoked");
    }

    @CommandHandler
    public Statement(CreateStatementCmd cmd) {
        log.debug("handling {}", cmd);
        apply(new StatementCreatedEvt(cmd.getCardId()));
    }

    @CommandHandler
    public void handle(RegisterTransactionCmd cmd) {
        log.debug("handling {}", cmd);
        apply(new TransactionRegisteredEvt(cardId, cmd.getTimestamp(), cmd.getType()));
    }

    @EventSourcingHandler
    public void on(StatementCreatedEvt evt) {
        log.debug("applying {}", evt);
        cardId = evt.getCardId();
    }

    @EventSourcingHandler
    public void on(TransactionRegisteredEvt evt) {
        log.debug("applying {}", evt);
        transactionFees.add(new TransactionFee(UUID.randomUUID().toString(), evt.getType()));
    }

}
