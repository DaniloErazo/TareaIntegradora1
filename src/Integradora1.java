import java.util.Scanner;
public class Integradora1{
	public static void main (String [] args){
		String location; //Input for the location of the property
		String material; //Input with all the information of each material (material/quantity/usage)
		String price; 	//Input with the prices (3) for each material
		Scanner sc = new Scanner(System.in);
		int productQuantity;
		
		System.out.println("Digite la cantidad total de productos a ingresar");
		productQuantity= sc.nextInt();
		System.out.println("Digite la ubicación del inmueble");
		location= sc.next();
		
		String [] product= new String[productQuantity]; //Saves the name of each material 
		String [] usage= new String[productQuantity];	//Saves the usage of each material
		int [] quantity= new int[productQuantity];		//Saves the quantity of each material
		double [] priceHC= new double[productQuantity];	//Saves all the prices in Home Center
		double [] priceFC= new double[productQuantity];	//Saves all the prices in Ferreteria Centro
		double [] priceFB= new double[productQuantity];	//Saves all the prices in Ferreteria Barrio
		
		for (int i=0; i<productQuantity; i++){
			System.out.println("Digite el material, la cantidad y el uso (Obra negra, blanca o pintura) separados por /");
			material=sc.next();
			String materialPart []= material.split("/");
			product[i]=materialPart[0];
			quantity[i]=Integer.parseInt(materialPart[1]);
			usage[i]=materialPart[2];
		}
		for (int i=0; i<productQuantity; i++){
			System.out.println("Digite el costo en HomeCenter, Ferreteria Centro y Ferreteria del Barrio para el producto: "+ product[i]+ " separados por /");
			price=sc.next();
			String pricePart []= price.split("/");
			priceHC[i]=Double.parseDouble(pricePart[0]);
			priceFC[i]=Double.parseDouble(pricePart[1]);
			priceFB[i]=Double.parseDouble(pricePart[2]);
		}
		

	}
}