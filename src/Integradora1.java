import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
public class Integradora1{
	public static Scanner sc = new Scanner(System.in);
	static final int NEGRA = 1300000;
	static final int BLANCA = 2600000;
	static final int PINTURA = 980000;
	public static void main (String [] args){
		locationE location; //Input for the location of the property
		String price; 	//Input with the price for each material
		String material; //Input for material
		int materialQuantity; //Input for the quantity of each material
		String use;			//Input fot use of material 
		Scanner sc2 = new Scanner(System.in);
		Scanner sc3 = new Scanner(System.in);
		int productQuantity=0; //Input of how many materials are going to be typed 
		
		System.out.println("Digite la cantidad total de productos a ingresar");
		productQuantity= sc.nextInt();
		location= typeLocation();
		
		String [] product= new String[productQuantity]; //Saves all the names of each material 
		String [] usage= new String[productQuantity];	//Saves the use for each material
		String [] placeBestPrice= new String[productQuantity];//Saves where the product is cheapest 
		int [] quantity= new int[productQuantity];		//Saves the quantity of each material
		double [] priceHC= new double[productQuantity];	//Saves all the prices in Home Center
		double [] priceFC= new double[productQuantity];	//Saves all the prices in Ferreteria Centro
		double [] priceFB= new double[productQuantity];	//Saves all the prices in Ferreteria Barrio
		double [] bestPriceAllProducts = new double[productQuantity]; 
		
		for (int i=0; i<productQuantity; i++){
			System.out.println("Digite el material");
			material=sc.next();
			System.out.println("Digite la cantidad");
			materialQuantity=sc.nextInt();
			System.out.println("Digite el uso (Obra negra, blanca o pintura)");
			use=sc2.nextLine();
			product[i]=material;
			usage[i]=use;
			quantity[i]=materialQuantity;
		}
		
		typePrice(product, productQuantity, priceHC, "HomeCenter");
		typePrice(product, productQuantity, priceFC, "Ferreteria del Centro");
		typePrice(product, productQuantity, priceFB, "Ferreteria del barrio");
		
		System.out.println("El precio de todos los productos en cada tienda, con domicilio y mano de obra incluida es: ");
		System.out.println("En Home Center es: ");
		double tmp= findNetPriceAllProducts(priceHC, quantity, productQuantity);		//tmp is temporal for net price in one store 
		double tmp2=priceTakeOutService(location, tmp);									//tmp2 is temporal for the price of takeout
		double tmp3=labourPrice(usage);													//tmp2 is temporal for labour price
		System.out.println(tmp+tmp2+tmp3);
		System.out.println("En Ferreteria Centro es: ");
		tmp= findNetPriceAllProducts(priceFC, quantity, productQuantity);
		tmp2=priceTakeOutService(location, tmp);
		tmp3=labourPrice(usage);
		System.out.println(tmp+tmp2+tmp3);
		System.out.println("En Ferreteria del Barrio es: " );
		tmp= findNetPriceAllProducts(priceFB, quantity, productQuantity);
		tmp2=priceTakeOutService(location, tmp);
		tmp3=labourPrice(usage);
		System.out.println(tmp+tmp2+tmp3);
		
		placeBestPrice=findPlaceBestPrice(priceHC, priceFC, priceFB, productQuantity);
		System.out.println("El mejor lugar para comprar es: "); 
		bestPriceAllProducts=bestPlacetoBuyAndPrice(product, priceHC, priceFC, priceFB, placeBestPrice); 
		System.out.println("El total de la mejor cotización es: " + findNetPriceAllProducts(bestPriceAllProducts, quantity, productQuantity));
		System.out.println("Los productos para la obra negra son: ");
		Arrays.stream(showProductsByUse(usage, product, "Obra negra")).forEach(System.out::println);
	}
	public static locationE typeLocation(){
		System.out.println("Digite la ubicación del inmueble(Centro: 1, Sur: 2, Norte: 3)");
		int locationBeforeSwitch = sc.nextInt();
		locationE location= null;
		switch(locationBeforeSwitch){
			case 1:
				location = locationE.CENTRO;
				break;
			case 2:
				location = locationE.SUR;
				break;
			case 3:
				location = locationE.NORTE;
		}
		return location;
	}
	public static double priceTakeOutService (locationE location, double netPrice){
		double takeOutPrice=0;
		if(location==locationE.NORTE){
			if(netPrice<80000){
				takeOutPrice=120000;
			}else if (netPrice>80000 && netPrice<300000){
				takeOutPrice=28000;
			}else if(netPrice>=300000){
				takeOutPrice=0;
			}
		}
		if(location==locationE.SUR){
			if(netPrice<80000){
				takeOutPrice=120000;
			}else if (netPrice>80000 && netPrice<300000){
				takeOutPrice=55000;
			}else if(netPrice>=300000){
				takeOutPrice=0;
			}
		}
		if(location==locationE.CENTRO){
			if(netPrice<80000){
				takeOutPrice=50000;
			}else{
				takeOutPrice=0;
			}
		}
		return takeOutPrice;
	}
	public static double labourPrice (String[] use){
		double price=0;
		boolean black=false;
		boolean white=false;
		boolean paint=false;
		for (int i=0; i<use.length && !black; i++){
			if(use[i].equals("Obra negra")){
				black=true;
				price=NEGRA;
			}
		}
		for (int i=0; i<use.length && !white; i++){
			if(use[i].equals("Obra blanca")){
				white=true;
				price+=BLANCA;
			}
		}
		for (int i=0; i<use.length && !paint; i++){
			if(use[i].equals("Pintura")){
				paint=true;
				price+=PINTURA;
			}
		}
		return price;
	}
	public static double [] typePrice (String[] product, int productQuantity, double[] storeArray, String storeName){
		for (int i=0; i<product.length; i++){
			System.out.println("Digite el costo en " + storeName + " para el producto: "+ product[i]+ " por unidad");
			double price=sc.nextDouble();
			storeArray[i]=price;
		}
		return storeArray;
	}
	//Determines which store has the cheapest price for each product and saves it in an array 
	public static String[] findPlaceBestPrice (double[] priceHC, double[] priceFC, double[] priceFB, int productQuantity){
		String [] placeBestPrice= new String[productQuantity];
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
		return placeBestPrice;
	}
	//Prints products with their best place to buy it and the price
	public static double[] bestPlacetoBuyAndPrice (String[] product, double[] priceHC, double[] priceFC, double[] priceFB, String[] placeBestPrice){ 		
		double [] bestPriceAllProducts = new double[product.length];
		for (int i=0; i<product.length; i++){
			double bestPrice=0;
			if(placeBestPrice[i]=="Home Center"){
				bestPrice=priceHC[i];
				bestPriceAllProducts[i]=priceHC[i];
			}else if (placeBestPrice[i]=="Ferreteria Centro"){
				bestPrice=priceFC[i];
				bestPriceAllProducts[i]=priceFC[i];
			}else if (placeBestPrice[i]=="Ferreteria de Barrio"){
				bestPrice=priceFB[i];
				bestPriceAllProducts[i]=priceFB[i];
			}
			System.out.println(product[i] + ": " + placeBestPrice[i] + ", " + bestPrice);
		}
		return bestPriceAllProducts;
	}
	//Calculates the price if all the products are bought in just one store
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
	//Determines all the products for what they are used (Negra, Blanca o Pintura)
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