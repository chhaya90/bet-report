package org.paddy.power.dto;

import java.io.Serializable;
import java.util.Objects;

public class BetReport implements Serializable {

    private String selectionName;
    private String currency;
    private Integer numberOfBets;
    private String totalStakes;
    private String totalLiability;

    public String getSelectionName() {
        return selectionName;
    }

    public void setSelectionName(final String selectionName) {
        this.selectionName = selectionName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public Integer getNumberOfBets() {
        return numberOfBets;
    }

    public void setNumberOfBets(final Integer numberOfBets) {
        this.numberOfBets = numberOfBets;
    }

    public String getTotalStakes() {
        return totalStakes;
    }

    public void setTotalStakes(final String totalStakes) {
        this.totalStakes = totalStakes;
    }

    public String getTotalLiability() {
        return totalLiability;
    }

    public void setTotalLiability(final String totalLiability) {
        this.totalLiability = totalLiability;
    }

    @Override
    public String toString() {
        return "ReportDao{" + "selectionName='" + selectionName + '\'' + ", currency='" + currency + '\'' + ", numberOfBets=" + numberOfBets + ", " +
                "totalStakes='" + totalStakes + '\'' + ", totalLiability='" + totalLiability + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BetReport betReport = (BetReport) o;
        return Objects.equals(selectionName, betReport.selectionName) && Objects.equals(currency, betReport.currency) && Objects.equals(numberOfBets, betReport.numberOfBets) && Objects.equals(totalStakes, betReport.totalStakes) && Objects.equals(totalLiability, betReport.totalLiability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectionName, currency, numberOfBets, totalStakes, totalLiability);
    }
}
