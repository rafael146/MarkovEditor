package main;


import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultApplicationModel;

public class App {
    
    /** Creates a new instance. */
    public static void main(String[] args) {
        Application app = null;
//        String os = System.getProperty("os.name").toLowerCase();
//        if (os.startsWith("mac")) {
//        //    app = new OSXApplication();
//        } else if (os.startsWith("win")) {
//          //  app = new DefaultMDIApplication();
//          //  app = new SDIApplication();
//        } else {
//            app = new MarkovApplication();
//         //   app = new SDIApplication();
//        }
        app = new MarkovApplication();
        
        
        
        DefaultApplicationModel model = new MarkovApplicationModel();
        model.setName("Markov Decision Process");
    //    model.setVersion(Main.class.getPackage().getImplementationVersion());
//        model.setCopyright("Copyright 2006-2010 (c) by the authors of JHotDraw and all its contributors.\n" +
//                "This software is licensed under LGPL and Creative Commons 3.0 Attribution.");
        model.setViewClassName("main.MarkovView");
        app.setModel(model);
        
       
        app.launch(args);
    }
    
}
