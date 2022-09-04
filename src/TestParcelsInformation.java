import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TestParcelsInformation {

	public static void main(String[] args) throws IOException {
		try {
			// Define BufferedReader and ArrayList
			BufferedReader br = new BufferedReader(new FileReader("courier.txt"));
			ArrayList<ParcelsInformation> parcelsList = new ArrayList<ParcelsInformation>();
			Scanner input = new Scanner(System.in);

			// loop to read all data from courier.txt
			String line = br.readLine();
			while (line != null) {
				StringTokenizer st = new StringTokenizer(line, ";");
				String TrackinNumber = st.nextToken();
				String SenderName = st.nextToken();
				String RecipientName = st.nextToken();
				int PostcodeTo = Integer.parseInt(st.nextToken());
				int PostcodeFrom = Integer.parseInt(st.nextToken());
				double ParcelWeight = Double.parseDouble(st.nextToken());
				double ParcelLength = Double.parseDouble(st.nextToken());
				double ParcelHeight = Double.parseDouble(st.nextToken());
				String shippingType = st.nextToken();
				String ParcelType = st.nextToken();
				String ShippingCategory = st.nextToken();
				String ShippingDate = st.nextToken();
				String DeliveryDate = st.nextToken();

				// Check if weight, height, and length less than zero or negative
				if(ParcelWeight<0 || ParcelLength<0 || ParcelHeight<0){
					System.out.println("Input is less than 0");
					System.exit(0);
				}
				
				// Create new instance of ParcelInformation with data recieved from courier.txt and store into parcelList array
				ParcelsInformation parcel = new ParcelsInformation(TrackinNumber, SenderName, RecipientName, PostcodeTo,
				PostcodeFrom, ParcelWeight, ParcelLength, ParcelHeight, shippingType, ParcelType,
				ShippingCategory, ShippingDate, DeliveryDate);
				parcelsList.add(parcel);

				// Update line with new line
				line = br.readLine();
			}
			
			// Define PrintWriter to writer
			PrintWriter writer = new PrintWriter(new FileWriter("courier.txt"));
			// Define format
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

			int choice=0;
			while(choice<5){
				// Menu to user select
				System.out.print(
				"\nMenu 1 - Add\nMenu 2 - Update\nMenu 3 - Delete\nMenu 4 - Display\nMenu 5 - Exit\n\nSelect with enter number - ");
				choice = input.nextInt();
				input.nextLine();
				if(choice<=0)
					choice = 5;

				String id;
				switch (choice) {
					// Adding parcel menu
					case 1:
						System.out.println("Add Parcel Information\n");
						System.out.print("Sender Name : ");
						String Sender = input.nextLine();

						System.out.print("Recipient Name : ");
						String Recipient = input.nextLine();

						System.out.print("Recipient Postcode : ");
						int RecipientPostCode = Integer.parseInt(input.nextLine());

						System.out.print("Sender Postcode : ");
						int SenderPostCode = Integer.parseInt(input.nextLine());

						double Weight=0;
						while(Weight<=0){
							System.out.print("Parcel Weight : ");
							Weight = Double.parseDouble(input.nextLine());
							if(Weight<=0){
								 System.out.println("Input is less than 0");
							}
						}

						double length=0;
						while(length<=0){
							System.out.print("Parcel Length : ");
							length = Double.parseDouble(input.nextLine());
							if(length<=0){
								 System.out.println("Input is less than 0");
							}
						}

						double height=0;
						while(height<=0){
							System.out.print("Parcel Height : ");
							height = Double.parseDouble(input.nextLine());
							if(height<=0){
								 System.out.println("Input is less than 0");
							}
						}

						System.out.print("Shipping Category : ");
						String category = input.nextLine();

						int expect;
						String code, shippingType;

						// Check if user put domestic or international
						if (category.equalsIgnoreCase("domestic")) {
							System.out.print("Send next day? Surcharge(RM10) [Y/N]");
							char nextDay = input.next().charAt(0);
							input.nextLine();
							if (nextDay == 'Y' || nextDay == 'y') {
								shippingType = "Next day delivery";
								expect = 1;
								code = "MYX";
							} else {
								shippingType = "Regular";
								expect = 7;
								code = "MYO";
							}
						} else {
							shippingType = "Regular";
							expect = 14;
							code = "INT";
						}

						System.out.print("Fragile ? [Y/N]");
						char fragile = input.next().charAt(0);
						input.nextLine();
						String ParcelType;
						if (fragile == 'Y' || fragile == 'y') {
							ParcelType = "Fragile";
						} else {
							ParcelType = "Non-fragile";
						}

						LocalDate date = LocalDate.now();
						String shippingDate = formatter.format(date);
						String DeliveryDate = formatter.format(LocalDate.now().plusDays(expect));

						// Generate tracking number
						String TrackingNumber = code + new Date().getTime();

						ParcelsInformation NewParcel = new ParcelsInformation(TrackingNumber, Sender, Recipient,
								RecipientPostCode, SenderPostCode, Weight, length, height, shippingType, ParcelType,
								category, shippingDate, DeliveryDate);
						
						// Adding to parceList array
						parcelsList.add(NewParcel);
						break;

					// Update delivery date menu
					case 2:
						System.out.println("Update Parcel Delivery Date\n");
						System.out.print("Enter Tracking Number to update : ");
						id = input.nextLine();

						for (int i = 0; i < parcelsList.size(); i++) {
							if (id.equalsIgnoreCase(parcelsList.get(i).getTrackingNumber())) {
								System.out.println("Current delivery date : " + parcelsList.get(i).getDeliveryDate());
								System.out.print("New delivery date : ");
								String newdate = input.nextLine();
								parcelsList.get(i).setDeliveryDate(newdate);
								System.out.println("Succesfully update delivery date\n");
								break;
							}
						}
						break;

					// Remove Parcel menu
					case 3:
						System.out.println("Remove Parcel\n");
						System.out.print("Enter Tracking Number to remove : ");
						id = input.nextLine();
						for (int i = 0; i < parcelsList.size(); i++) {
							if (id.equalsIgnoreCase(parcelsList.get(i).getTrackingNumber())) {
								System.out.println("\n" + parcelsList.get(i));
								System.out.print("\nAre you sure to delete this parcel information? [Y/N] : ");
								char confirm = input.next().charAt(0);
								input.nextLine();
								if (confirm == 'Y' || confirm == 'y') {
									parcelsList.remove(i);
									System.out.println("Succesfully remove\n");
								}
								break;
							}
						}
						break;

					// Display Menu
					case 4:
						System.out.println("Display\n");
						System.out.print(
								"1. All parcels in the system including the shipping rates\n2. Display all domestic parcels including the shipping rates\n3. Display all international parcels including the shipping rate\n4. Count and display fragile parcel and non-fragile parcels\n5. List and display large parcels (weight more than 5 kg)\n6. List and display all parcels that requested to be delivered on the next day\n\nEnter number - ");
						int DisplayOption = input.nextInt();
						input.nextLine();
						int count=1;
						if(DisplayOption<0 || DisplayOption>6){
							System.out.println("Please insert correct number");
						}
						switch (DisplayOption) {
							// Display all parcel information in parcelList
							case 1:
								count = 1;
								System.out.println("\nAll Parcel\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								System.out.printf("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",
										"#","Tracking Number", "Sender Name", "Recipient Name", "Recipient PostCode",
										"Sender PostCode", "Weight(Kg)", "Length(Cm)", "Height(Cm)",
										"Shipping Type", "Parcel Type", "Shipping Category", "Shipping Date",
										"Delivery Date", "Shipping Rates");
								System.out.println();
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								for (ParcelsInformation parcel : parcelsList) {
									System.out.format("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",count++, parcel.getTrackingNumber(),parcel.getSenderName(),parcel.getRecipientName(),parcel.getPostcodeTo(),parcel.getPostcodeFrom(),parcel.getParcelWeight(),parcel.getParcelLength(),parcel.getParcelHeight(),parcel.getShippingType(),parcel.getParcelType(),parcel.getShippingCategory(),parcel.getShippingDate(),parcel.getDeliveryDate(),parcel.CalculateShippingRate());
									System.out.println();
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								break;

							// Display all domestic parcel
							case 2:
								count = 1;
								System.out.println("\nAll Domestic Parcel\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								System.out.printf("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",
										"#","Tracking Number", "Sender Name", "Recipient Name", "Recipient PostCode",
										"Sender PostCode", "Weight(Kg)", "Length(Cm)", "Height(Cm)",
										"Shipping Type", "Parcel Type", "Shipping Category", "Shipping Date",
										"Delivery Date", "Shipping Rates");
								System.out.println();
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								for (ParcelsInformation parcel : parcelsList) {
									if(parcel.getShippingCategory().equalsIgnoreCase("Domestic")){
										System.out.format("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",count++, parcel.getTrackingNumber(),parcel.getSenderName(),parcel.getRecipientName(),parcel.getPostcodeTo(),parcel.getPostcodeFrom(),parcel.getParcelWeight(),parcel.getParcelLength(),parcel.getParcelHeight(),parcel.getShippingType(),parcel.getParcelType(),parcel.getShippingCategory(),parcel.getShippingDate(),parcel.getDeliveryDate(),parcel.CalculateShippingRate());
										System.out.println();
									}
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								break;

							// Display all international parcel
							case 3:
								count = 1;
								System.out.println("\nAll International Parcel\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								System.out.printf("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",
										"#","Tracking Number", "Sender Name", "Recipient Name", "Recipient PostCode",
										"Sender PostCode", "Weight(Kg)", "Length(Cm)", "Height(Cm)",
										"Shipping Type", "Parcel Type", "Shipping Category", "Shipping Date",
										"Delivery Date", "Shipping Rates");
								System.out.println();
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								for (ParcelsInformation parcel : parcelsList) {
									if(parcel.getShippingCategory().equalsIgnoreCase("International")){
										System.out.format("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",count++, parcel.getTrackingNumber(),parcel.getSenderName(),parcel.getRecipientName(),parcel.getPostcodeTo(),parcel.getPostcodeFrom(),parcel.getParcelWeight(),parcel.getParcelLength(),parcel.getParcelHeight(),parcel.getShippingType(),parcel.getParcelType(),parcel.getShippingCategory(),parcel.getShippingDate(),parcel.getDeliveryDate(),parcel.CalculateShippingRate());
										System.out.println();
									}
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								break;

							// Display all fragile parcel and non-fragile
							case 4:
								count = 1;
								System.out.println("\nFragile Parcel\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								System.out.printf("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",
										"#","Tracking Number", "Sender Name", "Recipient Name", "Recipient PostCode",
										"Sender PostCode", "Weight(Kg)", "Length(Cm)", "Height(Cm)",
										"Shipping Type", "Parcel Type", "Shipping Category", "Shipping Date",
										"Delivery Date", "Shipping Rates");
								System.out.println();
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								for (ParcelsInformation parcel : parcelsList) {
									if(parcel.getParcelType().equalsIgnoreCase("Fragile")){
										System.out.format("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",count++, parcel.getTrackingNumber(),parcel.getSenderName(),parcel.getRecipientName(),parcel.getPostcodeTo(),parcel.getPostcodeFrom(),parcel.getParcelWeight(),parcel.getParcelLength(),parcel.getParcelHeight(),parcel.getShippingType(),parcel.getParcelType(),parcel.getShippingCategory(),parcel.getShippingDate(),parcel.getDeliveryDate(),parcel.CalculateShippingRate());
										System.out.println();
									}
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								count = 1;
								System.out.println("\nNon-Fragile Parcel\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								System.out.printf("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",
										"#","Tracking Number", "Sender Name", "Recipient Name", "Recipient PostCode",
										"Sender PostCode", "Weight(Kg)", "Length(Cm)", "Height(Cm)",
										"Shipping Type", "Parcel Type", "Shipping Category", "Shipping Date",
										"Delivery Date", "Shipping Rates");
								System.out.println();
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								for (ParcelsInformation parcel : parcelsList) {
									if(parcel.getParcelType().equalsIgnoreCase("Non-Fragile")){
										System.out.format("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",count++, parcel.getTrackingNumber(),parcel.getSenderName(),parcel.getRecipientName(),parcel.getPostcodeTo(),parcel.getPostcodeFrom(),parcel.getParcelWeight(),parcel.getParcelLength(),parcel.getParcelHeight(),parcel.getShippingType(),parcel.getParcelType(),parcel.getShippingCategory(),parcel.getShippingDate(),parcel.getDeliveryDate(),parcel.CalculateShippingRate());
										System.out.println();
									}
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								break;

							// Display all large parcel that over 5kg
							case 5:
								count = 1;
								System.out.println("\nLarge Parcel\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								System.out.printf("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",
										"#","Tracking Number", "Sender Name", "Recipient Name", "Recipient PostCode",
										"Sender PostCode", "Weight(Kg)", "Length(Cm)", "Height(Cm)",
										"Shipping Type", "Parcel Type", "Shipping Category", "Shipping Date",
										"Delivery Date", "Shipping Rates");
								System.out.println();
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								for (ParcelsInformation parcel : parcelsList) {
									if(parcel.getParcelWeight()>5){
										System.out.format("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",count++, parcel.getTrackingNumber(),parcel.getSenderName(),parcel.getRecipientName(),parcel.getPostcodeTo(),parcel.getPostcodeFrom(),parcel.getParcelWeight(),parcel.getParcelLength(),parcel.getParcelHeight(),parcel.getShippingType(),parcel.getParcelType(),parcel.getShippingCategory(),parcel.getShippingDate(),parcel.getDeliveryDate(),parcel.CalculateShippingRate());
										System.out.println();
									}
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								break;
								
							// Display all next day delivery
							case 6:
								count = 1;
								System.out.println("\nNext Day Deliver Parcel\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								System.out.printf("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",
										"#","Tracking Number", "Sender Name", "Recipient Name", "Recipient PostCode",
										"Sender PostCode", "Weight(Kg)", "Length(Cm)", "Height(Cm)",
										"Shipping Type", "Parcel Type", "Shipping Category", "Shipping Date",
										"Delivery Date", "Shipping Rates");
								System.out.println();
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								for (ParcelsInformation parcel : parcelsList) {
									if(parcel.getShippingType().equalsIgnoreCase("Next day delivery")){
										System.out.format("%2s %17s %25s %25s %19s %17s %11s %11s %11s %19s %13s %19s %15s %15s %15s",count++, parcel.getTrackingNumber(),parcel.getSenderName(),parcel.getRecipientName(),parcel.getPostcodeTo(),parcel.getPostcodeFrom(),parcel.getParcelWeight(),parcel.getParcelLength(),parcel.getParcelHeight(),parcel.getShippingType(),parcel.getParcelType(),parcel.getShippingCategory(),parcel.getShippingDate(),parcel.getDeliveryDate(),parcel.CalculateShippingRate());
										System.out.println();
									}
								}
								System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
								break;
						}
						break;
					}
			}
			
			// Loop to write in courier.txt
			for(ParcelsInformation parcels: parcelsList){
				writer.println(parcels.getTrackingNumber()+";"+parcels.getSenderName()+";"+parcels.getRecipientName()+";"+parcels.getPostcodeTo()+";"+parcels.getPostcodeFrom()+";"+parcels.getParcelWeight()+";"+parcels.getParcelLength()+";"+parcels.getParcelHeight()+";"+parcels.getShippingType()+";"+parcels.getParcelType()+";"+parcels.getShippingCategory()+";"+parcels.getShippingDate()+";"+parcels.getDeliveryDate());
			}

			// close all filewriter	
			br.close();
			writer.close();
			input.close();

		// File reader error checker
		} catch (EOFException ex) {
			System.out.println("End of file error");
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		} catch (IOException ex) {
			System.out.println("Wrong input!!!");
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		} finally{
			System.out.println("Thank you for using this system");
		}
	}

}
