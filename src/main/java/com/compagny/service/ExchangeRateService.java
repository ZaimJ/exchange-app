/**
 * Package containing service interfaces and implementations.
 */
package com.compagny.service;

public interface ExchangeRateService {
    double getExchangeRate(String from, String to);
}