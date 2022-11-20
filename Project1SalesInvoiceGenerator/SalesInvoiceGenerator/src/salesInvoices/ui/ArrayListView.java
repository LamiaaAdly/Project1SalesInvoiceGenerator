package salesInvoices.ui;

import salesInvoices.Invoice;
import salesInvoices.InvoiceItem;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;

public class ArrayListView extends AbstractTableModel {

    ArrayList<Invoice> list;
    String[] headers;
    public ArrayListView(ArrayList<Invoice> list, String[] headers){
        this.list = list;
        this.headers = headers;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

       // System.out.println("list size = " +list.size()+ " and rowIndex = "+rowIndex);
        if(list.size() <= rowIndex || rowIndex < 0)
            return "";

        switch (columnIndex){
            case 0:
                return list.get(rowIndex).getInvoiceNo();
            case 1:
                return list.get(rowIndex).getDate();
            case 2:
                return list.get(rowIndex).getCustomerName();
            case 3:
                return list.get(rowIndex).getInvoiceTotal();

        }
        return null;
    }
    @Override
    public String getColumnName(int column)
    {
        return headers[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);

        Invoice item = list.get(rowIndex);

        String Value = aValue.toString();

        switch (columnIndex){
            case 0:
                item.setInvoiceNo(Integer.valueOf(Value));
                break;
            case 1:
                item.setDate( Value);
                break;
            case 2:
                item.setCustomerName((Value));
                break;
            case 3:
                item.setInvoiceTotal(Double.valueOf(Value));
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
