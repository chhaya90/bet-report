package org.paddy.power.dto;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class CsvBetRecord implements Serializable {
    @CsvBindByName(column = "betId")
    private String betId;

    @CsvBindByName(column = "betTimestamp")
    private String betTimestamp;

    @CsvBindByName(column = "selectionId")
    private Integer selectionId;

    @CsvBindByName(column = "selectionName")
    private String selectionName;

    @CsvBindByName(column = "stake")
    private double stake;

    @CsvBindByName(column = "price")
    private double price;

    @CsvBindByName(column = "currency")
    private String currency;

    public String getBetId() {
        return betId;
    }

    public String getBetTimestamp() {
        return betTimestamp;
    }

    public Integer getSelectionId() {
        return selectionId;
    }

    public String getSelectionName() {
        return selectionName;
    }

    public double getStake() {
        return stake;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "BetData{" + "betId='" + betId + '\'' + ", betTimestamp=" + betTimestamp + ", selectionId=" + selectionId + ", selectionName='"
                + selectionName + '\'' + ", stake=" + stake + ", price=" + price + ", currency=" + currency + '}';
    }
}
