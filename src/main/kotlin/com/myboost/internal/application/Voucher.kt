package com.myboost.internal.application

import java.time.LocalDateTime

data class Voucher(
    val code: String,
    val expirationDate: LocalDateTime,
    val isUsed: Boolean,
    val dateOfUse: LocalDateTime?,
    val recipient: Recipient,
    val specialOffer: SpecialOffer
) {
    val isExpired: Boolean
        get() = this.expirationDate < LocalDateTime.now()

    val discountPercent: Int
        get() = this.specialOffer.discountPercent
}
