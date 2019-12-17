package com.myboost.internal.application.voucherGenerator

import com.myboost.internal.application.SpecialOffer
import java.math.BigInteger
import java.security.MessageDigest
import java.time.LocalDateTime

class VoucherGeneratorService {
    fun generate(voucherGeneratorRequest: VoucherGeneratorRequest): String {
        return hashStrings(
            voucherGeneratorRequest.offerName,
            voucherGeneratorRequest.discountPercent.toString(),
            "%",
            voucherGeneratorRequest.expirationDate.toString(),
            "!"
        )
    }

    private fun hashStrings(vararg texts: String): String {
        val string = texts.fold("") { acc, str -> acc + str }
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(string.toByteArray())).toString(16).padStart(32, '0')
    }
}