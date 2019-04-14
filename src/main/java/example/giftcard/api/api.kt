package example.giftcard.api

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class IssueCardCmd(@TargetAggregateIdentifier val cardId: String, val amount: Int)
data class CardIssuedEvt(val cardId: String, val amount: Int)
data class RedeemCardCmd(@TargetAggregateIdentifier val cardId: String, val amount: Int)
data class CardRedeemedEvt(val cardId: String, val amount: Int)
data class ReimburseCardCmd(val transactionId: String)
data class CardReimbursedEvt(val transactionId: String)
