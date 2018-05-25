
import java.math.*;
import java.util.*;

public class pailier {
	private static final String FILENAME = "encryption key file";
	static numcount numb;	
	private BigInteger p, q, lambda;
	public BigInteger n;
	
	public BigInteger nsquare;
	private BigInteger g;
	private int bitLength;

	
	public pailier(int bitLengthVal, int certainty) {
	KeyGeneration(bitLengthVal, certainty);
	}

	public pailier() {

	}
	public BigInteger[] getPQ(){
		BigInteger[] res=new BigInteger[3];
		res[0]= new BigInteger(""+p);
		res[1]= new BigInteger(""+q);
		return res;
	}

	public void KeyGeneration(int bitLengthVal, int certainty) {
	bitLength = bitLengthVal;

	p = new BigInteger(bitLength / 2, certainty, new Random());
	q = new BigInteger(bitLength / 2, certainty, new Random());

	n = p.multiply(q);
	nsquare = n.multiply(n);

	g = new BigInteger("2");
	lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)).divide(
	p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));

	if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
	System.out.println("g is not good. Choose g again.");
	System.exit(1);
	}
	}
	
	public void KeyGeneration(int bitLengthVal, int certainty,BigInteger a,BigInteger b) {
	bitLength = bitLengthVal;

	p = a;
	q = b;

	n = p.multiply(q);
	nsquare = n.multiply(n);

	g = new BigInteger("2");
	lambda = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)).divide(
	p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));

	if (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).gcd(n).intValue() != 1) {
	System.out.println("g is not good. Choose g again.");
	System.exit(1);
	}
	}
	
	public BigInteger Encryption(BigInteger m, BigInteger r) {
	return g.modPow(m, nsquare).multiply(r.modPow(n, nsquare)).mod(nsquare);
	}


	public BigInteger Decryption(BigInteger c) {
	BigInteger u = g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).modInverse(n);
	return c.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n).multiply(u).mod(n);
	}

	public static void main(String[] str) {

	pailier paillier = new pailier();
	
	}	
public BigInteger EncrypStr(String st,BigInteger r){
	int temp=st.charAt(0);
	BigInteger num=new BigInteger(String.valueOf(temp));
	
	for(int i=1;i<st.length();i++)
	{
		temp=st.charAt(i);
		num=num.multiply(BigInteger.valueOf(1000)).add(BigInteger.valueOf(temp));
	}
	BigInteger enc=Encryption(num,r);
	return enc;}
	
String DecrpyStr(BigInteger num){
	BigInteger num1=Decryption(num);
	int strc = num1.toString().length();
    String m=num1.toString();
	if(strc % 3 != 0)
	    {
	       m = "0" + m;
	    }
	 String strd = "";
	 for (int i = 0; i < m.length(); i += 3)
	    {
	        strd += (char)(Integer.parseInt(m.substring(i, i + 3)));
	    }
	return strd;}}
	
