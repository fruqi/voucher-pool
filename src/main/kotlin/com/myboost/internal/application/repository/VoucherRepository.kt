package com.myboost.internal.application.repository

import com.myboost.internal.application.Voucher

interface VoucherRepository {
    fun findVoucher(voucherCode: String, email: String): Voucher?
    fun redeemVoucher(voucherCode: String, email: String)
}