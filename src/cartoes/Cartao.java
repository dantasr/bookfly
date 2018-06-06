package cartoes;

public class Cartao {
	private int codigo;
	private int ano;
	private int mes;
	private int cvv;
	private int saldo;

	public Cartao(int codigo, int ano, int mes, int cvv, int saldo) {
		super();
		this.codigo = codigo;
		this.ano = ano;
		this.mes = mes;
		this.cvv = cvv;
		this.saldo = saldo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public int getSaldo() {
		return saldo;
	}

	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
}
