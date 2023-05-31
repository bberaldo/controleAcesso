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
	private String acessoLocal = "";
	private String uid = "";
	SerialPort minhaPorta;
	
	public ControlaSensores() {	
		minhaPorta = SerialPort.getCommPort("COM9");
		minhaPorta.setComPortParameters(9600, 8, 1, 0);
	}
	
	public String getTemperatura () {
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		
		boolean hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}
		
		int delay = 0;   // tempo de espera antes da 1ª execução da tarefa.
		int interval = 1000;  // intervalo no qual a tarefa será executada.
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
			} catch (IOException e) {
				// enquanto não recebe nada do arduino
			}
		  }
		}, delay, interval);
		
		return temperatura;
	}
	

	public String getUmidade () {
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		
		boolean hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}
		
		int delay = 0;   // tempo de espera antes da 1ª execução da tarefa.
		int interval = 1000;  // intervalo no qual a tarefa será executada.
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		  public void run() {
			Integer comando = 4;
			try {
				minhaPorta.getOutputStream().write(comando.byteValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 3000, 0);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(minhaPorta.getInputStream()));
	        
        	try {
				umidade = in.readLine();
			} catch (IOException e) {
				//enquanto não recebe nada do arduino...
			}
	       

		  }
		}, delay, interval);
				
		return umidade;
		
	}
	
	
	public String getID () {
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		int delay = 0;   // tempo de espera antes da 1ª execução da tarefa.
		int interval = 1000;  // intervalo no qual a tarefa será executada.
		Timer timer = new Timer();
		
		boolean hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}
		
		timer.scheduleAtFixedRate(new TimerTask() {
		  public void run () {
			Integer comando = 1;
			try {
				minhaPorta.getOutputStream().write(comando.byteValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 3000, 0);

			while(acessoLocal.equals("") || acessoLocal.equals("negado")) {
				BufferedReader in = new BufferedReader(new InputStreamReader(minhaPorta.getInputStream()));
	        	try {
	        		String acesso = in.readLine();
	        		if(acesso.equals("Acesso Permitido")) {
	        			acessoLocal = "permitido";
	        			setLED(true);
	        		} else if(acesso.equals("Acesso Negado")) {
	        			acessoLocal = "negado";
	        		}
	        	}catch(IOException e) {
	        		acessoLocal = "";
	        	}
	        	
	        	break;
	        }
			timer.cancel();
		  }
		  
		}, delay, interval);
		
		return acessoLocal;
	}
	
	
	private void setLED(boolean state) {
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		Integer comando = state ? 2 : 3;
				
		boolean hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}
		
		try {
			minhaPorta.getOutputStream().write(comando.byteValue());
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	
	public String getUID() throws IOException {
		int delay = 0;   // tempo de espera antes da 1ª execução da tarefa.
		int interval = 1000;  // intervalo no qual a tarefa será executada.
		Timer timer = new Timer();
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		
		boolean hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}
		
		timer.scheduleAtFixedRate(new TimerTask() {
		  public void run () {
			Integer comando = 7;
			try {
				minhaPorta.getOutputStream().write(comando.byteValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 3000, 0);
			
			while(uid == "") {
				BufferedReader in = new BufferedReader(new InputStreamReader(minhaPorta.getInputStream()));
				try {
					uid = in.readLine();
					timer.cancel();
				}catch(IOException e) {
					//Enquanto não vem resposta do arduino
				}
			}
        	
		  }
		  
		}, delay, interval);
			
    	return uid;

	}
	
}
