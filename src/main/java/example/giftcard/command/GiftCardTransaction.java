package example.giftcard.command;

import example.giftcard.api.CardReimbursedEvt;
import example.giftcard.api.ReimburseCardCmd;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

public class GiftCardTransaction {

    @EntityId // 2.
    private String transactionId;

    private int transactionValue;
    private boolean reimbursed = false;

    GiftCardTransaction(String transactionId, int transactionValue) {
        this.transactionId = transactionId;
        this.transactionValue = transactionValue;
    }

    @CommandHandler
    public void handle(ReimburseCardCmd cmd) {
        if (reimbursed) {
            throw new IllegalStateException("Transaction already reimbursed");
        }
        apply(new CardReimbursedEvt(transactionId));
    }

    @EventSourcingHandler
    public void on(CardReimbursedEvt event) {
        // 2.
        if (transactionId.equals(event.getTransactionId())) {
            reimbursed = true;
        }
    }
}