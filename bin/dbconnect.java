import java.sql.*;

public class dbconnect {
	public static void main(String[] args){
	try{
		Class.forName("com.mysql.jdbc.Driver");  
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","dbpassword");  
	  Statement st=con.createStatement();
      
       //st.execute("create student(r_no int NOT NULL, nam varchar(20) NOT NULL, sapid int NOT NULL, PRIMARY KEY(sapid))");
       //st.executeUpdate("insert into student values(01,'prakhar',500037782)");
       //st.executeUpdate("insert into student values(02,'kndheeraj',500038333)");
       //st.executeUpdate("insert into student values(03,'chaitanya',500032442)");
     // st.execute("delete from studen where rno=500032442");
	  st.execute("alter table stundata modify id  DECIMAL(65,0)");
	  st.execute("alter table stundata modify sap DECIMAL(65,0)");
	  st.executeUpdate("insert into stundata values(52315609810444231184751228032267191340672981634444423358657636904321213535402167202116416199798297772769791997756983071804835671880409459083309238259328158089779996596657778837643237109990366861037901981205010188500471268291635010940574799551696634605974212652851072079290122426964571268054032095178987166244,55712821074666375008999654345253631123544390482789509687322985316673841915133820961274676183308229317015959189714730953294642608928222356524688119706231883802307772060180485692557526512355392822836131817613273156453879985582491408688329412413592806364099206298108481629081450017654300780350881403382304104966,11615623247546257781825666529429881699648474191769567006381839288551025477960618511056446693044600851336881313537007732998934775132914483399741371522523554143322288830174239566367161949578561578908363794990001619212417412968838269669709613052108220880835405563232361081454190395673211159317011219433882506016)");
	  st.executeQuery("select * from stundata");
       
       ResultSet rs = st.getResultSet();
       while(rs.next())
       {
      	 System.out.println("Id is " + rs.getString(1));
      	 System.out.println("Name is " + rs.getString(2));
      	 System.out.println("Sapid is " + rs.getString(3));
      	 System.out.println("----------------------------");	
       }
    		con.close();  
    
	}catch(Exception e)
	{
		System.out.println("Error in connection" + e);
	}
  
    }
}