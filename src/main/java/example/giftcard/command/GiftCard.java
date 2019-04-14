package example.giftcard.command;

import example.giftcard.api.CardIssuedEvt;
import example.giftcard.api.CardRedeemedEvt;
import example.giftcard.api.IssueCardCmd;
import example.giftcard.api.RedeemCardCmd;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Profile("command")
public class GiftCard {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @AggregateIdentifier
    private String cardId;

    @AggregateMember // 1.
    private List<GiftCardTransaction> transactions = new ArrayList<>();

    private int remainingValue;

    public GiftCard() {
        log.debug("empty constructor invoked");
    }

    @CommandHandler
    public GiftCard(IssueCardCmd cmd) {
        log.debug("handling {}", cmd);
        if (cmd.getAmount() <= 0) throw new IllegalArgumentException("amount <= 0");
        apply(new CardIssuedEvt(cmd.getCardId(), cmd.getAmount()));
    }

    @CommandHandler
    public void handle(RedeemCardCmd cmd) {
        log.debug("handling {}", cmd);
        if (cmd.getAmount() <= 0) throw new IllegalArgumentException("amount <= 0");
        if (cmd.getAmount() > remainingValue) throw new IllegalStateException("amount > remaining value");
        apply(new CardRedeemedEvt(cardId, cmd.getAmount()));
    }

    @EventSourcingHandler
    public void on(CardIssuedEvt evt) {
        log.debug("applying {}", evt);
        cardId = evt.getCardId();
        remainingValue = evt.getAmount();
        log.debug("new remaining value: {}", remainingValue);
    }

    @EventSourcingHandler
    public void on(CardRedeemedEvt evt) {
        log.debug("applying {}", evt);
        transactions.add(new GiftCardTransaction(UUID.randomUUID().toString(), evt.getAmount()));
        log.debug("new remaining value: {}", remainingValue);
    }

}
