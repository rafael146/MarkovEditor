package core;


/*
This file is part of MarkovEditor.

    Foobar is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.

*/


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
