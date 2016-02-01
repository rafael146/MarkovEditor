package core;

import java.util.List;

import figures.Estado;


public interface Arquivo {
	
	public void cabecalho(String begin);
	public void gerarCodigo(List<Estado> estados) ;

}
