package main;


import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JToolBar;

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultApplicationModel;
import org.jhotdraw.app.View;
import org.jhotdraw.app.action.file.ExportFileAction;
import org.jhotdraw.app.action.view.ToggleViewPropertyAction;
import org.jhotdraw.app.action.view.ViewPropertyAction;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.AttributeKeys;
import org.jhotdraw.draw.DefaultDrawingEditor;
import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.TextAreaFigure;
import org.jhotdraw.draw.action.ApplyAttributesAction;
import org.jhotdraw.draw.action.ButtonFactory;
import org.jhotdraw.draw.action.PickAttributesAction;
import org.jhotdraw.draw.tool.ConnectionTool;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.draw.tool.TextAreaCreationTool;
import org.jhotdraw.draw.tool.Tool;
import org.jhotdraw.gui.JFileURIChooser;
import org.jhotdraw.gui.URIChooser;
import org.jhotdraw.gui.filechooser.ExtensionFileFilter;
import org.jhotdraw.util.ResourceBundleUtil;

import action.GeraCodigo;
import action.InsertND;
import action.StateAtual;
import figures.Estado;
import figures.Transicao;
import edu.umd.cs.findbugs.annotations.Nullable;


public class MarkovApplicationModel extends DefaultApplicationModel {
    private static final long serialVersionUID = 1L;

    private static final double[] scaleFactors = {5, 4, 3, 2, 1.5, 1.25, 1, 0.75, 0.5, 0.25, 0.10};
    private static class ToolButtonListener implements ItemListener {

        private Tool tool;
        private DrawingEditor editor;

        public ToolButtonListener(Tool t, DrawingEditor editor) {
            this.tool = t;
            this.editor = editor;
        }

        @Override
        public void itemStateChanged(ItemEvent evt) {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                editor.setTool(tool);
               
            }
        }
    }
    /**
     * This editor is shared by all views.
     */
    private DefaultDrawingEditor sharedEditor;
    /** Creates a new instance. */
    public MarkovApplicationModel() {
    	getSharedEditor();
    }

    @Override
    public ActionMap createActionMap(Application a, @Nullable View v) {
        ActionMap m = super.createActionMap(a, v);
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getBundle("main.Labels");
        AbstractAction aa;

        m.put(ExportFileAction.ID, new ExportFileAction(a, v));
        m.put(GeraCodigo.ID, new GeraCodigo(a, v, getSharedEditor()));
        
        m.put("view.toggleGrid", aa = new ToggleViewPropertyAction(a, v, MarkovView.GRID_VISIBLE_PROPERTY));
        drawLabels.configureAction(aa, "view.toggleGrid");
        for (double sf : scaleFactors) {
            m.put((int) (sf * 100) + "%",
                    aa = new ViewPropertyAction(a, v, DrawingView.SCALE_FACTOR_PROPERTY, Double.TYPE, new Double(sf)));
            aa.putValue(Action.NAME, (int) (sf * 100) + " %");

        }
        return m;
    }

    public DefaultDrawingEditor getSharedEditor() {
        if (sharedEditor == null) {
            sharedEditor = new DefaultDrawingEditor();
        }
        return sharedEditor;
    }

    @Override
    public void initView(Application a, @Nullable View p) {
        if (a.isSharingToolsAmongViews()) {
        	((MarkovView) p).setEditor(getSharedEditor());
        	
        }
    }

    private void addCreationButtonsTo(JToolBar tb, final DrawingEditor editor) {
        // AttributeKeys for the entitie sets
        HashMap<AttributeKey<?>, Object> attributes;

        ResourceBundleUtil labels = ResourceBundleUtil.getBundle("main.Labels");
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getBundle("main.Labels");

        ButtonFactory.addSelectionToolTo(tb, editor,createDrawingActions(editor), createSelectionActions(editor));
        tb.addSeparator();

        attributes = new HashMap<AttributeKey<?>, Object>();
        attributes.put(AttributeKeys.FILL_COLOR, Color.white);
        attributes.put(AttributeKeys.STROKE_COLOR, Color.black);
        attributes.put(AttributeKeys.TEXT_COLOR, Color.black);
        ButtonFactory.addToolTo(tb, editor, new CreationTool(new Estado(), attributes), "edit.createstate", labels);

        attributes = new HashMap<AttributeKey<?>, Object>();
       // attributes.put(AttributeKeys.STROKE_COLOR, new Color(0xFF0000));
        ButtonFactory.addToolTo(tb, editor, new ConnectionTool(new Transicao(), attributes), "edit.createDependency", labels);
        tb.addSeparator();
        ButtonFactory.addToolTo(tb, editor, (Tool) new TextAreaCreationTool(new TextAreaFigure()), "edit.createTextArea", drawLabels);

    }
    public static Collection<Action> createDrawingActions(DrawingEditor editor) {
        LinkedList<Action> a = new LinkedList<Action>();
       // a.add(new CutAction());
        //a.add(new CopyAction());
        //a.add(new PasteAction());
        a.add(new InsertND(editor));
        a.add(new StateAtual(editor));

        return a;
    }
   

    public static Collection<Action> createSelectionActions(DrawingEditor editor) {
        LinkedList<Action> a = new LinkedList<Action>();
//        a.add(new InsertND(editor));
//
        a.add(null); // separator

        return a;
    }

    /**
     * Creates toolbars for the application.
     * This class always returns an empty list. Subclasses may return other
     * values.
     */
    @Override
    public java.util.List<JToolBar> createToolBars(Application a, @Nullable View pr) {
        ResourceBundleUtil drawLabels = ResourceBundleUtil.getBundle("main.Labels");
        MarkovView p = (MarkovView) pr;

        DrawingEditor editor;
        if (p == null) {
            editor = getSharedEditor();
        } else {
            editor = p.getEditor();
        }

        LinkedList<JToolBar> list = new LinkedList<JToolBar>();
        JToolBar tb;
        tb = new JToolBar();
        addCreationButtonsTo(tb, editor);
        tb.setName(drawLabels.getString("window.drawToolBar.title"));
        list.add(tb);
        tb = new JToolBar();
        
        
     //   ButtonFactory.addAttributesButtonsTo(tb, editor);
        tb.add(new PickAttributesAction(editor));
        
        tb.add(new ApplyAttributesAction(editor));
        tb.addSeparator();
        ButtonFactory.addColorButtonsTo(tb, editor);
        tb.addSeparator();
        ButtonFactory.addFontButtonsTo(tb, editor);
        tb.setName(drawLabels.getString("window.attributesToolBar.title"));
        list.add(tb);
//       
        return list;
    }


    @Override
    public URIChooser createOpenChooser(Application a, @Nullable View v) {
        JFileURIChooser c = new JFileURIChooser();
        c.addChoosableFileFilter(new ExtensionFileFilter("PDM Diagram", "xml"));
        return c;
    }

    @Override
    public URIChooser createSaveChooser(Application a, @Nullable View v) {
        JFileURIChooser c = new JFileURIChooser();
        c.addChoosableFileFilter(new ExtensionFileFilter("PDM Diagram", "xml"));
        return c;
    }
    @Override
    public URIChooser createExportChooser(Application a, View v) {
    	JFileURIChooser c = new JFileURIChooser();
        c.addChoosableFileFilter(new ExtensionFileFilter("PNG", "png"));
        return c;
    }
   
}
