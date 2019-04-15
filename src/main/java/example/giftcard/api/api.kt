package example.giftcard.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.time.Instant

data class CreateStatementCmd(@TargetAggregateIdentifier val cardId: String)
data class StatementCreatedEvt(val cardId: String)
data class RegisterTransactionCmd(@TargetAggregateIdentifier val cardId: String, val timestamp: Instant, val type: String)
data class TransactionRegisteredEvt(val cardId: String, val timestamp: Instant, val type: String)
data class ReimburseCardCmd(val transactionId: String)
data class CardReimbursedEvt(val transactionId: String)
