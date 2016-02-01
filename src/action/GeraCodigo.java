package action;

import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JFileChooser;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.AbstractViewAction;
import org.jhotdraw.app.action.edit.SelectAllAction;
import org.jhotdraw.app.action.file.SaveFileAction;
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
import figures.Estado;
import main.MarkovView;



import edu.umd.cs.findbugs.annotations.Nullable;

public class GeraCodigo extends AbstractViewAction{
	
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ID = "gera.codigo";
	private String diretorio="";
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
//			SaveFileAction save = new SaveFileAction(getApplication(), getApplication().getActiveView());
//			save.actionPerformed(e);
			
			//this.getDirectory();
			Gerador gerador = new Gerador(new ArquivoPRISM(), estados, "diretorio");
			gerador.gerar();
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
	        URIChooser chsr = (URIChooser) (view.getComponent()).getClientProperty("saveChooser");
	        if (chsr == null) {
	            chsr = getApplication().getModel().createSaveChooser(getApplication(), view);
	            view.getComponent().putClientProperty("saveChooser", chsr);
	        }
	        return chsr;
	    }
	    protected void getDirectory(){
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
		                        setDiretorio(uri.getPath());
		        			}
		                }
		            });
		        }
			
		}
	    private void setDiretorio(String d){
	    	diretorio=d;
	    }
	    private String getDiretorio(){
	    	return diretorio;
	    }
}


