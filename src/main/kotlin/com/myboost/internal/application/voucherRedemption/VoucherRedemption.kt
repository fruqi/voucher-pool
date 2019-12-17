package com.myboost.internal.application.voucherRedemption

import com.myboost.internal.application.repository.VoucherRepository
import com.myboost.internal.util.Either
import com.myboost.internal.util.error
import com.myboost.internal.util.value

typealias VoucherDiscountPercent = Int

typealias VoucherRedemptionResult = Either<VoucherRedemptionError, VoucherDiscountPercent>

enum class VoucherRedemptionError {
    INVALID_VOUCHER,
    EXPIRED_VOUCHER,
    USED_VOUCHER
}

class VoucherRedemption(private val voucherRepository: VoucherRepository) {
    fun redeem(voucherCode: String, email: String): VoucherRedemptionResult {
        val voucher = voucherRepository.findVoucher(voucherCode, email)

        return when {
            voucher == null -> error(VoucherRedemptionError.INVALID_VOUCHER)
            voucher.isExpired -> error(VoucherRedemptionError.EXPIRED_VOUCHER)
            voucher.isUsed -> error(VoucherRedemptionError.USED_VOUCHER)
            else -> {
//                voucherRepository.redeemVoucher(voucherCode, email)
                value(voucher.discountPercent)
            }
        }
    }
}

