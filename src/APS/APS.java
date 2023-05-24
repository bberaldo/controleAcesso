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

public class APS {
	public static void controlaLED(boolean status) throws IOException {
		SerialPort minhaPorta = SerialPort.getCommPort("COM9");
		minhaPorta.setComPortParameters(9600, 8, 1, 0);
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		/*var hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}*/
		if(status == true) {
			System.out.println(status);
			Integer comando = 2;
			minhaPorta.getOutputStream().write(comando.byteValue());
		}else {
			Integer comando = 3;
			minhaPorta.getOutputStream().write(comando.byteValue());
		}
	}
	
	public static boolean validaCPF(String CPF) {
		CPF = CPF.replace(",", "").replace("-", "").replace("/", "").replace(".", "");
		
		if (CPF.equals("00000000000") ||
	        CPF.equals("11111111111") ||
	        CPF.equals("22222222222") || CPF.equals("33333333333") ||
	        CPF.equals("44444444444") || CPF.equals("55555555555") ||
	        CPF.equals("66666666666") || CPF.equals("77777777777") ||
	        CPF.equals("88888888888") || CPF.equals("99999999999") ||
	        (CPF.length() != 11))
            return false;

        char dig10, dig11;
        int sm, i, r, num, peso;

        try {
        	// 1o. Digito Verificador
        	sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
	        	// (48 é a posicao de '0' na tabela ASCII)
	            num = (int)(CPF.charAt(i) - 48);
	            sm = sm + (num * peso);
	            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
	            num = (int)(CPF.charAt(i) - 48);
	            sm = sm + (num * peso);
	            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }	
        }
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Colaborador colaborador = new Colaborador();
		Scanner sc = new Scanner(System.in);
		String menu = "-------------- Menu ----------------\n1- Cadastrar coladorador\n2- Deletar colaborador";
		/*SerialPort minhaPorta = SerialPort.getCommPort("COM9");
		minhaPorta.setComPortParameters(9600, 8, 1, 0);
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		
		var hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}*/
		
		/*
		int delay = 1000;   // tempo de espera antes da 1ª execução da tarefa.
		int interval = 5000;  // intervalo no qual a tarefa será executada.
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
		  public void run() {
			Integer comando = 1;
			try {
				minhaPorta.getOutputStream().write(comando.byteValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 3000, 0);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(minhaPorta.getInputStream()));
	        try {
	        	String acesso = in.readLine();
	        	System.out.println(acesso);
	        	if(acesso.equals("Acesso Permitido!")) {
	        		timer.cancel();
	        		System.out.println("Você está dentro do sistema");
	        		comando = 3;
	        		minhaPorta.getOutputStream().write(comando.byteValue());       		
	        	}
	        }catch(IOException e) {
	        	System.out.println("Aproxime a tag no sensor...");
	        }

		  }
		}, delay, interval);*/
		
		ControlaSensores sensores = new ControlaSensores();
		sensores.getCodeID();
		
		
		
		
		/*while(true) {
			System.out.println("\n");
			System.out.println(menu);
			System.out.println("Escolha uma opção: ");
			int opcao = sc.nextInt();
			switch(opcao) {
				case 1:
					while(true) {
						System.out.println("Digite o nome: ");
						String nome = sc.next();
						System.out.println("Digite o CPF:");
						String CPF = sc.next();
						
						if(validaCPF(CPF)) {					
							colaborador.createColaborador(nome, "123", CPF);
							System.out.println("Colaborador adicionado com sucesso!");
							break;
						}else {
							System.out.println("CPF inválido. Tente novamente.");
						}
					}
					break;
				case 2:
					System.out.println("Digite o cpf do colaborador que deseja deletar");
					String cpf = sc.next();
					if(validaCPF(cpf)) {
						String[] colaboradores = colaborador.getColaborador();
						
						System.out.println(colaboradores);
					}
			}
		}*/
		
		/*
		long timeStart = System.currentTimeMillis();
		
		SerialPort minhaPorta = SerialPort.getCommPort("COM9");
		minhaPorta.setComPortParameters(9600, 8, 1, 0);
		minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
						
		var hasOpen = minhaPorta.openPort();
		if (!hasOpen) {
			throw new IllegalStateException("Failed to open port");
		}
		
		Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print("\nEnter number of LED blinks (0 to exit): ");
            Integer blinks = input.nextInt();
            if(blinks == 0) break;
            Thread.sleep(3000);
            minhaPorta.getOutputStream().write(blinks.byteValue());
            
			minhaPorta.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 3000, 0);
	
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(minhaPorta.getInputStream()));
	        try {
	        	String umidadade = in.readLine();
	        	System.out.println(umidadade);
	        }catch(IOException e) {
	        	e.printStackTrace();
	        }
        }
        input.close();
		
		if(minhaPorta.closePort()) {
			System.out.println("Port is closed");
		}else {
			System.out.println("Port is not closed");
		}
		
		
		
		minhaPorta.closePort();*/
		
		
		
	}
}
