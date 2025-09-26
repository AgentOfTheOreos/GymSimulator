package gym.management.Sessions;

public enum SessionType {
	Pilates(60, 30),
	MachinePilates(80, 10),
	ThaiBoxing(100, 20),
	Ninja(150, 5);

	private final int price;
	private final int capacity;

	SessionType(int price, int capacity) {
		this.price = price;
		this.capacity = capacity;
	}

	public int getPrice() { return price; }
	public int getCapacity() { return capacity; }
}
