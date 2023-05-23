package APS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import com.fazecast.jSerialComm.*;

public class ControlaSensores {
	private String temperatura;
	private String umidade;
	SerialPort minhaPorta = SerialPort.getCommPort("COM9");
	
	public ControlaSensores() {	
		minhaPorta.setComPortParameters(9600, 8, 1, 0);
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		
		boolean hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}
	}
	
	public void getTemperatura() {
		
		if(minhaPorta.openPort()) {
			int delay = 500;   // tempo de espera antes da 1ª execução da tarefa.
			int interval = 5000;  // intervalo no qual a tarefa será executada.
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
			  public void run() {
				Integer comando = 5;
				try {
					minhaPorta.getOutputStream().write(comando.byteValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 3000, 0);
				
				BufferedReader in = new BufferedReader(new InputStreamReader(minhaPorta.getInputStream()));
		        try {
		        	temperatura = in.readLine();
		        	System.out.println(temperatura);
		        }catch(IOException e) {
		        	System.out.println("Não foi possível ler a temperatura.");
		        }

			  }
			}, delay, interval);
		}else {
			System.out.println("porta fevhada");
		}
	}
	

}
