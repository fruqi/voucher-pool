package com.myboost.internal

import com.myboost.internal.application.*
import com.myboost.internal.application.repository.VoucherRepository
import com.myboost.internal.application.voucherRedemption.VoucherRedemption
import com.myboost.internal.application.voucherRedemption.VoucherRedemptionError
import com.myboost.internal.util.fold
import io.kotlintest.TestCase
import io.kotlintest.fail
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime

class TestRecipient : StringSpec() {

    private lateinit var voucherRepository: VoucherRepository
    private val voucherCode = "voucher code"
    private val email = "recipient@email.com"
    private val discountPercent = 10
    private val recipient = Recipient("recipient", email)
    private val specialOffer =
        SpecialOffer("shop name", discountPercent)

    override fun beforeTest(testCase: TestCase) {
        voucherRepository = mockk()
    }

    init {
        "should receive a discount when using a valid voucher" {
            // given
            val futureDate = LocalDateTime.now().plusDays(1)
            val voucher = Voucher(
                voucherCode,
                futureDate,
                false,
                null,
                recipient,
                specialOffer
            )
            every { voucherRepository.findVoucher(any(), any()) } returns voucher

            // when
            val voucherRedemptionResult = VoucherRedemption(
                voucherRepository
            ).redeem(voucherCode, email)

            // then
            voucherRedemptionResult.fold(
                failure = {
                    fail("it should receive discount")
                },
                success = {
                    it shouldBe 10
                }
            )
        }

        "should return INVALID_VOUCHER error when the voucher is invalid" {
            // given
            val invalidVoucher = "invalid voucher"
            val email = "recipient@email.com"
            every { voucherRepository.findVoucher(invalidVoucher, any()) } returns null

            // when
            val voucherRedemptionResult = VoucherRedemption(
                voucherRepository
            ).redeem(invalidVoucher, email)

            // then
            voucherRedemptionResult.fold(
                failure = {
                    it shouldBe VoucherRedemptionError.INVALID_VOUCHER
                },
                success = {
                    fail("it should return an error")
                }
            )
        }

        "should return EXPIRED_VOUCHER error when redeeming expired voucher code" {
            // given
            val pastDate = LocalDateTime.now().minusDays(1)
            val voucher = Voucher(
                voucherCode,
                pastDate,
                false,
                null,
                recipient,
                specialOffer
            )
            every { voucherRepository.findVoucher(any(), any()) } returns voucher

            // when
            val voucherRedemptionResult = VoucherRedemption(
                voucherRepository
            ).redeem(voucherCode, email)

            // then
            voucherRedemptionResult.fold(
                failure = {
                    it shouldBe VoucherRedemptionError.EXPIRED_VOUCHER
                },
                success = {
                    fail("it should return an error")
                }
            )
        }

        "should return USED_VOUCHER error when redeeming used voucher code" {
            // given
            val futureDate = LocalDateTime.now().plusDays(1)
            val voucher = Voucher(
                voucherCode,
                futureDate,
                true,
                null,
                recipient,
                specialOffer
            )
            every { voucherRepository.findVoucher(any(), any()) } returns voucher

            // when
            val voucherRedemptionResult = VoucherRedemption(
                voucherRepository
            ).redeem(voucherCode, email)

            // then
            voucherRedemptionResult.fold(
                failure = {
                    it shouldBe VoucherRedemptionError.USED_VOUCHER
                },
                success = {
                    fail("it should return an error")
                }
            )
        }
    }

}
