package main;
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

import org.jhotdraw.app.Application;
import org.jhotdraw.app.DefaultApplicationModel;

public class App {
    
    public static void main(String[] args) {
        Application app = null;
        app = new MarkovApplication();
        
        
        
        DefaultApplicationModel model = new MarkovApplicationModel();
        model.setVersion(App.class.getPackage().getImplementationVersion());
        model.setName("Markov Decision Process");
        model.setViewClassName("main.MarkovView");
        app.setModel(model);
        
       
        app.launch(args);
    }
    
}
