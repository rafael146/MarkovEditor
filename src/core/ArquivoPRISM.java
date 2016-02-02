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
