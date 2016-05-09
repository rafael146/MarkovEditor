package action;


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


import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import main.MarkovView;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.AbstractViewAction;
import org.jhotdraw.app.action.edit.SelectAllAction;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.gui.JFileURIChooser;
import org.jhotdraw.gui.JSheet;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.gui.event.SheetEvent;
import org.jhotdraw.gui.event.SheetListener;
import org.jhotdraw.gui.filechooser.ExtensionFileFilter;
import org.jhotdraw.util.ResourceBundleUtil;

import core.ArquivoPRISM;
import core.Gerador;
import edu.umd.cs.findbugs.annotations.Nullable;
import figures.Estado;

public class GeraCodigo extends AbstractViewAction{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID = "gera.codigo";
	
	private ResourceBundleUtil labels = ResourceBundleUtil.getBundle("main.Labels");
	public GeraCodigo(Application app, @Nullable View view){
		super(app, view);
		labels.configureAction(this, ID);
	     
	}
	public GeraCodigo(Application app, @Nullable View view, DrawingEditor ed){
		super(app, view);
		labels.configureAction(this, ID);
	     
	}
	
	
	 @Override
	public MarkovView getActiveView() {
	        return (MarkovView) super.getActiveView();
	    }
	    
	    @Override
	    public void actionPerformed(ActionEvent e) {
	        DrawingEditor ed = getActiveView().getEditor();
	        SelectAllAction select = new SelectAllAction();
	        select.actionPerformed(e);
	       
	        DrawingView v =ed.getActiveView();
	       
			Set<Figure> figures=v.getSelectedFigures();
			Iterator< Figure> itr= figures.iterator();
			System.out.println(figures.size());
			ArrayList<Estado> estados = new ArrayList<Estado>(); 
			
			Estado atual=null;
			while(itr.hasNext()){
				Figure a =itr.next();
					if ( a instanceof Estado){
						atual= (Estado) a;
						estados.add(atual);
						System.out.println(atual.toString());
					}
					
			}
			if(estados.size()!=0){
				Salvar(estados);
			}else {
				JOptionPane.showMessageDialog(null,"Modelo vazio.\nInsira estados e Transições");
			}
			
			this.setEnabled(true);
	    }
	    
	    
	    @Override
	    protected void updateView() {
	        putValue(
	                Action.SELECTED_KEY,
	                getActiveView() != null 
	                );
	    }
	    protected URIChooser getChooser(View view) {
	    	JFileURIChooser c = new JFileURIChooser();
	        c.addChoosableFileFilter(new ExtensionFileFilter("PRISM Source", "nm"));
	       URIChooser a=(URIChooser) c.getComponent();

	        URIChooser chsr = a;// (URIChooser) (view.getComponent()).getClientProperty("saveChooser");
	        if (chsr == null) {
	            chsr = getApplication().getModel().createSaveChooser(getApplication(), view);
	            view.getComponent().putClientProperty("saveChooser", chsr);
	        }
	        return chsr;
	    }
	    protected void Salvar(ArrayList<Estado> estados){
	    	
	    	final View view = getActiveView();
	    	 
			
		    if (view.isEnabled()) {
		        view.setEnabled(false);

		        URIChooser fileChooser = getChooser(view);
		        JSheet.showSaveSheet(fileChooser, view.getComponent(), new SheetListener() {
		        	@Override
		            public void optionSelected(final SheetEvent evt) {
		        		
		        		if (evt.getOption() == JFileChooser.APPROVE_OPTION) {
		        			
		        			final URI uri;
		        			
		                    	if ((evt.getChooser() instanceof JFileURIChooser) && (evt.getFileChooser().getFileFilter() instanceof ExtensionFileFilter)) {
		                    		uri = ((ExtensionFileFilter) evt.getFileChooser().getFileFilter()).makeAcceptable(evt.getFileChooser().getSelectedFile()).toURI();
		                    		
		                    	} else {
		                        	uri = evt.getChooser().getSelectedURI();
		                        	
		                        }
		                    	
		                    	Gerador gerador = new Gerador(new ArquivoPRISM(), estados);
		            			gerador.gerar();
		            			String module= evt.getFileChooser().getSelectedFile().getName();
			            		String path = uri.getPath();
			            		System.out.println(gerador.getCod());
			            		gerador.salvarCod(module, path);
			            				
		                    	
		                }
		            }
		        	 
		        });
		        
		        }
			view.setEnabled(true);
			
		}
	    
}



