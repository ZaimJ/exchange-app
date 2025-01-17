package com.compagny.service;

import com.compagny.dto.BillRequest;
import com.compagny.dto.BillResponse;

public interface BillService {
    BillResponse calculateBill(BillRequest request);
}