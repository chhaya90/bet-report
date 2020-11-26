package org.paddy.power.dto;

/**
 * Object to store bet data read from input source.
 */
public class BetRecord {

    private String betId;
    private String betTimestamp;
    private Integer selectionId;
    private String selectionName;
    private double stake;
    private double price;
    private String currency;

    public BetRecord(BetRecordBuilder builder) {
        this.betId = builder.betId;
        this.betTimestamp = builder.betTimestamp;
        this.selectionId = builder.selectionId;
        this.selectionName = builder.selectionName;
        this.stake = builder.stake;
        this.price = builder.price;
        this.currency = builder.currency;
    }

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

    public static class BetRecordBuilder {

        private String betId;

        private String betTimestamp;

        private Integer selectionId;

        private String selectionName;

        private double stake;

        private double price;

        private String currency;

        public BetRecordBuilder setBetId(String betId) {
            this.betId = betId;
            return this;
        }

        public BetRecordBuilder setBetTimestamp(String betTimestamp) {
            this.betTimestamp = betTimestamp;
            return this;
        }

        public BetRecordBuilder setSelectionId(Integer selectionId) {
            this.selectionId = selectionId;
            return this;
        }

        public BetRecordBuilder setSelectionName(String selectionName) {
            this.selectionName = selectionName;
            return this;
        }

        public BetRecordBuilder setStake(double stake) {
            this.stake = stake;
            return this;
        }

        public BetRecordBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public BetRecordBuilder setCurrency(String currency) {
            this.currency = currency;
            return this;
        }

        public BetRecord build() {
            BetRecord user = new BetRecord(this);
            return user;
        }
    }
}
