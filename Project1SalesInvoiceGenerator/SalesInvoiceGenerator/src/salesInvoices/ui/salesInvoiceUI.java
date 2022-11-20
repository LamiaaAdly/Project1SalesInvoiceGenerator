package salesInvoices.ui;

import Helpers.CSVfileHelper;
import salesInvoices.Invoice;
import salesInvoices.InvoiceItem;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;


public class salesInvoiceUI extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadFile;
    private JMenuItem saveFile;

    private JPanel leftSidePanel, rightSidePanel;
    private JPanel leftSidePanelChild, rightSidePanelChild1, rightSidePanelChild2;
    private JPanel southBtnPanel,southBtnPanel2 ,rightSidePanelChild;
    private JTable invoicesTable;
    private DefaultTableModel tableMoldelInvoices;
    private int invoicesTableRowSelected;
    private String invoicesTableDirectory;
    private int invoicesRow, itemsRow;
    private String[] invoicesTableHeaders;
    private ArrayList<Invoice> invoicesTableData;
    private  ArrayListView invoicesModel;

    private JTable invoiceItems;
    JScrollPane scrollPane, scrollPane1;
    String InveoicesPath, itemPath, fileNameOfInvoices;
    private String[] invoiceItemsHeaders;
    private ArrayList<InvoiceItem> invoiceItemsData;
    private ArrayListViewInvoiceItems arrayListViewItems;
    private JButton createInvoice, deleteInvoice;
    private JButton saveChanges, cancelChanges;
    private JTextField invoiceDate,customerName, invoiceNo,invoiceTotal;
    private JButton addItem;

    public salesInvoiceUI (String title){
        super(title);
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        loadFile = new JMenuItem("Load File", 'L');
        loadFile.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_DOWN_MASK));
        loadFile.addActionListener(this);
        loadFile.setActionCommand("L");

        saveFile = new JMenuItem("Save File",'S');
        saveFile.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        saveFile.addActionListener(this);
        saveFile.setActionCommand("S");

        setJMenuBar(menuBar);

        menuBar.add(fileMenu);
        fileMenu.add(loadFile);
        fileMenu.add(saveFile);

        setLayout(new GridLayout(1,2));
        // leftSidePanel
        leftSidePanel = new JPanel();
        leftSidePanelChild = new JPanel();
        leftSidePanelChild.setLayout(new BoxLayout(leftSidePanelChild,BoxLayout.Y_AXIS));
        leftSidePanelChild.setBorder(BorderFactory.createEtchedBorder());
        //leftSidePanel.setBorder(BorderFactory.createEtchedBorder());

        leftSidePanelChild.setBorder(BorderFactory.createTitledBorder("Invoices Table"));
        leftSidePanelChild.setSize(leftSidePanel.getWidth(),leftSidePanel.getHeight()/2);
        //leftSidePanelChild.add(new JLabel("Invoices Table"));

        leftSidePanelChild.add(new JLabel("   "));
        invoicesTableHeaders = new String[]{"Invoice No","Date","Customer Name", "Total"};

        String[] paths = CSVfileHelper.getPaths(this);
        InveoicesPath = paths[0];
        invoicesTableDirectory = paths[1];
        fileNameOfInvoices = paths[2];

        //error management


        invoicesTableData = CSVfileHelper.LoadInvoicesFromCSVFile(InveoicesPath);

        invoicesTable = new JTable();
        invoicesModel =new ArrayListView(invoicesTableData, invoicesTableHeaders);
        invoicesTable.setModel(invoicesModel);
        invoicesTable.setCellSelectionEnabled(true);
        invoicesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 79, 300, 500);
        scrollPane.setViewportView(invoicesTable);
        invoicesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                ArrayList<InvoiceItem> items = new ArrayList<>();
                invoicesTableRowSelected = invoicesTable.getSelectedRow();
                if(invoicesTableRowSelected < 0)
                    invoicesTableRowSelected = invoicesModel.list.size()-1;
                if(invoicesTableRowSelected < invoicesTableData.size()) {
                    invoiceNo.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,0)));
                    invoiceDate.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,1)));
                    customerName.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,2)));
                    invoiceTotal.setText(String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,3)));

                    String brePath = invoicesTableDirectory.replace("\\openInvoices","\\invoices");
                    itemPath = brePath + "\\" + String.valueOf(invoicesModel.getValueAt(invoicesTableRowSelected,0)) + ".csv";

                    items = CSVfileHelper.LoadInvoicesItemsFromCSVFile(itemPath);

                    //error management
//                    FileInputStream fis = null;
//
//                    try {
//                        fis= new FileInputStream(path);
//                        BufferedReader reader = new BufferedReader(new InputStreamReader(fis) );
//                        while(reader.ready()) {
//                            String line = reader.readLine();
//                            String[] dataCell = line.split(",");
//                            items.add(new InvoiceItem(Integer.valueOf(dataCell[0]),
//                                    dataCell[1],
//                                    Double.parseDouble(dataCell[2]),
//                                    Integer.valueOf(dataCell[3]),
//                                    Double.parseDouble(dataCell[4])));
//                        }
//                    } catch (FileNotFoundException e1){
//                        e1.printStackTrace();
//                    }catch (IOException e1){
//                        e1.printStackTrace();
//                    }finally {
//                        try{
//                            if(fis!=null) {
//                                fis.close();
//                            }
//                        }catch (IOException e1){}
//                    }

                    invoiceItems = new JTable();
                    arrayListViewItems  =
                            new ArrayListViewInvoiceItems(items, invoiceItemsHeaders);
                    invoiceItemsData = items;
                    invoiceItems.setModel(arrayListViewItems);
                    invoiceItems.setCellSelectionEnabled(true);
//                    invoiceItems.putClientProperty("terminateEditOnFocusLost", true);
                    invoiceItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                    scrollPane1.setViewportView(invoiceItems);
                }
            }
        });
        leftSidePanelChild.add(invoicesTable.getTableHeader());
        leftSidePanelChild.add(scrollPane);
        //leftSidePanelChild.add(new JLabel("   "));

        leftSidePanel.add(leftSidePanelChild);

        //leftSidePanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        //leftSidePanel.setBorder(BorderFactory.createEtchedBorder());
        leftSidePanel.add(leftSidePanelChild, BorderLayout.NORTH);
        //leftSidePanel.add(Box.createRigidArea(new Dimension(300, 100))); // just an empty placeholder

        southBtnPanel = new JPanel();
        createInvoice = new JButton("Create New Invoice");
        createInvoice.setActionCommand("createInvoice");
        createInvoice.addActionListener(this);
        southBtnPanel.add(createInvoice);

        deleteInvoice = new JButton("Delete Invoice");
        deleteInvoice.setActionCommand("deleteInvoice");
        deleteInvoice.addActionListener(this);
        southBtnPanel.add(deleteInvoice);

        leftSidePanel.add(southBtnPanel, BorderLayout.SOUTH);
        add(leftSidePanel);
        //End leftSidePanel

        //rightSidePanel
        rightSidePanel = new JPanel();
        rightSidePanelChild =new JPanel();
        rightSidePanelChild1 = new JPanel();
        rightSidePanelChild2 = new JPanel();

        rightSidePanelChild.setLayout(new BoxLayout(rightSidePanelChild, BoxLayout.Y_AXIS));
        //rightSidePanelChild1
        rightSidePanelChild1.setLayout(new BoxLayout(rightSidePanelChild1, BoxLayout.Y_AXIS));
        rightSidePanel.setBorder(BorderFactory.createEtchedBorder());
        rightSidePanelChild1.setSize(rightSidePanel.getWidth(),rightSidePanel.getHeight()/3);
        //rightSidePanelChild1.setAlignmentX(JPanel.TOP_ALIGNMENT);

        JPanel panel1 = new JPanel();
        panel1.add(new JLabel("Invoice Number:"));
        invoiceNo = new JTextField(15);
        invoiceNo.setEnabled(false);
        panel1.add(invoiceNo);
        rightSidePanelChild1.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.add(new JLabel("Customer Name:"));
        customerName = new JTextField(15);
        panel2.add(customerName);
        rightSidePanelChild1.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.add(new JLabel("Date:"));
        invoiceDate = new JTextField(15);
        panel3.add(invoiceDate);
        rightSidePanelChild1.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.add(new JLabel("Total:"));
        invoiceTotal = new JTextField(15);
        invoiceTotal.setEnabled(false);
        double total=0;
//        if(invoiceItemsData!=null){
//            for(int i=0; i<invoiceItemsData.size(); i++){
//                total += Double.parseDouble(String.valueOf(invoiceItems.getValueAt(i,4)));
//            }
//        }

//        invoiceTotal.setText(String.valueOf(total));
        panel4.add(invoiceTotal);
        rightSidePanelChild1.add(panel4);

        rightSidePanelChild.add(rightSidePanelChild1);
        //End rightSidePanelChild1
        rightSidePanelChild.add(new JLabel("     "));
        //rightSidePanelChild2
        rightSidePanelChild2.setLayout(new BoxLayout(rightSidePanelChild2, BoxLayout.Y_AXIS));
        rightSidePanelChild2.setBorder(BorderFactory.createTitledBorder("Invoice Items"));
        rightSidePanelChild2.setSize(rightSidePanel.getWidth(),rightSidePanel.getHeight()/2);

        invoiceItemsHeaders = new String[]{"No.","Name","Price", "Count","Total"};
        scrollPane1 = new JScrollPane();
        scrollPane1.setViewportView(new JTable());
        scrollPane1.setBounds(10, 79, 300, 400);

        addItem = new JButton("Add New Item");
        addItem.setActionCommand("addItem");
        addItem.addActionListener(this);

        rightSidePanelChild2.add(scrollPane1);

//        if(invoiceItems!= null) {
//
//
//            ArrayListViewInvoiceItems arrayListViewItems  =
//                    new ArrayListViewInvoiceItems(invoiceItemsData, invoiceItemsHeaders);
//            invoiceItems.setModel(arrayListViewItems);
//            scrollPane1.setViewportView(invoiceItems);
//            rightSidePanelChild2.add(invoiceItems.getTableHeader());
//            rightSidePanelChild2.add(scrollPane1);
//        }

        rightSidePanelChild.add(rightSidePanelChild2);
        rightSidePanelChild.add(addItem);
        //end rightSidePanelChild2

        southBtnPanel2 = new JPanel();
        saveChanges = new JButton("Save Changes");
        saveChanges.setActionCommand("saveChanges");
        saveChanges.addActionListener(this);
        southBtnPanel2.add(saveChanges);

        cancelChanges = new JButton("Cancel Changes");
        cancelChanges.setActionCommand("cancelChanges");
        cancelChanges.addActionListener(this);
        southBtnPanel2.add(cancelChanges);

        rightSidePanel.add(rightSidePanelChild, BorderLayout.NORTH);
        //rightSidePanel.add(Box.createRigidArea(new Dimension(400, 250))); // just an empty placeholder

        rightSidePanel.add(southBtnPanel2, BorderLayout.SOUTH);
        add(rightSidePanel);
        //End rightSidePanel


        setSize(1000,750);
        //setResizable(false);
        setLocation(200,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "L":
                loadFile();
                break;
            case "S":
                saveFile();
                break;
            case "saveChanges":
                saveChanges();
                break;
            case "cancelChanges":
                JOptionPane.showMessageDialog(null, "Changes not saved to Invoice Items Table",
                        "INFORMATION_MESSAGE", JOptionPane.INFORMATION_MESSAGE);
                break;
            case "createInvoice":
                createInvoice();
                break;
            case "deleteInvoice":
                deleteInvoice(invoicesTableRowSelected);
                break;
            case "addItem":
                addItem();
                break;
        }
    }

//    private ArrayList<Invoice> loadFile(){
//        ArrayList<Invoice> invoces = new ArrayList<>();
//        JFileChooser fc = new JFileChooser();
//        fc.setCurrentDirectory(new File("..\\InvoiceTables\\openInvoices"));
//        int result = fc.showOpenDialog(this);
//        if(result == JFileChooser.APPROVE_OPTION){
//            String path = fc.getSelectedFile().getPath();
//            invoicesTableDirectory = fc.getSelectedFile().getParent();
//            String fi = fc.getName();
//            FileInputStream fis = null;
//
//            try {
//                fis= new FileInputStream(path);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(fis) );
//                while(reader.ready()) {
//                    String line = reader.readLine();
//                    String[] dataCell = line.split(",");
//                    invoces.add(new Invoice(Integer.valueOf(dataCell[0]),
//                                            dataCell[1],
//                                            dataCell[2],
//                                            Double.parseDouble(dataCell[3])));
//                }
//            } catch (FileNotFoundException e){
//                e.printStackTrace();
//            }catch (IOException e){
//                e.printStackTrace();
//            }finally {
//                try{
//                    if(fis!=null) {
//                        fis.close();
//                    }
//                }catch (IOException e){}
//            }
//        }
//        return invoces;
//    }

    private void loadFile(){
        String paths[] = CSVfileHelper.getPaths(this);
        InveoicesPath = paths[0];
        invoicesTableDirectory = paths[1];
        fileNameOfInvoices = paths[2];

        invoicesTableData = CSVfileHelper.LoadInvoicesFromCSVFile(InveoicesPath);

        invoicesModel =new ArrayListView(invoicesTableData, invoicesTableHeaders);
        invoicesTable.setModel(invoicesModel);
    }
    private void saveFile(){

        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("..\\InvoiceTables\\openInvoices"));
        int result = fc.showSaveDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            String path = fc.getSelectedFile().getPath();
            if(invoicesTable!=null) {
                CSVfileHelper.SaveInvoicesToCSVFile(path, invoicesTable);
            }
        }
    }

    private void saveChanges(){
        double sum =0;
        for(int i=0;i<arrayListViewItems.list.size();i++) {
            sum += Double.valueOf(arrayListViewItems.getValueAt(i, 4).toString());
        }
        String Ivdate = invoiceDate.getText();
        String IvcustomerName = customerName.getText();

            //if (invoiceItems != null) {
        CSVfileHelper.SaveInvoiceItemsToCSVFile(itemPath, invoiceItems);
        invoicesModel.setValueAt(Ivdate,invoicesTableRowSelected,1);
        invoicesModel.setValueAt(IvcustomerName,invoicesTableRowSelected,2);
        invoicesModel.setValueAt(sum,invoicesTableRowSelected,3);

            //}
//            ArrayList<InvoiceItem> items = new ArrayList<>();
//            items = CSVfileHelper.LoadInvoicesItemsFromCSVFile(itemPath);
//            arrayListViewItems = new ArrayListViewInvoiceItems(items,invoiceItemsHeaders);
//            invoiceItems.setModel(arrayListViewItems);
       // }
    }

    private void createInvoice(){

        if(invoicesTable.isRowSelected(invoicesTableRowSelected)) {
            invoicesTable.clearSelection();
        }

        int newInvoiceNo = (int)invoicesModel.getValueAt(invoicesModel.list.size()-1,0) +1;
        String brePath = invoicesTableDirectory.replace("\\openInvoices","\\invoices");
        itemPath = brePath + "\\" + newInvoiceNo+ ".csv";
        CSVfileHelper.CreateFile(itemPath);
        invoicesModel.list.add(new Invoice(newInvoiceNo, "", "", 0));
        invoicesModel.fireTableStructureChanged();

        invoiceNo.setText(String.valueOf(newInvoiceNo));
        customerName.setText(" ");
        invoiceDate.setText(" ");
        invoiceTotal.setText(" ");

        ArrayList<InvoiceItem> emptyItems = new ArrayList<>();
        arrayListViewItems  =
                new ArrayListViewInvoiceItems(emptyItems, invoiceItemsHeaders);
        if(invoiceItems == null)
            invoiceItems = new JTable();
        invoiceItems.setModel(arrayListViewItems);

    }
    private void deleteInvoice(int invoicesTableRowSelected){
        if(invoicesTableRowSelected >= 0) {
            invoicesModel.list.remove(invoicesTableRowSelected);
            System.out.println("file removed!");
        }
        String invoPath = invoicesTableDirectory.replace("\\invoices","\\openInvoices") + fileNameOfInvoices +".csv";
        CSVfileHelper.SaveInvoicesToCSVFile(invoPath,  invoicesTable);
    }

    private void addItem(){
        arrayListViewItems.list.add(new InvoiceItem(0, "", 0,0, 0));
        arrayListViewItems.fireTableStructureChanged();
    }

//    public static void main(String[] args) {
//        salesInvoiceUI frame= new salesInvoiceUI("Sales Invoices");
//        frame.setVisible(true);
//
//    }

}
