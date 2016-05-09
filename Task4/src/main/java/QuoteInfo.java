public class QuoteInfo {
    private double commissionPercentage;
    private String targetCurrency;
    private double feeInGbp;
    private double youPay;
    private double targetValue;
    private String sourceCurrency;
    private double sourceAmount;
    private double offerRate;
    private double recipientReceives;

    public double getCommissionPercentage() {
        return commissionPercentage;
    }

    public void setCommissionPercentage(double commissionPercentage) {
        this.commissionPercentage = commissionPercentage;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getFeeInGbp() {
        return feeInGbp;
    }

    public void setFeeInGbp(double feeInGbp) {
        this.feeInGbp = feeInGbp;
    }

    public double getYouPay() {
        return youPay;
    }

    public void setYouPay(double youPay) {
        this.youPay = youPay;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public double getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(double sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public double getOfferRate() {
        return offerRate;
    }

    public void setOfferRate(double offerRate) {
        this.offerRate = offerRate;
    }

    public double getRecipientReceives() {
        return recipientReceives;
    }

    public void setRecipientReceives(double recipientReceives) {
        this.recipientReceives = recipientReceives;
    }
}
