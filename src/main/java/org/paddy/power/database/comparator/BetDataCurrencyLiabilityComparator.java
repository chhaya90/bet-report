package org.paddy.power.database.comparator;

import java.util.Comparator;

import org.paddy.power.dto.ReportDao;

public class BetDataCurrencyLiabilityComparator implements Comparator<ReportDao> {
    @Override
    public int compare(final ReportDao o1, final ReportDao o2) {
        int value = o2.getCurrency().compareTo(o1.getCurrency());
        if (value == 0) {
            return Double.compare(Double.parseDouble(o2.getTotalLiability().substring(1)),
                    Double.parseDouble(o1.getTotalLiability().substring(1)));
        } else {
            return value;
        }
    }
}
