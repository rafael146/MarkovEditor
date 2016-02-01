package core;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jhotdraw.draw.Figure;


import figures.Estado;
import figures.Transicao;;


public class ArquivoPRISM implements Arquivo {

	@Override
	public void cabecalho(String begin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gerarCodigo(List<Estado> estados) {
		String cod = "";
		for (Estado estado : estados) {
			for(int i=0; i < estado.getQuantidadeGrupos(); i++) {
				Set<Transicao> transicoes = estado.getTransicoesPorGrupo(i);
				
				if(transicoes == null || transicoes.size()==0) continue;
				int quantidadeTransicoes = transicoes.size();
				cod += "[] s=" + estado.getId() + " -> ";
				
				int add = 0;
				for(Transicao transicao : estado.getTransicoesPorGrupo(i)) {
					cod += transicao.getPeso() + " : (s'=" +((Estado)transicao.getEndFigure()).getId()  + ")";
					add++;
					if(add < quantidadeTransicoes) {
						cod += " + ";
					}
				}
				cod += ";\n";
			}	
		}
		System.out.println(cod);
	}

}
