import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Integradora1{
	public static void main (String [] args){
		String location; //Input for the location of the property
		String price; 	//Input with the prices (3) for each material
		String material; //Input for material
		int materialQuantity; //Input for the quantity of material
		String use;			//Input fot use of material 
		Scanner sc = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		Scanner sc3 = new Scanner(System.in);
		int productQuantity; //Input of how many materials are going to be typed 
		
		System.out.println("Digite la cantidad total de productos a ingresar");
		productQuantity= sc.nextInt();
		System.out.println("Digite la ubicación del inmueble");
		location= sc.next();
		
		String [] product= new String[productQuantity]; //Saves all the names of each material 
		String [] usage= new String[productQuantity];	//Saves the use for each material
		String [] placeBestPrice= new String[productQuantity];//Saves where the product is cheapest 
		int [] quantity= new int[productQuantity];		//Saves the quantity of each material
		double [] priceHC= new double[productQuantity];	//Saves all the prices in Home Center
		double [] priceFC= new double[productQuantity];	//Saves all the prices in Ferreteria Centro
		double [] priceFB= new double[productQuantity];	//Saves all the prices in Ferreteria Barrio
		
		for (int i=0; i<productQuantity; i++){
			System.out.println("Digite el material");
			material=sc.next();
			System.out.println("Digite la cantidad");
			materialQuantity=sc2.nextInt();
			System.out.println("Digite el uso (Obra negra, blanca o pintura)");
			use=sc3.nextLine();
			product[i]=material;
			usage[i]=use;
			quantity[i]=materialQuantity;
		}
		
		for (int i=0; i<productQuantity; i++){
			System.out.println("Digite el costo en HomeCenter, Ferreteria Centro y Ferreteria del Barrio para el producto: "+ product[i]+ " por unidad, separados por /");
			price=sc.next();
			String pricePart []= price.split("/");
			priceHC[i]=Double.parseDouble(pricePart[0]);
			priceFC[i]=Double.parseDouble(pricePart[1]);
			priceFB[i]=Double.parseDouble(pricePart[2]);
		}
		
		System.out.println("El precio de todos los productos en Home Center es: " + findNetPriceAllProducts(priceHC, quantity, productQuantity));
		System.out.println("El precio de todos los productos en Ferreteria Centro es: " + findNetPriceAllProducts(priceFC, quantity, productQuantity));
		System.out.println("El precio de todos los productos en Home Ferreteria del Barrio es: " + findNetPriceAllProducts(priceFB, quantity, productQuantity));
		
		for (int i=0; i<priceHC.length; i++){
			String lowPrice="";
			if(priceHC[i]<priceFC[i] && priceHC[i]<priceFB[i]){
				lowPrice="Home Center";
			}else if (priceFC[i]<priceHC[i] && priceFC[i]<priceFB[i]){
				lowPrice="Ferreteria Centro";
			}else if (priceFB[i]<priceHC[i] && priceFB[i]<priceFC[i]){
				lowPrice="Ferreteria de Barrio";
			}
			placeBestPrice[i]=lowPrice;
		}
		
		System.out.println("El mejor lugar para comprar es: ");
		for (int i=0; i<product.length; i++){
			double bestPrice=0;
			if(placeBestPrice[i]=="Home Center"){
				bestPrice=priceHC[i];
			}else if (placeBestPrice[i]=="Ferreteria Centro"){
				bestPrice=priceFC[i];
			}else if (placeBestPrice[i]=="Ferreteria de Barrio"){
				bestPrice=priceFB[i];
			}
			System.out.println(product[i] + ": " + placeBestPrice[i] + ", " + bestPrice);
		}
		
		System.out.println("Los productos para la obra negra son: ");
		Arrays.stream(showProductsByUse(usage, product, "Obra negra")).forEach(System.out::println);
	}
	public static int findNetPriceAllProducts (double [] price, int[] quantityPerProduct, int quantityAllProducts){
		int netPrice=0;
		double[] netPriceByProduct = new double[quantityAllProducts];
		for(int i=0;i<price.length; i++){
			netPriceByProduct[i]=price[i]*quantityPerProduct[i];
		}
		for(int i=0;i<price.length; i++){
			netPrice+=netPriceByProduct[i];
		}
		return netPrice;
	}
	public static String[] showProductsByUse (String[] use, String[] product, String typeOfUse){
		ArrayList<Integer> indexUse = new ArrayList<Integer>();
		for(int i=0;i<product.length; i++){
			if (use[i].equals(typeOfUse)){
				indexUse.add(i);
			}
		}
		String[] listOFProductsByUse = new String[indexUse.size()];
		for (int i=0; i<listOFProductsByUse.length; i++){
			listOFProductsByUse[i]=product[indexUse.get(i)];
		}
		return listOFProductsByUse;
	}
}