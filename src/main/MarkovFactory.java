package main;



import org.jhotdraw.draw.connector.ChopRectangleConnector;
import org.jhotdraw.draw.connector.LocatorConnector;
import org.jhotdraw.draw.decoration.ArrowTip;
import org.jhotdraw.draw.locator.RelativeLocator;
import org.jhotdraw.draw.*;
import org.jhotdraw.xml.*;

import figures.Estado;
import figures.Transicao;

public class MarkovFactory extends DefaultDOMFactory {
    private static final Object[][] classTagArray = {
        { DefaultDrawing.class, "PDMDiagram" },
        { Estado.class, "state" },
        { Transicao.class, "dep" },
        { ListFigure.class, "list" },
        { TextFigure.class, "text" },
        { GroupFigure.class, "g" },
        { TextAreaFigure.class, "ta" },
     //  { SeparatorLineFigure.class, "separator" },
        
        { ChopRectangleConnector.class, "rectConnector" },
        { LocatorConnector.class, "locConnector" },
        { RelativeLocator.class, "relativeLocator" },
        { ArrowTip.class, "arrowTip" }
    };
    
    /** Creates a new instance. */
    public MarkovFactory() {
        for (Object[] o : classTagArray) {
            addStorableClass((String) o[1], (Class) o[0]);
        }
    }
}
