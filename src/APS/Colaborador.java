package APS;

public class Colaborador {
	private String nome;
	private String secretKey;
	private String cpf;
	
	public void createColaborador(String nome, String secretKey, String cpf){
		setNome(nome);
		setSecretKey(secretKey);
		setCpf(cpf);
	}
	
	private void setNome(String nome) {
		this.nome = nome;
	}
	
	private void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	private void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public void deletaColaborador() {
		this.nome = "";
		this.secretKey = "";
		this.cpf = "";
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getCPF() {
		return this.cpf;
	}
	
	public void showColaborador() {
		if(this.cpf == "" || this.cpf == null) {
			System.out.println("Nenhum colaborador cadastrado.");
		} else {			
			System.out.println("Nome: "+nome);
			System.out.println("CPF: "+cpf);
			System.out.println("Secret Key: "+secretKey);
		}
	}
	
	public boolean isSecretKey(String sensorKey) {
		System.out.println("*"+sensorKey+" "+secretKey);
		if(sensorKey == secretKey) {
			return true;
		} else {
			return false;
		}
	}
	
}
