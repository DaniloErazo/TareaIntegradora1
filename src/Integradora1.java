import java.util.Arrays;
import java.util.Scanner;
public class Integradora1{
	public static Scanner sc = new Scanner(System.in);
	static final int BLACK = 1300000;
	static final int WHITE = 2600000;
	static final int PAINT = 980000;
	public static void main (String [] args){
		LocationE location; 				//Input for the location of the property
		String price; 						//Input with the price for each material
		String material; 					//Input for material
		int materialQuantity; 				//Input for the quantity of each material
		int use;							//Input for use of material 
		int productQuantity=0; 				//Input of how many materials are going to be typed 
		double fullBill=0; 					//Output for the total bill in one store or for the best quote
		double deliveryPrice=0; 			//Output for the price of takeout service
		double labourPriceV=0; 				//Output for the price of workforce
		int option;							//Input for the selected option in the menu
		
		System.out.println("Digite la cantidad total de productos a ingresar");
		productQuantity= sc.nextInt();
		location= typeLocation();
		
		String [] product= new String[productQuantity]; //Saves all the names of each material 
		int [] usage= new int[productQuantity];	//Saves the use for each material
		int [] quantity= new int[productQuantity];		//Saves the quantity of each material
		double [] priceHC= new double[productQuantity];	//Saves all the prices in Home Center
		double [] priceFC= new double[productQuantity];	//Saves all the prices in Ferreteria Centro
		double [] priceFB= new double[productQuantity];	//Saves all the prices in Ferreteria Barrio
		String [] placeBestPrice= new String[productQuantity];//Output with the store that has the cheapest price for each product 
		double [] bestPriceAllProducts= new double[productQuantity]; //Output with the cheapest price for each product
		
		for (int i=0; i<productQuantity; i++){
			Scanner sc2 = new Scanner(System.in);
			System.out.println("Digite el material");
			material=sc.next();
			System.out.println("Digite la cantidad");
			materialQuantity=sc.nextInt();
			System.out.println("Digite el uso (1 para Obra negra, 2 para Obra blanca o 3 para pintura)");
			use=sc2.nextInt();
			product[i]=material;
			usage[i]=use;
			quantity[i]=materialQuantity;
		}
		
		getPrice(product, priceHC, "HomeCenter");
		getPrice(product, priceFC, "Ferreteria del Centro");
		getPrice(product, priceFB, "Ferreteria del barrio");
		
		do{
			System.out.println("\nAhora que ha ingresado sus datos, elija una opción");
			System.out.println("1. Generar total de cuenta (domicilio y mano de obra incluidos) en HomeCenter");
			System.out.println("2. Generar total de cuenta (domicilio y mano de obra incluidos) en Ferreteria del Centro");
			System.out.println("3. Generar total de cuenta (domicilio y mano de obra incluidos) en Ferreteria del barrio");
			System.out.println("4. Consultar el mejor lugar para comprar cada producto y su precio unitario");
			System.out.println("5. Generar total de cuenta para la mejor cotización ");
			System.out.println("6. Generar la lista de los materiales para la obra negra");
			System.out.println("7. Generar la lista de los materiales para la Obra blanca");
			System.out.println("8. Generar la lista de los materiales para pintura");
			System.out.println("0. Terminar la ejecución :)");
			option = sc.nextInt();

			switch(option){
				case 1:{
					System.out.println("En Home Center es: ");
					fullBill= findNetPriceAllProducts(priceHC, quantity, productQuantity);		 
					deliveryPrice=priceTakeOutService(location, fullBill);									
					labourPriceV=labourPrice(usage);													
					System.out.println(fullBill+deliveryPrice+labourPriceV);
					break;
				}
				case 2:{
					System.out.println("En Ferreteria Centro es: ");																	
					fullBill= findNetPriceAllProducts(priceFC, quantity, productQuantity);
					deliveryPrice=priceTakeOutService(location, fullBill);
					labourPriceV=labourPrice(usage);
					System.out.println(fullBill+deliveryPrice+labourPriceV);
					break;
				} 
				case 3:{
					System.out.println("En Ferreteria del Barrio es: " );																
					fullBill= findNetPriceAllProducts(priceFB, quantity, productQuantity);
					deliveryPrice=priceTakeOutService(location, fullBill);
					labourPriceV=labourPrice(usage);
					System.out.println(fullBill+deliveryPrice+labourPriceV);
					break;
				} 
				case 4:{
					placeBestPrice=findPlaceBestPrice(priceHC, priceFC, priceFB, productQuantity, placeBestPrice);
					System.out.println("El mejor lugar para comprar es: "); 															
					bestPriceAllProducts=bestPlacetoBuyAndPrice(product, priceHC, priceFC, priceFB, placeBestPrice, bestPriceAllProducts);
					break;
				}
				case 5:{
					System.out.println("El total de la mejor cotización (domicilio y mano de obra incluida) es: "); 					
					fullBill=findNetPriceAllProducts(bestPriceAllProducts, quantity, productQuantity);
					deliveryPrice=priceTakeOutService(location, fullBill);
					labourPriceV=labourPrice(usage);
					System.out.println(fullBill+deliveryPrice+labourPriceV);
					break;
				}
				case 6:{
					System.out.println("Los productos para la obra negra son: ");														
					printArray(clasiffyProductsByUse(usage, product, 1));
					break;
				}
				case 7:{
					System.out.println("Los productos para la obra blanca son: ");														
					printArray(clasiffyProductsByUse(usage, product, 2));
					break;
				}
				case 8:{
					System.out.println("Los productos para la pintura son: ");														
					printArray(clasiffyProductsByUse(usage, product, 3));
					break;
				}
				default:
				System.out.println("Terminando la ejecución. Graciar por hacer uso del programa");
			}
		}while (option !=0);
	}
	/**
	* typeLocation is a method that asks the user where the property is located <br>
	* <b> pre: </b> the value inputted by user is either 1 or 2 or 3<br>
	* <b> pos: </b> location chosen from LocationE
	* @return location 
	*/
	public static LocationE typeLocation(){
		System.out.println("Digite la ubicación del inmueble(Centro: 1, Sur: 2, Norte: 3)");
		int locationBeforeSwitch = sc.nextInt();
		LocationE location= null;
		switch(locationBeforeSwitch){
			case 1:
				location = LocationE.CENTRO;
				break;
			case 2:
				location = LocationE.SUR;
				break;
			case 3:
				location = LocationE.NORTE;
		}
		return location;
	}
	/**
	* priceTakeOutService calculates the price for delivery  <br>
	* <b> pre: </b> location is defined.  <br>
	* <b> pos: </b> 
	* @param location where the property is
	* @param netPrice total price for the bill. netPrice &gt; 0 
	* @return takeOutPrice full price of delivery taken into account location and full bill 
	*/
	public static double priceTakeOutService (LocationE location, double netPrice){
		double takeOutPrice=0;
		if(location==LocationE.NORTE){
			if(netPrice<80000){
				takeOutPrice=120000;
			}else if (netPrice>80000 && netPrice<300000){
				takeOutPrice=28000;
			}else if(netPrice>=300000){
				takeOutPrice=0;
			}
		}
		if(location==LocationE.SUR){
			if(netPrice<80000){
				takeOutPrice=120000;
			}else if (netPrice>80000 && netPrice<300000){
				takeOutPrice=55000;
			}else if(netPrice>=300000){
				takeOutPrice=0;
			}
		}
		if(location==LocationE.CENTRO){
			if(netPrice<80000){
				takeOutPrice=50000;
			}else{
				takeOutPrice=0;
			}
		}
		return takeOutPrice;
	}
	/**
	* labourPrice calculates the price for workforce, checking which of the three types have to be charged    <br>
	* <b> pre: </b> use is defined, initialized and filled  <br>
	* <b> pos: </b> 
	* @param use contains the usage for every product that has been inputted 
	* @return price full price of workforce 
	*/
	public static double labourPrice (int[] use){
		double price=0;
		boolean black=false;
		boolean white=false;
		boolean paint=false;
		for (int i=0; i<use.length && !black; i++){
			if(use[i]==1){
				black=true;
				price=BLACK;
			}
		}
		for (int i=0; i<use.length && !white; i++){
			if(use[i]==2){
				white=true;
				price+=WHITE;
			}
		}
		for (int i=0; i<use.length && !paint; i++){
			if(use[i]==3){
				paint=true;
				price+=PAINT;
			}
		}
		return price;
	}
	/**
	* getPrice asks the user the price for all the products in one store and saves it in an array   <br>
	* <b> pre: </b> product is defined, initialized and filled. storeArray is defined and initialized. storeName corresponds to one of the three stores  <br>
	* <b> pos: </b> storeArray is completely filled 
	* @param product is the list of the products inputted by the user
	* @param storeArray is where the prices are being saved, there's one for each store
	* @param storeName is the name of the store whose prices are being asked 
	* @return storeArray which contains the price for each product in a specific store 
	*/
	public static double [] getPrice (String[] product, double[] storeArray, String storeName){
		for (int i=0; i<product.length; i++){
			System.out.println("Digite el costo en " + storeName + " para el producto: "+ product[i]+ " por unidad");
			double price=sc.nextDouble();
			storeArray[i]=price;
		}
		return storeArray;
	}
	/**
	* findPlaceBestPrice determines which store has the cheapest price for each product and saves it in an array  <br>
	* <b> pre: </b> priceHC, priceFC and priceFB are defined, initialized and filled.   <br>
	* <b> pos: </b> placeBestPrice is completely filled 
	* @param priceHC contains the price for all the products in HomeCenter
	* @param priceFC contains the price for all the products in Ferreteria del Centro
	* @param priceFB contains the price for all the products in Ferreteria del Barrio
	* @param productQuantity amount of inputted products. productQuantity &gt; 0 
	* @param placeBestPrice is an empty array for saving the store where each product is cheaper
	* @return placeBestPrice which contains the place where each product is cheaper
	*/
	public static String[] findPlaceBestPrice (double[] priceHC, double[] priceFC, double[] priceFB, int productQuantity, String[] placeBestPrice){
		String lowPrice="";
		for (int i=0; i<priceHC.length; i++){ 
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
	/**
	* bestPlacetoBuyAndPrice prints products with their best place to buy them and the price. And saves the price (lowest) in an array <br>
	* <b> pre: </b> product, priceHC, priceFC and priceFB, placeBestPrice are defined, initialized and filled. <br>
	* <b> pos: </b> bestPriceAllProducts is completely filled <br> 
	* @param product contains the name of all the products asked to the user 
	* @param priceHC contains the price for all the products in HomeCenter
	* @param priceFC contains the price for all the products in Ferreteria del Centro
	* @param priceFB contains the price for all the products in Ferreteria del Barrio
	* @param placeBestPrice contains the store where each product has the lowest cost 
	* @param bestPriceAllProducts is an empty array for saving the cheapest price for each product
	* @return bestPriceAllProducts which contains the cheapest price found for every product
	*/
	public static double[] bestPlacetoBuyAndPrice (String[] product, double[] priceHC, double[] priceFC, double[] priceFB, String[] placeBestPrice, double[] bestPriceAllProducts){ 		
		double bestPrice=0;
		for (int i=0; i<product.length; i++){
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
			System.out.println("- " + product[i] + ": " + placeBestPrice[i] + ", " + bestPrice);
		}
		return bestPriceAllProducts;
	}
	/**
	* findNetPriceAllProducts calculates the price for all the products if unit price and quantity are given <br>
	* <b> pre: </b> price and quantityPerProduct are defined, initialized and filled. <br>
	* <b> pos: </b> 
	* @param price contains the name of all the products asked to the user 
	* @param quantityPerProduct contains the quantity inputted for each product
	* @param quantityAllProducts amount of inputted products. quantityAllProducts &gt; 0 
	* @return netPrice is the full bill for the given prices
	*/
	public static double findNetPriceAllProducts (double [] price, int[] quantityPerProduct, int quantityAllProducts){  
		double netPrice=0;
		double[] netPriceByProduct = new double[quantityAllProducts];
		for(int i=0;i<price.length; i++){
			netPriceByProduct[i]=price[i]*quantityPerProduct[i];
		}
		for(int i=0;i<price.length; i++){
			netPrice+=netPriceByProduct[i];
		}
		return netPrice;
	}
	/**
	* clasiffyProductsByUse sorts products by the type of use (Obra negra, Blanca o Pintura) and saves it in an array<br>
	* <b> pre: </b> use, product and typeOfUse are defined, initialized and filled.<br>
	* <b> pos: </b> 
	* @param use contains the use for each product 
	* @param product contains the name of all the products inputted
	* @param typeOfUse contains the type of use in the building for each product. It can be 1(Obra negra), 2(Obra blanca) or 3 (Pintura) 
	* @return listOFProductsByUse contains the products used for the given use 
	*/
	public static String[] clasiffyProductsByUse (int[] use, String[] product, int typeOfUse){ 
		int count=0;
		for(int i=0;i<product.length; i++){
			if(use[i]==typeOfUse){
				count+=1;
			}
		}
		int[] indexUse = new int[count];
		int indexUsecount=0;
		for(int i=0;i<product.length; i++){
			if (use[i]==typeOfUse){
				indexUse[indexUsecount]=i;
				indexUsecount+=1;
			}
		}
		String[] listOFProductsByUse = new String[indexUse.length];
		for (int i=0; i<listOFProductsByUse.length; i++){
			listOFProductsByUse[i]=product[indexUse[i]];
		}
		return listOFProductsByUse;
	}
	/**
	* printArray prints the given array <br>
	* <b> pre: </b> array is defined, initialized and filled.<br>
	* <b> pos: </b> array is printed 
	* @param array is the one that is going to be printed  
	*/
	public static void printArray (String[] array){
		for (int i=0; i< array.length; i++){
			System.out.println(array[i]);
		}
	}
}