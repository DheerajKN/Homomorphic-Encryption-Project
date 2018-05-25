
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.Random;
import java.util.Vector;
import java.lang.Math;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class uisql {
	private static final String FILENAME =  "encryption key location";
   private JFrame mainFrame;
   private JFrame encryptedTable,decryptedTable;   
   private JLabel headerLabel;
   private JPanel controlPanel;
   static pailier paillier;
   private static String sip,databasename,password,user;
   static aes aesen;
   static file fl;
  static BigInteger r;
   
   public uisql(){
      prepareGUI();
   }

   public static void main(String[] args){
	   fl=new file(); 
	  paillier = new pailier();
	  if(fl.check(FILENAME)){
		  BigInteger[] rValue = fl.getKey(FILENAME);
		  paillier.KeyGeneration(512, 62,rValue[0],rValue[1]);
			r = rValue[2];
		}
		else {
			 r = new BigInteger(512, new Random());
			 paillier.KeyGeneration(512, 62);
			 BigInteger[] temp = paillier.getPQ();
			 temp[2] = new BigInteger(""+r);
			temp[3] = new BigInteger(""+100 +  (int)(Math.random()*(10000-100+1)));
			fl.fileWrite(temp,FILENAME);
		}
		 aesen=new aes();
	  sip="localhost";
	  databasename="project";
	  password=dbapssword;
	  user="root";
	  uisql  uisql = new uisql();      
      uisql.showTextFieldDemo();
      }
   
   public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();
	    rs.beforeFirst();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }
	    
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}

public static DefaultTableModel builddecryptedTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();
	    rs.beforeFirst();
	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(aesen.decrypt(metaData.getColumnName(column)));
	    }
	    
	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	        	if(columnIndex==2)
	            vector.add(paillier.DecrpyStr(new BigInteger(rs.getObject(columnIndex).toString())));
	        	else
        		vector.add(paillier.Decryption(new BigInteger(rs.getObject(columnIndex).toString())));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
 
   public void create(String a,String b,String c, String d){
	      try{
	  		 Class.forName("com.mysql.jdbc.Driver");  
	  		  Connection con=DriverManager.getConnection("jdbc:mysql://"+sip+":3306/"+databasename,user,password);  
	  		  Statement st=con.createStatement();
	  		  
	  		String ena=aesen.encrypt(a);
	  		String enb=aesen.encrypt(b);
	  		String enc=aesen.encrypt(c);
	  		String end=aesen.encrypt(d);
	  		
	  		System.out.println("a="+ena+"\nb="+enb+"\nc="+enc+"\nd="+end+"\n");
	  		String up="CREATE TABLE `"+ena+"`(`"+enb+"` VARCHAR(310),`"+enc+"` VARCHAR(310), `"+end+"` VARCHAR(310));";
	  		System.out.println(up);	  		  
	  		st.execute("CREATE TABLE `"+ena+"`(`"+enb+"` VARCHAR(310),`"+enc+"` VARCHAR(310),`"+end+"` VARCHAR(310));");
	  		ResultSet rs=st.executeQuery("SELECT * FROM `"+ena+"`;");
	  	
	  		encryptedTable=new JFrame();
	  		encryptedTable.setSize(640,220);
	  		encryptedTable.setLocation(460,0);
	  		JTable t1=new JTable(buildTableModel(rs));
	  		JScrollPane sp = new JScrollPane(t1);
	  		sp.setBounds(460,10,640,210);
	  		encryptedTable.add(sp);
	  		encryptedTable.setVisible(true);
	  		decryptedTable=new JFrame();
	  		decryptedTable.setSize(640,220);
	  		decryptedTable.setLocation(460,230);
	  		JTable t2=new JTable(builddecryptedTableModel(rs));
	  		JScrollPane sp1 = new JScrollPane(t2);
	  		sp.setBounds(460,10,640,210);
	  		decryptedTable.add(sp1);
	  		decryptedTable.setVisible(true);
	  		
	  		con.close();
	      }		
	  		catch(Exception e)
	  		{
	  			System.out.println("Error in connection" + e);
	  		}
	      
	      }
  	public void insertdata(String a,String b,String c,String d){
      try{
  		 Class.forName("com.mysql.jdbc.Driver");  
  		  Connection con=DriverManager.getConnection("jdbc:mysql://"+sip+":3306/"+databasename,user,password);  
  		  Statement st=con.createStatement();
  		  BigInteger a1=new BigInteger(a);
          BigInteger ena=paillier.Encryption(a1,r);
  		  BigInteger enb=paillier.EncrypStr(b,r);
		  BigInteger c1=new BigInteger(c);
  		  BigInteger enc=paillier.Encryption(c1,r);
  		String end=aesen.encrypt(d);
  	       
  	       st.executeUpdate("INSERT INTO `"+end+"` VALUES('"+ena+"','"+enb+"','"+enc+"')");
  	       System.out.println(ena+" "+enb+" "+enc );
  	      
  	     st.executeQuery("SELECT * FROM `"+end+"`");
  	       
  	       ResultSet rs = st.getResultSet();
  	     encryptedTable=new JFrame();
	  		encryptedTable.setSize(640,220);
	  		encryptedTable.setLocation(460,0);
	  		JTable t1=new JTable(buildTableModel(rs));
	  		JScrollPane sp = new JScrollPane(t1);
	  		sp.setBounds(460,10,640,210);
	  		encryptedTable.add(sp);
	  		encryptedTable.setVisible(true);
	  		decryptedTable=new JFrame();
	  		decryptedTable.setSize(640,220);
	  		decryptedTable.setLocation(460,230);
	  		JTable t2=new JTable(builddecryptedTableModel(rs));
	  		JScrollPane sp1 = new JScrollPane(t2);
	  		sp.setBounds(460,10,640,210);
	  		decryptedTable.add(sp1);
	  		decryptedTable.setVisible(true);
	  		
  	    		con.close();  
  	    
  		}catch(Exception e)
  		{
  			System.out.println("Error in connection" + e);
  		}  	    }
  	
  	public void displaydata(String tname)
  	{
  		 try{
  		Class.forName("com.mysql.jdbc.Driver");  
  		Connection con=DriverManager.getConnection("jdbc:mysql://"+sip+":3306/"+databasename,user,password);  
		  Statement st=con.createStatement();
		  String enh=aesen.encrypt(tname);
		  	st.executeQuery("SELECT * FROM `"+enh+"`");
	       
		    ResultSet rs = st.getResultSet();
		  		encryptedTable=new JFrame();
		  		encryptedTable.setSize(640,220);
		  		encryptedTable.setLocation(460,0);
		  		JTable t1=new JTable(buildTableModel(rs));
		  		JScrollPane sp = new JScrollPane(t1);
		  		sp.setBounds(460,10,640,210);
		  		encryptedTable.add(sp);
		  		encryptedTable.setVisible(true);
		  		decryptedTable=new JFrame();
		  		decryptedTable.setSize(640,220);
		  		decryptedTable.setLocation(460,230);
		  		JTable t2=new JTable(builddecryptedTableModel(rs));
		  		JScrollPane sp1 = new JScrollPane(t2);
		  		sp.setBounds(460,10,640,210);
		  		decryptedTable.add(sp1);
		  		decryptedTable.setVisible(true);
		  		con.close();  
		    
  		 }catch(Exception e)
   		{
   			System.out.println("Error in connection" + e);
   		} 
  	}
  	
  	public void updatedata(String a,String b,String c,String d,String h){
        try{
    		  Class.forName("com.mysql.jdbc.Driver");  
        	  Connection con=DriverManager.getConnection("jdbc:mysql://"+sip+":3306/"+databasename,user,password);  
    		  Statement st=con.createStatement();
    		  String ena=aesen.encrypt(a);
    		  String enc=aesen.encrypt(c);
    		  BigInteger enb=BigInteger.ZERO;
    		  BigInteger end=BigInteger.ZERO;
    		  if(isInteger(b)){ //Change this section later as per string value or id, sap BigInteger token val  
    			   
    			  BigInteger b1=new BigInteger(b);
        		  enb=paillier.Encryption(b1,r);  
    		  }else{
    			  enb=paillier.EncrypStr(b,r);
    		  }
    		  if(isInteger(d)){ //Change this section later as per string value or id, sap BigInteger token val
    			  System.out.println("In d1");
    			  BigInteger d1=new BigInteger(d);
    		      end=paillier.Encryption(d1,r);
    		  }else{  
    			  end=paillier.EncrypStr(d,r);
    		  }
    		  System.out.println(a+enb+c+end);
    		  String enh=aesen.encrypt(h);
			String up="UPDATE `"+enh+"` SET `" +ena+ "`= '"+enb+"' WHERE `"+enc+"` = '"+end+"'";
    		  
         st.executeUpdate(up);
         st.executeQuery("SELECT * FROM `"+enh+"`");
	       
         ResultSet rs = st.getResultSet();
	  		encryptedTable=new JFrame();
	  		encryptedTable.setSize(640,220);
	  		encryptedTable.setLocation(460,0);
	  		JTable t1=new JTable(buildTableModel(rs));
	  		JScrollPane sp = new JScrollPane(t1);
	  		sp.setBounds(460,10,640,210);
	  		encryptedTable.add(sp);
	  		encryptedTable.setVisible(true);
	  		decryptedTable=new JFrame();
	  		decryptedTable.setSize(640,220);
	  		decryptedTable.setLocation(460,230);
	  		JTable t2=new JTable(builddecryptedTableModel(rs));
	  		JScrollPane sp1 = new JScrollPane(t2);
	  		sp.setBounds(460,10,640,210);
	  		decryptedTable.add(sp1);
	  		decryptedTable.setVisible(true);

	    		con.close();  
	    
        }catch(Exception e)
  		{
  			System.out.println("Error in connection" + e);
  		} }
  	public static boolean isInteger(String s) {
  	    try { 
  	        Integer.parseInt(s); 
  	    } catch(NumberFormatException e) { 
  	        return false; 
  	    } catch(NullPointerException e) {
  	        return false;
  	    }
  	    // only got here if we didn't return false
  	    return true;
  	}
  	public void houpdatedata(String a,String b,String c,String d,String g,String f,String h){
        try{
    		  Class.forName("com.mysql.jdbc.Driver");  
        	  Connection con=DriverManager.getConnection("jdbc:mysql://"+sip+":3306/"+databasename,user,password);  
    		  Statement st=con.createStatement();		BigInteger enb=BigInteger.ZERO;
    		  BigInteger end=BigInteger.ZERO;
    		  String enh=aesen.encrypt(h);
    		  //hocol1,ndata,col2,odata,sym,data,n4
    		  if(isInteger(f)){ //Change this section later as per string value or id, sap BigInteger token val      			   
    			  BigInteger b1=new BigInteger(f);
    			  enb=paillier.Encryption(b1,r);  
    		  }else{
    			  enb=paillier.EncrypStr(b,r);
    		  }
    		  if(isInteger(d)){ //Change this section later as per string value or id, sap BigInteger token val
    			   BigInteger d1=new BigInteger(d);
    		      end=paillier.Encryption(d1,r);
    		  }else{  
    			  end=paillier.EncrypStr(d,r);
    		  }

    		  String up=null;
    		  ResultSet rs=null;
    		  String enc=aesen.encrypt(c);
    		  String ena=aesen.encrypt(a);
    		  if(g.equals("+")){
    		  String u="SELECT * FROM `"+enh+"` WHERE `"+enc+"` ='"+end+"'";
    		  st.executeQuery(u);
    		   rs = st.getResultSet();
    		  BigInteger m=BigInteger.ZERO;
   	       while(rs.next()){
   	    	   BigInteger em1=new BigInteger(rs.getString(1));
    		  m=em1.multiply(enb).mod(paillier.nsquare);}
   	          up="UPDATE `"+enh+"` SET `" +ena+ "`= '"+m+"' WHERE `"+enc+"` = '"+end+"'";
			
    		  }else if(g.equals("*")){
    			  String u="SELECT * FROM `"+enh+"` WHERE `"+enc+"` ='"+end+"'";
    			  st.executeQuery(u);
        		  rs = st.getResultSet();
        		  BigInteger m=BigInteger.ZERO;
       	       while(rs.next()){
       	    	   BigInteger em1=new BigInteger(rs.getString(1));
        		  m=em1.modPow(new BigInteger(f), paillier.nsquare);
        		  System.out.println(m);
        		  System.out.println("Dec:"+paillier.Decryption(m).toString());}
       	         up="UPDATE `"+enh+"` SET `" +ena+ "`= '"+m+"' WHERE `"+enc+"` = '"+end+"'";  
    		  }
         st.executeUpdate(up);
         st.executeQuery("SELECT * FROM `"+enh+"`");
	       
         rs = st.getResultSet();
	  		encryptedTable=new JFrame();
	  		encryptedTable.setSize(640,220);
	  		encryptedTable.setLocation(460,0);
	  		JTable t1=new JTable(buildTableModel(rs));
	  		JScrollPane sp = new JScrollPane(t1);
	  		sp.setBounds(460,10,640,210);
	  		encryptedTable.add(sp);
	  		encryptedTable.setVisible(true);
	  		decryptedTable=new JFrame();
	  		decryptedTable.setSize(640,220);
	  		decryptedTable.setLocation(460,230);
	  		JTable t2=new JTable(builddecryptedTableModel(rs));
	  		JScrollPane sp1 = new JScrollPane(t2);
	  		sp.setBounds(460,10,640,210);
	  		decryptedTable.add(sp1);
	  		decryptedTable.setVisible(true);
	  		con.close();  
	    
        }catch(Exception e)
  		{
  			System.out.println("Error in connection" + e);
  		} }
   private void prepareGUI(){
      mainFrame = new JFrame("Ui for SQL");
      mainFrame.setSize(460,440);
      mainFrame.setLayout(new GridLayout(11, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
        
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      
      mainFrame.add(headerLabel);
      mainFrame.setVisible(true);  
   }

   private void showTextFieldDemo(){
	   
      headerLabel.setText("Enter Desired Values to insert data");
      JLabel dblabel=new JLabel("Enter Database Name:");
      final JTextField dbname=new JTextField(8);
      JLabel passlabel=new JLabel("Enter Database Password:");
      final JPasswordField pass=new JPasswordField(8);
      JLabel ip=new JLabel("Enter IP: ",JLabel.CENTER);
      final JTextField serverip=new JTextField(15); 
      JLabel userlabel=new JLabel("Enter User Name:");
      final JTextField username=new JTextField(8);
      JLabel  select = new JLabel("SELECT * FROM ");
      final JTextField selectname = new JTextField(8);
      JLabel  li = new JLabel("CREATE TABLE");
      final JTextField nam = new JTextField(6);
      JLabel  li2 = new JLabel("( ", JLabel.CENTER);
      final JTextField attr1 = new JTextField(4);
      JLabel  li3 = new JLabel(", ", JLabel.CENTER);
      final JTextField attr2 = new JTextField(4);
      
      JLabel  li4 = new JLabel(", ", JLabel.CENTER);
      final JTextField attr3 = new JTextField(4);
      JLabel  li5 = new JLabel(") ", JLabel.CENTER);
      
      JLabel  la = new JLabel("INSERT INTO ", JLabel.CENTER);
      final JTextField t1 = new JTextField(4);
      JLabel  la1 = new JLabel("VALUES ", JLabel.CENTER);
      final JTextField val1 = new JTextField(3);
      JLabel  la2 = new JLabel("VALUES ", JLabel.CENTER);
      final JTextField val2 = new JTextField(3);
      JLabel  la3 = new JLabel("VALUES ", JLabel.CENTER);
      final JTextField val3 = new JTextField(3);
    
      JLabel  la4 = new JLabel("Update ", JLabel.CENTER);
      final JTextField t2 = new JTextField(4);
       JLabel  l4 = new JLabel(" set ", JLabel.CENTER);
      final JTextField val4 = new JTextField(4);
      JLabel  la5 = new JLabel("= ", JLabel.CENTER);
      final JTextField val5 = new JTextField(4);
      JLabel  la6 = new JLabel("where ", JLabel.CENTER);
      final JTextField val6 = new JTextField(4);
      JLabel  la7 = new JLabel("= ", JLabel.CENTER);
      final JTextField val7 = new JTextField(4);
      
      JLabel  la8 = new JLabel("Update ", JLabel.CENTER);
      final JTextField t3 = new JTextField(4);
      JLabel  la81 = new JLabel("set ", JLabel.CENTER);
      final JTextField val8 = new JTextField(3);
      JLabel  la9 = new JLabel("= ", JLabel.CENTER);
      final JTextField val9 = new JTextField(3);
      final JTextField val12 = new JTextField(2);
      final JTextField val13 = new JTextField(3);
      JLabel  la10 = new JLabel("where ", JLabel.CENTER);
      final JTextField val10 = new JTextField(3);
      JLabel  la11 = new JLabel("= ", JLabel.CENTER);
      final JTextField val11 = new JTextField(3);
      JButton convert= new JButton("Process");
      JButton clear = new JButton("Clear");
      clear.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
        	  if(encryptedTable!=null)
        	  encryptedTable.dispose();
        	  if(decryptedTable!=null)
        	  decryptedTable.dispose();
        	  t1.setText("");
        	  t2.setText("");
        	  t3.setText("");
        	  attr1.setText("");
              attr2.setText("");
              attr3.setText("");
              nam.setText("");
              val1.setText("");
              val2.setText("");
              val3.setText("");
              val4.setText("");
              val5.setText("");
              val6.setText("");
              val7.setText("");
              val8.setText("");
              val9.setText("");
              val10.setText("");
              val11.setText("");
              val12.setText("");
              val13.setText("");
              selectname.setText("");
                                      }
      });
      convert.addActionListener(new ActionListener() {
         @SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) { 
        	 
        	  String n1 = val1.getText();
        	  String col1 = val4.getText();
         	  String hocol1 = val8.getText();
         	  if(!serverip.getText().equals(""))
         	  sip=serverip.getText();
         	 if(!dbname.getText().equals(""))
         	  databasename=dbname.getText();
         	 if(!username.getText().equals(""))
            	  user=username.getText();
         	if(!pass.getText().equals(""))
         	  password=pass.getText();
        	  
       if(!val1.getText().equals("")){
            String n2= val2.getText(); 
            String n3 = val3.getText();
            String n4=t1.getText();
            insertdata(n1,n2,n3,n4);}
       else if(!nam.getText().equals("")){
    	   String na1=nam.getText();
    	   String na2=attr1.getText();
    	   String na3=attr2.getText();
    	   String na4=attr3.getText();
    	   create(na1,na2,na3,na4);
       }
       else if(!val4.getText().equals("")){    
           String ndata = val5.getText();                      
           String col2 = val6.getText();
           String odata = val7.getText();  
           String n4=t2.getText();
           updatedata(col1,ndata,col2,odata,n4);}
       
       else if(!val8.getText().equals("")){
    	   String ndata = val9.getText();                      
           String col2 = val10.getText();
           String odata = val11.getText();  
           String data = val13.getText();  
           String sym = val12.getText();
           String n4 = t3.getText();
           houpdatedata(hocol1,ndata,col2,odata,sym,data,n4);
       }
       else if(!selectname.getText().equals("")){
    	   String tname = selectname.getText();                      
           displaydata(tname);
       }}
         
      });
        
      controlPanel.add(ip);
      controlPanel.add(serverip);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      
      controlPanel.add(dblabel);
      controlPanel.add(dbname);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      
      controlPanel.add(userlabel);
      controlPanel.add(username);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      controlPanel.add(passlabel);
      controlPanel.add(pass);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      controlPanel.add(li);
      controlPanel.add(nam);
      controlPanel.add(li2);
      controlPanel.add(attr1);
      controlPanel.add(li3);
      controlPanel.add(attr2);
      controlPanel.add(li4);
      controlPanel.add(attr3);
      controlPanel.add(li5);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      controlPanel.add(la);
      controlPanel.add(t1);
      controlPanel.add(la1);       
      controlPanel.add(val1);
      controlPanel.add(la2);       
      controlPanel.add(val2);
      controlPanel.add(la3);       
      controlPanel.add(val3);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      controlPanel.add(la4);
      controlPanel.add(t2);
      controlPanel.add(l4);
      controlPanel.add(val4);
      controlPanel.add(la5);       
      controlPanel.add(val5);
      controlPanel.add(la6);       
      controlPanel.add(val6);
      controlPanel.add(la7);       
      controlPanel.add(val7);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
  
      controlPanel.add(la8);
      controlPanel.add(t3);
      controlPanel.add(la81);
      controlPanel.add(val8);
      controlPanel.add(la9);       
      controlPanel.add(val9);
      controlPanel.add(val12);
      controlPanel.add(val13);
      controlPanel.add(la10);       
      controlPanel.add(val10);
      controlPanel.add(la11);       
      controlPanel.add(val11);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      
      controlPanel.add(select);
      controlPanel.add(selectname);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      
      controlPanel.add(convert);
      controlPanel.add(clear);
      mainFrame.add(controlPanel);
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      
      mainFrame.setVisible(true);
      
   }
}
