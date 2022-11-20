package salesInvoices;

public class InvoiceItem{

    private int itemNumber;
    private String itemName;
    private double itemPrice;

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public void setItemTotal(double itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public double getItemTotal() {
        return itemTotal;
    }

    private int itemCount;
    private double itemTotal;

    public InvoiceItem(int num, String name, double price, int count, double total) {
        this.itemNumber = num;
        this.itemName = name;
        this.itemPrice = price;
        this.itemCount = count;
        this.itemTotal = total;
    }


}
