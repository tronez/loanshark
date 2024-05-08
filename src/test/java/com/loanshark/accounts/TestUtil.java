package com.loanshark.accounts;

import java.util.concurrent.atomic.AtomicLong;

public class TestUtil {
    public static final AtomicLong mobileNumber = new AtomicLong(1_000_000_000);

    public static Long getMobileNumberAtomic() {
        return mobileNumber.incrementAndGet();
    }
}
