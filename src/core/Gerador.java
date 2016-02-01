package core;

import java.io.Serializable;
import java.util.ArrayList;

import figures.Estado;

public class Gerador implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1503994813479163807L;
	private ArrayList<Estado> transicoes;
	private Arquivo arquivoGerado;
	private String diretorio;
	
	public Gerador(Arquivo arquivo, ArrayList<Estado> t, String d){
		this.arquivoGerado = arquivo;
		this.setTransicoes(t);
		this.setDiretorio(d);
		
	}
	
	public void gerar() {
		arquivoGerado.gerarCodigo(transicoes);
	}

	private void setDiretorio(String d) {
		// TODO Auto-generated method stub
		this.diretorio=d;
	}

	public ArrayList<Estado> getTransicoes() {
		return transicoes;
	}

	public void setTransicoes(ArrayList<Estado> transicoes) {
		this.transicoes = transicoes;
	}

	public Arquivo getArquivoGerado() {
		return arquivoGerado;
	}

	public void setArquivoGerado(Arquivo arquivoGerado) {
		this.arquivoGerado = arquivoGerado;
	}
}
