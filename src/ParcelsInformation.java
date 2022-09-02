import java.text.DecimalFormat;

public class ParcelsInformation {

	private String TrackingNumber;
	private String SenderName;
	private String RecipientName;
	private int PostcodeTo; // recipient postcode
	private int PostcodeFrom; // sender postcode
	private double ParcelWeight; // in kilogram
	private double ParcelLength; // in centimeter
	private double ParcelHeight; // in centimeter
	private String ShippingType; // Regular or Next day delivery
	private String ParcelType; // fragile or non-fragile
	private String ShippingCategory; // domestic or international
	private String ShippingDate;
	private String DeliveryDate; // initially set value to null

	public ParcelsInformation() {
		// TODO Auto-generated constructor stub
		this.TrackingNumber = null;
		this.SenderName = null;
		this.RecipientName = null;
		this.PostcodeTo = 0;
		this.PostcodeFrom = 0;
		this.ParcelWeight = 0.0;
		this.ParcelLength = 0.0;
		this.ParcelHeight = 0.0;
		this.ShippingType = null;
		this.ParcelType = null;
		this.ShippingCategory = null;
		this.ShippingDate = null;
		this.DeliveryDate = null;
	}

	public ParcelsInformation(String TrackinNumber, String SenderName, String RecipientName, int PostcodeTo,
			int PostcodeFrom, double ParcelWeight, double ParcelLength, double ParcelHeight, String shippingType,
			String ParcelType, String ShippingCategory, String ShippingDate, String DeliveryDate) {
		this.TrackingNumber = TrackinNumber;
		this.SenderName = SenderName;
		this.RecipientName = RecipientName;
		this.PostcodeTo = PostcodeTo;
		this.PostcodeFrom = PostcodeFrom;
		this.ParcelWeight = ParcelWeight;
		this.ParcelLength = ParcelLength;
		this.ParcelHeight = ParcelHeight;
		this.ShippingType = shippingType;
		this.ParcelType = ParcelType;
		this.ShippingCategory = ShippingCategory;
		this.ShippingDate = ShippingDate;
		this.DeliveryDate = DeliveryDate;
	}

	public void setTrackingNumber(String TrackingNum) {
		TrackingNumber = TrackingNum;
	}

	public void setSenderName(String Name) {
		SenderName = Name;
	}

	public void setRecipientName(String Name) {
		RecipientName = Name;
	}

	public void setPostcodeTo(int Postcode) {
		PostcodeTo = Postcode;
	}

	public void setPostcodeFrom(int Postcode) {
		PostcodeFrom = Postcode;
	}

	public void setParcelWeight(double Weight) {
		ParcelWeight = Weight;
	}

	public void setParcelLength(double Length) {
		ParcelLength = Length;
	}

	public void setParcelHeight(double Height) {
		ParcelHeight = Height;
	}

	public void setShippingType(String ShippingType) {
		this.ShippingType = ShippingType;
	}

	public void setParcelType(String ParcelType) {
		this.ParcelType = ParcelType;
	}

	public void setShippingCategory(String category) {
		ShippingCategory = category;
	}

	public void setShippingDate(String ShipDate) {
		ShippingDate = ShipDate;
	}

	public void setDeliveryDate(String DeliverDate) {
		DeliveryDate = DeliverDate;
	}

	public void setParcelsInformation(String TrackinNumber, String SenderName, String RecipientName, int PostcodeTo,
			int PostcodeFrom, double ParcelWeight, double ParcelLength, double ParcelHeight, String shippingType,
			String ParcelType, String ShippingCategory, String ShippingDate, String DeliveryDate) {
		this.TrackingNumber = TrackinNumber;
		this.SenderName = SenderName;
		this.RecipientName = RecipientName;
		this.PostcodeTo = PostcodeTo;
		this.PostcodeFrom = PostcodeFrom;
		this.ParcelWeight = ParcelWeight;
		this.ParcelLength = ParcelLength;
		this.ParcelHeight = ParcelHeight;
		this.ShippingType = shippingType;
		this.ParcelType = ParcelType;
		this.ShippingCategory = ShippingCategory;
		this.ShippingDate = ShippingDate;
		this.DeliveryDate = DeliveryDate;
	}

	public String getTrackingNumber() {
		return TrackingNumber;
	}

	public String getSenderName() {
		return SenderName;
	}

	public String getRecipientName() {
		return RecipientName;
	}

	public int getPostcodeTo() {
		return PostcodeTo;
	}

	public int getPostcodeFrom() {
		return PostcodeFrom;
	}

	public double getParcelWeight() {
		return ParcelWeight;
	}

	public double getParcelLength() {
		return ParcelLength;
	}

	public double getParcelHeight() {
		return ParcelHeight;
	}

	public String getShippingType() {
		return ShippingType;
	}

	public String getParcelType() {
		return ParcelType;
	}

	public String getShippingCategory() {
		return ShippingCategory;
	}

	public String getShippingDate() {
		return ShippingDate;
	}

	public String getDeliveryDate() {
		return DeliveryDate;
	}

	public String CalculateShippingRate() {
		DecimalFormat formater = new DecimalFormat("RM #,###.00");
		double shippingRate;
		if (ShippingCategory.equalsIgnoreCase("International"))
			shippingRate = ParcelWeight * 50.0;
		else if (ShippingCategory.equalsIgnoreCase("Domestic"))
			shippingRate = ParcelWeight * 7.0;
		else {
			System.out.println("Shipping Category not match");
			return formater.format(0.0);
		}

		if (ShippingType.equalsIgnoreCase("Next day delivery"))
			shippingRate += 10.0;
		return formater.format(shippingRate);
	}

	public String toString() {
		return ("Tracking Number : " + TrackingNumber + "\nSender : " + SenderName + "\nRecipient : " + RecipientName
		+ "\nRecipient Postcode :" + PostcodeTo + "\nSender Postcode : " + PostcodeFrom + "\nParcel Weight : "
				+ ParcelWeight + "\nParcel Length : " + ParcelLength + "\nParcel Height : " + ParcelHeight
				+ "\nShipping Type : " + ShippingType + "\nParcel Type : " + ParcelType + "\nShipping Category : "
				+ ShippingCategory + "\nShipping Date : " + ShippingDate + "\nDelivery Date : " + DeliveryDate+"\nShipping Rate : "+CalculateShippingRate());
	}
}
