
package figures;

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


import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.EllipseFigure;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.GraphicalCompositeFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.event.FigureAdapter;
import org.jhotdraw.draw.event.FigureEvent;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.layouter.VerticalLayouter;
import org.jhotdraw.geom.Insets2D;

import exception.PesoException;
import figures.Estado;
import figures.Transicao;

import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

public class Estado extends GraphicalCompositeFigure {
    private static final long serialVersionUID = 1L;

    private HashMap<Integer, HashSet<Transicao>> transicoes;
    private ArrayList<Integer> grupos;
    private int id=0;
    private static int idGeral=-1;
    private int grupoAtual=0;
    private String rotulo;
    private TextFigure tx; 
    
    private static class NameAdapter extends FigureAdapter {

        private Estado target;

        @Override
        public void attributeChanged(FigureEvent e) {
           
           // target.firePropertyChange("name", e.getOldValue(), e.getNewValue());
        }
    }

    public Estado() {
    	
    	
    	this.setPresentationFigure(new EllipseFigure(40,40,40,40));
    	this.setRotulo("s"+idGeral);
    	this.setId(idGeral);
    	this.tx= new TextFigure(rotulo);
    	this.tx.set(LAYOUT_INSETS,new Insets2D.Double(20,20,20,20));
    	this.tx.setBounds(new Point2D.Double(10, 10), new Point2D.Double(20, 20));
    	this.tx.setEditable(false);
    	//setRotulo(rotulo);
    	this.tx.setText(getRotulo());
    	this.add(tx);
    	this.transicoes = new HashMap<Integer, HashSet<Transicao>>();
    	this.setLayouter(new VerticalLayouter());
    	
    	this.grupos=new ArrayList<Integer>();
    	this.grupos.add(0);
    	idGeral++;
    	layout();
    }
    
    @Override
    public Estado clone() {
       Estado that = new Estado();
       setId(idGeral-1);
       System.out.println("idGeral: "+idGeral+" id "+id);
       return that;
    }
    
    @Override
	public void removeNotify(Drawing drawing) {
		// TODO Auto-generated method stub
		
		int dif=0;
		System.out.println(this.getId()+" "+ idGeral);
		if(this.getId()==(idGeral)-1){
			idGeral-=2;
			clone();
			//System.out.println("aki");
		}else if (this.getId()<(idGeral -1)) {
			
			dif=idGeral-this.getId();
			
			idGeral-=dif;
			System.out.println(idGeral+ " " +id +" "+dif);
			//clone();
			
		}
		super.removeNotify(drawing);
		
	}
    
    @Override
    public Collection<Handle> createHandles(int detailLevel) {
    	// TODO Auto-generated method stub
    	return super.createHandles(detailLevel);
    }


	
  
    @Override
    public void read(DOMInput in) throws IOException {
       
    	double x = in.getAttribute("x", 0d);
        double y = in.getAttribute("y", 0d);
        double w = in.getAttribute("w", 0d);
        double h = in.getAttribute("h", 0d);
        setBounds(new Point2D.Double(x, y), new Point2D.Double(x + w, y + h));
       // setRotulo(in.getAttribute("rotulo", ""));
        readAttributes(in);
        in.openElement("id");
        id=((int) in.readObject());
        
        in.closeElement();
        in.openElement("rotulo");
        setRotulo((String) in.readObject());
        tx.setText(getRotulo());
        System.out.println(getRotulo());
        in.closeElement();
        
        in.openElement("grupos");
        String numGrupo = (String)in.readObject();
        int nun=Integer.parseInt(numGrupo);
        for(int i=0; i<nun;i++){
        	this.getGrupos().add(i);
        }
        in.closeElement();
        
    }

    @Override
    public void write(DOMOutput out) throws IOException {
        Rectangle2D.Double r = getBounds();
        out.addAttribute("x", r.x);
        out.addAttribute("y", r.y);
        //out.addAttribute("rotulo", getRotulo());
        writeAttributes(out);
        out.openElement("rotulo");
        out.writeObject(getRotulo());
        out.closeElement();
        out.openElement("id");
        out.writeObject(getId());
        out.closeElement();
        out.openElement("grupos");
        out.writeObject(""+grupos.size());
        out.closeElement();
    }

    @Override
    public int getLayer() {
        return 0;
        
    }

    public Set<Transicao> getDependencies() {
    	Set<Transicao> trans = new HashSet<Transicao>();
    	for(Integer key : transicoes.keySet()) {
    		trans.addAll(transicoes.get(key));
    	}
        return Collections.unmodifiableSet(trans);
    }
    
    public Set<Transicao> getTransicoesPorGrupo(int grupo) {
    	return transicoes.get(grupo);
    }

    public void addDependency(Transicao f) {
        //destino.add(f); 
        if(!transicoes.containsKey(f.getGrupo())) {
        	transicoes.put(f.getGrupo(), new HashSet<Transicao>());
        }
        transicoes.get(f.getGrupo()).add(f);
    }

    public void removeDependency(Transicao f) {
    	transicoes.get(f.getGrupo()).remove(f);
    	//System.out.println(transicoes.get(f.getGrupo()));
        //destino.remove(f);
    	
    }


    /**
     * Returns dependent Estado which are directly connected via a
     * Transicao to this Estado.
     */
    public List<Estado> getSuccessors() {
        ArrayList<Estado>  list = new ArrayList<Estado>();
        for (Transicao c : getDependencies()) {
            if (c.getStartFigure() == this && c.getGrupo()==this.getGrupoAtual()) {
                list.add((Estado) c.getEndFigure());
            }

        }
        return list;
    }
    
   
    public List<Transicao> getTransicaoEstadoAtual(){
    	ArrayList<Transicao> list=new ArrayList<Transicao>();
    	for (Transicao t : getDependencies()) {
			if(t.getGrupo()==this.getGrupoAtual()){
				list.add(t);
			}
		}
    	
    	return list;
    }
    
    public boolean isAddPesoGrupo(double peso) throws PesoException{
    	double pesoTotal=getPesoGrupo();
    	if(pesoTotal + peso>1){
    		System.out.println(pesoTotal+peso);
    		throw new PesoException();
    		//return false;
    	}
    	
    	return true;
    }
	public double getPesoGrupo(){
		double pesoTotal=0;
		for (Transicao t : getTransicaoEstadoAtual()) {
			pesoTotal+=t.getPeso();
		}
		return pesoTotal;
	}

	@Override
    public String toString() {
        return "Estado#" + " " + "s"+id ;
    }
	
	public int getId() {
		return id;
	}
	public void setId(int id){
		this.id=id;
	}
	
    public int getGrupoAtual() {
		return grupoAtual;
	}

	public void setGrupoAtual(int grupoAtual) {
		this.grupoAtual = grupoAtual;
	}

	public TextFigure getTx() {
		return tx;
	}

	public void setTx(TextFigure tx) {
		this.tx = tx;
	}

	public String getRotulo() {
		return rotulo;
	}

	
	private void setRotulo(String rotulo) {
		this.rotulo = rotulo;
		
	}
	
	public ArrayList<Integer> getGrupos() {
		return grupos;
	}
	
	public int getQuantidadeGrupos() {
		return grupos.size();
		
	}

	public void setGrupos(ArrayList<Integer> grupos) {
		this.grupos = grupos;
	}
	
	public void addGrupo(int i){
		grupos.add(i);
	}


	public void removeGrupo(int i){
		grupos.remove(i);
	}
	

}

