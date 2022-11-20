package salesInvoices.ui;

import salesInvoices.InvoiceItem;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ArrayListViewInvoiceItems extends AbstractTableModel {

    ArrayList<InvoiceItem> list;
    String[] headers;

    public ArrayListViewInvoiceItems(ArrayList<InvoiceItem> list, String[] headers){
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
        switch (columnIndex){
            case 0:
                return list.get(rowIndex).getItemNumber();
            case 1:
                return list.get(rowIndex).getItemName();
            case 2:
                return list.get(rowIndex).getItemPrice();
            case 3:
                return list.get(rowIndex).getItemCount();
            case 4:
                return list.get(rowIndex).getItemTotal();

        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        super.setValueAt(aValue, rowIndex, columnIndex);

        InvoiceItem item = list.get(rowIndex);
        String Value = aValue.toString();
        switch (columnIndex){
            case 0:
                item.setItemNumber((Integer.valueOf(Value)));
                break;
            case 1:
                item.setItemName(Value);
                break;
            case 2:
                item.setItemPrice(Double.valueOf(Value));
                break;
            case 3:
                item.setItemCount(Integer.valueOf(Value));
                break;
            case 4:
                item.setItemTotal(Double.valueOf(Value));
                break;

        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column)
    {
        return headers[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }
}
