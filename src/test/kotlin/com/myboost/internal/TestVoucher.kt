package com.myboost.internal

import com.myboost.internal.application.Recipient
import com.myboost.internal.application.SpecialOffer
import com.myboost.internal.application.Voucher
import com.myboost.internal.application.voucherGenerator.VoucherGeneratorRequest
import com.myboost.internal.application.voucherGenerator.VoucherGeneratorService
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.time.LocalDateTime

class TestVoucher : StringSpec() {

    private val fixedExpiryDate = LocalDateTime.of(2019, 11, 1, 13, 30, 59)

    init {
        "should generate voucher code given special offer and expiration date" {
            // given
            val voucherGeneratorRequest = VoucherGeneratorRequest(
                "Shopee-11-11",
                11,
                fixedExpiryDate
            )
            val voucherCode = "25ad2e449760891daedec4ce383ef136"

            // when
            val generatedVoucherCode = VoucherGeneratorService().generate(voucherGeneratorRequest)

            // then
            generatedVoucherCode shouldBe voucherCode
        }

        "voucher should have code, expiration date, used flag, date of use" {
            val code = "abc-132"
            val expirationDate = fixedExpiryDate.plusDays(10)
            val isUsed = false
            val dateOfUse = fixedExpiryDate
            val recipient =
                Recipient("recipient name", "recipient@email.com")
            val specialOffer = SpecialOffer("shop name", 10)

            val voucher = Voucher(
                code,
                expirationDate,
                isUsed,
                dateOfUse,
                recipient,
                specialOffer
            )

            voucher.code shouldBe code
            voucher.expirationDate shouldBe expirationDate
            voucher.isUsed shouldBe isUsed
            voucher.dateOfUse shouldBe dateOfUse
        }

        "should use voucher code only once" {
            // given

            // when

            // then

        }
    }
}

