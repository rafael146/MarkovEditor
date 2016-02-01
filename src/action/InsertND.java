package action;

import java.awt.event.ActionEvent;
import java.util.Set;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.util.ResourceBundleUtil;

import figures.Estado;




public class InsertND extends AbstractSelectedAction {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8448486105161426194L;
	public static final String ID = "edit.insertND";

	public InsertND(DrawingEditor editor) {
		 super(editor);
	        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("main.Labels");
	        labels.configureAction(this, ID);
	        updateEnabledState();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		DrawingView view=  getView();
		Set<Figure> figures=view.getSelectedFigures();
		
		Estado atual=(Estado) figures.iterator().next();
		int nunGrupos=atual.getGrupos().size();

		atual.getGrupos().add(nunGrupos);
		
	}

}
