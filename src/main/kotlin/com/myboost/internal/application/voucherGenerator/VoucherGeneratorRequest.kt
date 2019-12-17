package com.myboost.internal.application.voucherGenerator

import java.time.LocalDateTime

data class VoucherGeneratorRequest(val offerName: String, val discountPercent: Int, val expirationDate: LocalDateTime)

