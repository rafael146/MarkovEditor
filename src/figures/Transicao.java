
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


import static org.jhotdraw.draw.AttributeKeys.END_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.FONT_ITALIC;
import static org.jhotdraw.draw.AttributeKeys.FONT_UNDERLINE;
import static org.jhotdraw.draw.AttributeKeys.START_DECORATION;
import static org.jhotdraw.draw.AttributeKeys.STROKE_COLOR;
import static org.jhotdraw.draw.AttributeKeys.STROKE_DASHES;
import static org.jhotdraw.draw.AttributeKeys.STROKE_WIDTH;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.LabeledLineConnectionFigure;
import org.jhotdraw.draw.TextFigure;
import org.jhotdraw.draw.connector.Connector;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.handle.BezierNodeHandle;
import org.jhotdraw.draw.handle.ConnectionEndHandle;
import org.jhotdraw.draw.handle.ConnectionStartHandle;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.layouter.LocatorLayouter;
import org.jhotdraw.draw.locator.BezierLabelLocator;
import org.jhotdraw.xml.DOMInput;
import org.jhotdraw.xml.DOMOutput;

import exception.PesoException;


public class Transicao extends LabeledLineConnectionFigure {
    private static final long serialVersionUID = 1L;
    //private TextFigure label =new TextFigure("");
    private int grupo;
    private double peso;
    private String Label;
    /** Creates a new instance. */
    public Transicao() {
        set(STROKE_COLOR, new Color(0xFF0000));
        set(STROKE_WIDTH, 1d);
        set(END_DECORATION, new ArrowTip());

        setAttributeEnabled(END_DECORATION, false);
        setAttributeEnabled(START_DECORATION, false);
        setAttributeEnabled(STROKE_DASHES, false);
        setAttributeEnabled(FONT_ITALIC, false);
        setAttributeEnabled(FONT_UNDERLINE, false);
        setAttributeEnabled(STROKE_COLOR, true);
        
        
        setGrupo(0);
      //  add(label);
        peso=0;
        setLayouter(new LocatorLayouter());
        
    }

    /**
     * Checks if two figures can be connected. Implement this method
     * to constrain the allowed connections between figures.
     */
    @Override
    public boolean canConnect(Connector start, Connector end) {
    	
        if ((start.getOwner() instanceof Estado)
                && (end.getOwner() instanceof Estado)) {

            Estado sf = (Estado) start.getOwner();
            Estado ef = (Estado) end.getOwner();
            this.setGrupo(sf.getGrupoAtual());
           

            // Disallow multiple connections to same dependent
          
				if (sf.getSuccessors().contains(ef) || sf.getPesoGrupo()>=1) {
					
				    return false;
				}
            
            return true;
           
        }

        return false;
    }
    


    @Override
    public boolean canConnect(Connector start) {
    	
        return (start.getOwner() instanceof Estado);
    	
    }

    /**
     * Handles the disconnection of a connection.
     * Override this method to handle this event.
     */
    @Override
    protected void handleDisconnect(Connector start, Connector end) {
        Estado sf = (Estado) start.getOwner();
        sf.removeDependency(this);
       
    }

    /**
     * Handles the connection of a connection.
     * Override this method to handle this event.
     */
    @Override
    protected void handleConnect(Connector start, Connector end) {
        Estado sf = (Estado) start.getOwner();
        Estado ef = (Estado) end.getOwner();
       
        double taxa = getPeso();
        
       
        sf.addDependency(this); 
        try {
        	System.out.println(getPeso());
        	if(getPeso()==0){
        		taxa=Double.parseDouble(JOptionPane.showInputDialog("Probabilidade da transição: "));
        		sf.isAddPesoGrupo(taxa);
        	}
        	
        	setPeso(taxa);
        	
        	
		} catch (NumberFormatException e) {
			// TODO: handle exception
			this.handleDisconnect(start, end);
			getDrawing().remove(this);	
		
		} catch( PesoException p){
			System.out.println(p.getMessage());
			getDrawing().remove(this);
			this.handleDisconnect(start, end);
		
		}

        Label= criaLabel(sf.getRotulo());
        System.out.println(sf.getRotulo());
        TextFigure t = new TextFigure(Label);
        t.setEditable(false);
        t.set(LocatorLayouter.LAYOUT_LOCATOR, new BezierLabelLocator(0.8,  Math.PI + Math.PI /  4, 0));
        this.add(t);
        
        
       // this.getBounds().
       // this.getLabel().changed();
       
        // getDrawing().remove(this); ///chamar caso levante excessao
    }
    
    @Override
    public Transicao clone() {
        Transicao that = (Transicao) super.clone();
        //label.setText("");
        that.grupo=0;
        that.peso=0;
        return that; 
    }

    @Override
    public int getLayer() {
        return 1;
    }

	//remove a figura do drawing
    @Override
    public void removeNotify(Drawing d) {
        if (getStartFigure() != null) {
            ((Estado) getStartFigure()).removeDependency(this);
        }
//        if (getEndFigure() != null) {
//            ((Estado) getEndFigure()).removeDependency(this);
//        }
        super.removeNotify(d);
    }

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}
	
	
	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		this.Label = label;
	}

	@Override
	public Collection<Handle> createHandles(int detailLevel) {
		 LinkedList<Handle> handles = new LinkedList<Handle>();
	        switch (detailLevel % 2) {
	            case 0:
	                for (int i = 0, n = path.size(); i < n; i++) {
	                    handles.add(new BezierNodeHandle(this, i, this));
	                    handles.add(new ConnectionStartHandle(this));
	                    handles.add(new ConnectionEndHandle(this));
	                }
	                break;
	            case 1:
	               // TransformHandleKit.addTransformHandles(this, handles);
	                break;
	            default:
	                break;
	        }
	        return handles;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Transição: "+ "origen->"+getStartFigure().toString()+" " + "destino->"+getEndFigure().toString() +" "+getPeso();
	}
	
	String criaLabel(String rotulo){
		char a='a';
		for (int i = 0; i <grupo; i++) {
			a++;
		}
		return rotulo+":"+a+" -> "+peso;
	}
	@Override
	public void write(DOMOutput out) throws IOException {
		// TODO Auto-generated method stub
		super.write(out);
		
		out.openElement("peso");
		out.writeObject(getPeso());
		out.closeElement();
		out.openElement("grupo");
		String g= ""+getGrupo();
		out.writeObject(g);
		out.closeElement();
		
		
	}
	
	@Override
	public void read(DOMInput in) throws IOException {
		super.read(in);
        in.openElement("peso");
        setPeso((double) in.readObject());
        in.closeElement();
        in.openElement("grupo");
        String g=((String) in.readObject());
        setGrupo(Integer.parseInt(g));
        in.closeElement();
        
	
	}
//	[] s=0 -> 1.0 : (s'=1);
//	[] s=0 -> 0.7 : (s'=0
//
}
