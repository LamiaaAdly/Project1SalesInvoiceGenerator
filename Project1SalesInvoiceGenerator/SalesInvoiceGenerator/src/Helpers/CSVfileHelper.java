package Helpers;

import salesInvoices.Invoice;
import salesInvoices.InvoiceItem;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class CSVfileHelper {

    public static ArrayList<Invoice> LoadInvoicesFromCSVFile(String path){
        ArrayList<Invoice> invoices = new ArrayList<>();

        FileInputStream fis = null;
        try {
            fis= new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis) );
            while(reader.ready()) {
                String line = reader.readLine();
                String[] dataCell = line.split(",");
                invoices.add(new Invoice(Integer.valueOf(dataCell[0]),
                        dataCell[1],
                        dataCell[2],
                        Double.parseDouble(dataCell[3])));
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(fis!=null) {
                    fis.close();
                }
            }catch (IOException e){}
        }
        return invoices;
    }

    public static ArrayList<InvoiceItem> LoadInvoicesItemsFromCSVFile(String path){
        ArrayList<InvoiceItem> items = new ArrayList<>();

        FileInputStream fis = null;
        try {
            fis= new FileInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis) );
            while(reader.ready()) {
                String line = reader.readLine();
                String[] dataCell = line.split(",");
                items.add(new InvoiceItem(Integer.valueOf(dataCell[0]),
                                    dataCell[1],
                                    Double.parseDouble(dataCell[2]),
                                    Integer.valueOf(dataCell[3]),
                                    Double.parseDouble(dataCell[4])));
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if(fis!=null) {
                    fis.close();
                }
            }catch (IOException e){}
        }
        return items;
    }


    public static void SaveInvoicesToCSVFile(String path, JTable table){

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);

            int nRow = table.getRowCount();
            int nCol = table.getColumnCount();
            String data = "";

            //write row information
            for (int i = 0 ; i < nRow ; i++){
                for (int j = 0 ; j < nCol ; j++){
                    data += String.valueOf(table.getValueAt(i,j));

                    if(j==nCol-1) data=data.replace("\r","");
                    if (j!=nCol-1) data += ",";
                }
                if(i!=nRow-1)data += "\r\n";
            }

            byte[] b = data.getBytes();
            fos.write(b);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                fos.close();
            }catch (IOException e){}
        }
    }

    public static void SaveInvoiceItemsToCSVFile(String path, JTable table){

        SaveInvoicesToCSVFile(path, table);

    }

    public static void CreateFile(String path) {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String[] getPaths(Component context)
    {
        String path =null;
        String parentDirectory =null;
        String fileName =null;
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("..\\InvoiceTables\\openInvoices"));
        int result = fc.showOpenDialog(context);
        if(result == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile().getPath();
            parentDirectory = fc.getSelectedFile().getParent();
            fileName = fc.getSelectedFile().getName();
        }
        return new String[] {path, parentDirectory, fileName};
    }
}
