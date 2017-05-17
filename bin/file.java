
import java.io.*;
import java.io.IOException;
import java.math.BigInteger;

public class file{
	
	public boolean check(String F){
		boolean res=false;
		File f = new File(F);
		res = f.exists();
		return res;
		
	}
	public void fileWrite(BigInteger[] temp1,String FILENAME){
		File f = null;
	      boolean bool = false;
	      	      try {
	      
	         
	         f = new File(FILENAME);
	         
	         
	         bool = f.createNewFile();
	         
	         System.out.println("File created: "+bool);
	         PrintWriter writer = new PrintWriter(FILENAME, "UTF-8");
	         writer.write(temp1[0].toString());
	         writer.write(System.getProperty( "line.separator" ));
	         writer.write(temp1[1].toString());
	         writer.write(System.getProperty( "line.separator" ));
	         writer.write(temp1[2].toString());
	         writer.write(System.getProperty( "line.separator" ));
	         writer.close();
	      } catch(Exception e) {
	         e.printStackTrace();
	      }
	}
	public BigInteger[] getKey(String FILENAME){
		BufferedReader br = null;
  		FileReader fr = null;
  		String sCurrentLine=null;
  		BigInteger temp3[] = new BigInteger[3]; 
  		try {

  			fr = new FileReader(FILENAME);
  			br = new BufferedReader(fr);

  		

  			br = new BufferedReader(new FileReader(FILENAME));
  			int i=0;
  			while((sCurrentLine =br.readLine())!=null){
  			temp3[i]= new BigInteger(sCurrentLine);
  			i++;
  			}
  			
  		} catch (IOException e) {

  			e.printStackTrace();

  		} finally {
  			try {
  				if (br != null)
  					br.close();

  				if (fr != null)
  					fr.close();

  			} catch (IOException ex) {

  				ex.printStackTrace();

  			}

  		}
  		return temp3;

	}
}