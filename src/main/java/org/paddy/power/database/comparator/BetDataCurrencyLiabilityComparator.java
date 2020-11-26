package org.paddy.power.database.comparator;

import java.util.Comparator;

import org.paddy.power.dto.BetReport;

/**
 * Comparator to sort the BetReport .
 */
public class BetDataCurrencyLiabilityComparator implements Comparator<BetReport> {

    /**
     * Method sort the BetReport data by currency and totalLiability (stakes*price) in descending order.
     * 
     * @param o1
     *            first Report dao
     * @param o2
     *            second Report dao
     * @return int a negative integer, zero, or a positive integer
     */
    @Override
    public int compare(final BetReport o1, final BetReport o2) {
        int value = o2.getCurrency().compareTo(o1.getCurrency());
        if (value == 0) {
            String totalLiability2 = o2.getTotalLiability().substring(1);
            String totalLiability1 = o1.getTotalLiability().substring(1);
            return Double.compare(Double.parseDouble(totalLiability2),
                    Double.parseDouble(totalLiability1));
        } else {
            return value;
        }
    }
}
