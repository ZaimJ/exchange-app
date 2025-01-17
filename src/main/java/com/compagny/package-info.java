/**
 * Calculation of the bills Service with Currency Exchange Service and discounts.
 *
 * <h2>Core Components:</h2>
 * <ul>
 *   <li>Bill - Stores bill information and line items</li>
 *   <li>LineItem - Represents individual bill entries</li>
 *   <li>User - Customer with type and registration date</li>
 * </ul>
 *
 * <h2>Main Services:</h2>
 * <ul>
 *   <li>BillService - Calculates final bill amount with discounts and currencey after exchange</li>
 *   <li>ExchangeRateService - Handles currency conversion</li>
 *   <li>DiscountService - Applies discount rules:
 *     <ul>
 *       <li>30% for employees</li>
 *       <li>10% for affiliates</li>
 *       <li>5% for 2+ year customers</li>
 *       <li>$5 off per $100 spent</li>
 *     </ul>
 *   </li>
 * </ul>
 *
 * @see com.compagny.service.BillService
 * @see com.compagny.service.ExchangeRateService
 * @see com.compagny.service.DiscountService
*/
package com.compagny;