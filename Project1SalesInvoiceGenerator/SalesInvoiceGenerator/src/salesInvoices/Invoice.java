package salesInvoices;

public class Invoice {
    private int invoiceNo;
    private String date;
    private String customerName;
    private double invoiceTotal;

    public Invoice(int num, String date1, String customerName,double total){
        this.invoiceNo = num;
        this.customerName = customerName;
        this.date = date1;
        this.invoiceTotal = total;
    }

    public int getInvoiceNo() {
        return invoiceNo;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getInvoiceTotal() {
        return invoiceTotal;
    }

    public void setInvoiceNo(int invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setInvoiceTotal(double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

}
