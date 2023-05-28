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
	
}
