
package full_stack;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.RoundingMode;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import jxl.*;
import jxl.read.biff.BiffException;
import java.sql.Types.*;
import java.text.*;
import java.util.Date;
import static java.util.Locale.filter;
import javax.swing.filechooser.*;
import javax.jnlp.*;
import javax.jnlp.ServiceManager;
import static jdk.nashorn.internal.objects.NativeArray.filter;
import static sun.awt.image.ImagingLib.filter;
import static sun.reflect.annotation.TypeAnnotation.filter;
import static sun.util.locale.LocaleMatcher.filter;


public class Full_Stack extends JFrame {
    
    private JTable bodyTable;
    private JTextField field;
    private int numberOfCols;
    private JScrollPane tablePane;
    private JButton checkButton;
    private JComboBox combo;
    private JComboBox xlLists;
    
    public Full_Stack(){
        
        setLayout(new BorderLayout());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        JPanel panel_1=new JPanel(new FlowLayout());
        //panel_1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel label=new JLabel("File:");
        field=new JTextField(20);
        field.setPreferredSize(new Dimension(200,25));
        
        xlLists=new JComboBox();
        xlLists.setPreferredSize(new Dimension(120,25));
        xlLists.addItem("");
        
        JButton openButton=new JButton("Browse");
        
        openButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
             JFileChooser chooser=new JFileChooser();
             
            int option=chooser.showOpenDialog(Full_Stack.this);
             field.setText(chooser.getSelectedFile().toString());
             
             
             try{
                    File file=new File(field.getText());
                    Workbook wk=Workbook.getWorkbook(file);
                    String[] shNames =wk.getSheetNames();
                    
                    //xlLists.removeAllItems();
                    
                    for(int i=0; i<wk.getSheets().length; i++){
                        xlLists.addItem(wk.getSheets()[i].getName());
                    }
                 
                    
             } catch (IOException|BiffException|NullPointerException e){e.printStackTrace();}
             
             
             
             
            }
        });
        
        
        
        checkButton=new JButton("check");
        checkButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                
                
            }
        });
        
        
        JButton createList=new JButton("Create List");
        createList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                createList();
                
            }
        });
        
        JButton clearBtn=new JButton("Clear");
        clearBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                DefaultTableModel model=(DefaultTableModel)bodyTable.getModel();
                //model.getDataVector().removeAllElements();
                //model.fireTableDataChanged();
                model.setRowCount(0);
                model.setColumnCount(0);
              
                
            }
        });
        
        panel_1.add(label);
        panel_1.add(field);
        panel_1.add(xlLists);
        panel_1.add(openButton);
        panel_1.add(createList);
        panel_1.add(clearBtn);
        
        JPanel panel_2=new JPanel();
        panel_2.setPreferredSize(new Dimension(300,300));
        
        bodyTable=new JTable();
        bodyTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablePane=new JScrollPane(bodyTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tablePane.setPreferredSize(new Dimension(300,300));
        //tablePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tablePane.setBorder(BorderFactory.createLineBorder(Color.gray, 1));//.createEmptyBorder(10, 10, 10, 10));
        tablePane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10), BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
        //JPanel bottomPane=new JPanel(new BorderLayout());
        
        JPanel panel_3=new JPanel(new FlowLayout());
        //panel_3.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //JTextField tableName=new JTextField(20);
        combo=new JComboBox();
        combo.setPreferredSize(new Dimension(200,25));
        combo.addItem("");
        try{
            Connection conn=DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:orcl", "system", "oracle");
            Statement stmt=conn.createStatement();
            ResultSet rs=stmt.executeQuery("Select * from tab");
            while (rs.next()){
                combo.addItem(rs.getString(1));
            }
            
        } catch(Exception e){e.printStackTrace();}
        
        
        
        
        JButton checkButton=new JButton("Check");
        
        JButton importButton=new JButton("Import");
        
        importButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                populateDB();
            }
        });
        
        JLabel mapTo=new JLabel("Map to");
        panel_3.add(mapTo);;
        panel_3.add(combo);
        panel_3.add(checkButton);
        panel_3.add(importButton);
        
        
        
        add(panel_1, BorderLayout.NORTH);
        add(tablePane,BorderLayout.CENTER);
        add(panel_3,BorderLayout.SOUTH);
        pack();
        setVisible(true);
        
    }
    
    private void createList(){
        try{
                    File file=new File(field.getText());
                    Workbook wk=Workbook.getWorkbook(file);
                    Sheet s=wk.getSheet(xlLists.getSelectedItem().toString());
                    
                    int rows=s.getRows();
                    System.out.println(rows);
                    int cols=s.getColumns();
                    
                    
                    
                    
                    DefaultTableModel model=(DefaultTableModel)bodyTable.getModel();
                    
                    for(int i=0; i<cols; i++){
                        model.addColumn(QueringOracle.convert(s.getCell(i, 0).getContents()));
                        //bodyTable.getColumnModel().getColumn(i).
                          //      setPreferredWidth(400);
                    }
                                  
                    
                    
                    for(int i=0; i<rows-1; i++){                         
                        
                        
                        
                        Object[] rowData=new Object[cols+1];
                        
                        
                        for(int j=0; j<cols; j++){
                            rowData[j]=QueringOracle.convert(s.getCell(j, 1+i).getContents());
                        }
                        
                        model.addRow(rowData);
                        
                        /*model.addRow(new Object[]{i+1+"",
                                                  QueringOracle.convert(s.getCell(1,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(2,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(3,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(4,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(5,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(6,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(7,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(8,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(9,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(10,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(11,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(12,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(13,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(14,2+i).getContents()),
                                                  QueringOracle.convert(s.getCell(15,2+i).getContents())});*/
                        }
                        
                    
                    
                } catch(IOException|BiffException|ArrayIndexOutOfBoundsException e)
                {   e.printStackTrace();
                    System.out.println("Choose file first");}
        
        
    }
    
    private static String getSQL(String tableName){
        
        
        
        String sql="INSERT INTO";
        return sql;    
    }    
    
    private void populateDB(){
        
        String url="jdbc:oracle:thin:@localhost:1521:orcl";
        String user="system";
        String pass="oracle";
        
        DefaultTableModel model=(DefaultTableModel)bodyTable.getModel();
        System.out.println("Connecting...");
        System.out.println("rows: "+model.getRowCount());
        
        try{
            Connection con=DriverManager.getConnection(url, user, pass);
            System.out.println("Connected");
            //ResultSet table=con.createStatement().executeQuery("Select * from ");
            
            String sql="INSERT INTO "+combo.getSelectedItem().toString().trim()+" VALUES"+"(";
            
            for (int i=0; i<model.getColumnCount(); i++){
                if(i!=model.getColumnCount()-1){
                    sql=sql+"?,";
                } else sql=sql+"?";                
            }
            sql=sql+")";
            System.out.println(sql);
            
            PreparedStatement stmt=con.prepareStatement(sql);
            
            ResultSetMetaData rsMetaData=con.createStatement().
                    executeQuery("select * from "+combo.getSelectedItem()).getMetaData();
            
            System.out.println(bodyTable.getColumnCount());
                        
            for(int i=0; i<model.getRowCount(); i++){
                                
                for(int k=1; k<=bodyTable.getColumnCount(); k++){
                                    
                   if(rsMetaData.getColumnType(k)==java.sql.Types.CHAR){
                        
                        String str=model.getValueAt(i, k-1).toString();
                        int precision=rsMetaData.getPrecision(k);
                        String varcharData=str.substring(0, precision+1); 
                        
                        stmt.setString(k, varcharData);
                        
                    } else if(rsMetaData.getColumnType(k)==java.sql.Types.VARCHAR){
                        
                        String str=model.getValueAt(i, k-1).toString();
                        int precision=rsMetaData.getPrecision(k);
                        String varcharData;
                        if(precision+1>str.length()){
                            varcharData=str.substring(0, str.length()).trim();                            
                        } else {
                            varcharData=str.substring(0, precision+1).trim();
                        }                       
                        stmt.setString(k, varcharData);
                        
                    } else if(rsMetaData.getColumnType(k)==java.sql.Types.TIMESTAMP){
                        
                        String strDate=model.getValueAt(i, k-1).toString();
                        String pattern="dd.MM.yyyy HH:mm:ss";
                        java.text.DateFormat format=new java.text.SimpleDateFormat(pattern);
                        java.util.Date date=format.parse(strDate);
                        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
                        //java.sql.Timestamp sqlTimeStamp=new java.sql.Timestamp(date.getTime());                       
                        stmt.setDate(k, sqlDate);
                        
                    } else if(rsMetaData.getColumnType(k)==java.sql.Types.NUMERIC){
                        
                        int precision=rsMetaData.getPrecision(k);
                        int scale=rsMetaData.getScale(k);
                        String strNumber=model.getValueAt(i, k-1).toString();
                        //System.out.println("show number"+strNumber);
                        //System.out.println(model.getValueAt(i, k-1).toString());
                        java.math.BigDecimal sqlNumber;
                        if(strNumber==""){
                            sqlNumber=java.math.BigDecimal.ZERO;
                        } else{
                            sqlNumber=new java.math.BigDecimal(strNumber.replaceAll(",", ""));
                        }                        
                        sqlNumber.setScale(scale, RoundingMode.HALF_UP);
                        
                        stmt.setBigDecimal(k, sqlNumber);                      
                        
                    } else {
                        System.out.println(rsMetaData.getColumnType(k));
                        System.out.println(rsMetaData.getColumnTypeName(k));
                        System.out.println(java.sql.Types.DATE);
                        throw new NoMatchingFormatException();
                    }          
                    
                    //stmt.setString(k, model.getValueAt(i, k-1).toString());
                }              
                stmt.executeUpdate();
            }           
        } catch(Exception e){e.printStackTrace();}
        
        System.out.println("Done!");
        
    }
    
    
    public static void main(String[] args) {
        
        new Full_Stack();// TODO code application logic here
        
        
    }
    
}

class NoMatchingFormatException extends Exception{
    public NoMatchingFormatException(){
        super("Check format");
    }
}
