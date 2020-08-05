package mission.zoo;

public class Pig implements Runnable {
	void soundDuck() {
		System.out.println("꽥꽥~");
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			soundDuck();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
