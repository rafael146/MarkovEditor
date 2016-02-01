package action;

import java.awt.event.ActionEvent;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.Figure;
import org.jhotdraw.draw.action.AbstractSelectedAction;
import org.jhotdraw.util.ResourceBundleUtil;

import figures.Estado;




public class StateAtual extends AbstractSelectedAction {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -8448486105161426194L;
	public static final String ID = "edit.stateAtual";

	public StateAtual(DrawingEditor editor) {
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
		
		JFrame frame = new JFrame();
		int nunGrupos=atual.getGrupos().size();
		
		String list[]=new String[nunGrupos];
		
		for(int i=0;i<nunGrupos;i++){
			list[i]=Integer.toString(i);
			System.out.println(list[i]);
		}
       

        String a=(String) JOptionPane.showInputDialog(frame, "Pick a printer", "Input", JOptionPane.QUESTION_MESSAGE,
               null, list, "Titan");
       
		atual.setGrupoAtual(Integer.parseInt(a));
	}

}
