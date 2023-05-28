package APS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class APS {
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

	public static void mudaTemperatura() {
		
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		Colaborador colaborador = new Colaborador();
		Scanner sc = new Scanner(System.in);
		boolean acesso = false;
		String menu = "-------------- Menu ----------------\n1- Cadastrar coladorador\n2- Deletar colaborador\n3- Entrar";
				
		while(true) {
			System.out.println("\n");
			System.out.println(menu);
			System.out.println("Escolha uma opção: ");
			int opcao = sc.nextInt();
			
			if(opcao == 1) {
				System.out.println("Digite o nome: ");
				String nome = sc.next();
				while(true) {
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
			}else if(opcao == 2) {
				System.out.println("Digite o cpf do colaborador que deseja deletar");
				String cpf = sc.next();
				if(validaCPF(cpf)) {
					String cpfColaborador = colaborador.getCPF();
					
					if(cpfColaborador != null && cpfColaborador.equals(cpf)) {
						String nomeColaborador = colaborador.getNome();
						
						System.out.println("Deseja deletar o(a) colaborador(a) " + nomeColaborador + "? (s/n)");
						String confirmacaoDelete = sc.next();
						if(!confirmacaoDelete.equalsIgnoreCase("s")) {
							System.out.println("Nenhum colaborador foi deletado do sistema");
							break;
						}
						
						colaborador.deletaColaborador();
						
						System.out.println("O colaborador " + nomeColaborador + ", portador do CPF " + cpfColaborador + " foi deletado do sistema.");
					}else {
						System.out.println("Colaborador não encontrado na base de dados");
					}
				}
				
			}else if(opcao == 3) {
				System.out.println("Aproxime a tag do sensor...");
				ControlaSensores arduinoSerial = new ControlaSensores();
				
				int count = 0;
				while(true) {
					Thread.sleep(5000);
					String respostaSensor = arduinoSerial.getID();
					if(respostaSensor.equals("permitido")) {
						System.out.println("Acesso permitido!");
						acesso = true;
						break;
					} else if(respostaSensor.equals("negado")) {
						System.out.println("Acesso não permitido. Tente novamente");
						acesso = false;
						count++;
						if(count >= 3) {
							System.out.println("Não foi possível encontrar a sua chave de acesso, procure o seu gerente.");
							break;
						}
					}
				
				}
				
				if(acesso) {
					System.out.println("Por favor, entre com o valor padrão do Ar Condicionado:");
					float valorArCondicionado = sc.nextFloat();
					boolean respostaTemp = true;
					// aguardar a resposta do arduino, primeiro valor vem nulo
					while(respostaTemp) {
						String tempSensor = arduinoSerial.getTemperatura();
						String umidadeSensor = arduinoSerial.getUmidade();
						Thread.sleep(10000);
						
						
						if(tempSensor != null ) {
							float tempSensorFloat = Float.parseFloat(tempSensor);
							
							// arduino começou a retornar valores muito altos para a temperatura
							if(tempSensorFloat < 40) {
								System.out.println("senor flora"+tempSensorFloat);
								float umidadeSensorFloat = Float.parseFloat(umidadeSensor);

								if(tempSensorFloat >= valorArCondicionado) {
								//if(16.00 >= valorArCondicionado) { // PARA TESTES
									System.out.println("\nTemperatura: " + tempSensorFloat + "º\nUmidade: " + umidadeSensorFloat +  "\nA temperatura está " + (tempSensorFloat - valorArCondicionado) + "º acima da ideal para a sala, diminuindo para " + valorArCondicionado + "º");
									break;
								}else {
									float diferenca = valorArCondicionado - tempSensorFloat;
									//float diferenca = (float) (valorArCondicionado - 16.00) ; // PARA TESTES
									System.out.println("\nTemperatura: " + tempSensorFloat + "º\nUmidade: " + umidadeSensorFloat + "\nA temperatura está " + diferenca + "º abaixo da ideal para a sala, aumentando para " + valorArCondicionado + "º");
									break;
								}
															}
						}else {
							System.out.println("Lendo sensores de temperatura e umidade...");
						}
					}
					
				} 
				
				break;
				
				
			} else {
				System.out.println("Opção não encontrada");
			}
			
			sc.close();
		}
	}
}
