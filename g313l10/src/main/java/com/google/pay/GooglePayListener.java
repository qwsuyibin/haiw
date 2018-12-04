package com.google.pay;

import com.google.pay.googlepay.Purchase;

public interface GooglePayListener {
    void PayOver(Purchase pdata);
}
