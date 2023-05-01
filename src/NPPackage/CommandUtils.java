package NPPackage;

import java.io.IOException;

public class CommandUtils {

	public CommandUtils() {
		// TODO Auto-generated constructor stub
	}
	
	static void awaitUntil(long timeSecs) {
		long start = System.currentTimeMillis();
		long end = start + timeSecs;
		while (System.currentTimeMillis() < end) {
		}
	}
	
	static void awaitUntil() {
		try {
			System.out.println("\nAperte qualquer tecla para voltar!");
			System.in.read();
			clearScreen(12);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void awaitTimeAndRun(Runnable runnable, long delay) {
		long start = System.currentTimeMillis();
		long end = start + delay;
		while (System.currentTimeMillis() < end) {}
		runnable.run();
	}

	static void setTimeout(Runnable runnable, int delay){
	    new Thread(() -> {
	        try {
	            Thread.sleep(delay);
	            runnable.run();
	        }
	        catch (Exception e){
	            System.err.println(e);
	        }
	    }).start();
	}
	
	static void clearScreen() {  
		for (int i = 0; i < 50; ++i) System.out.println();
	} 
	
	static void clearScreen(int qtd) {  
		for (int i = 0; i < qtd; ++i) System.out.println();
	} 
}
