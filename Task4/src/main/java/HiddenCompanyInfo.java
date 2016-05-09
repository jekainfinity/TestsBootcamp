public class HiddenCompanyInfo {
    private String companyName;
    private double sourceAmount;
    private String sourceCurrency;
    private String targetCurrency;
    private double hiddenFeePercentage;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getSourceAmount() {
        return sourceAmount;
    }

    public void setSourceAmount(double sourceAmount) {
        this.sourceAmount = sourceAmount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public double getHiddenFeePercentage() {
        return hiddenFeePercentage;
    }

    public void setHiddenFeePercentage(double hiddenFeePercentage) {
        this.hiddenFeePercentage = hiddenFeePercentage;
    }
}
