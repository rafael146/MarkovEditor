package exception;

import javax.swing.JOptionPane;


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


public class PesoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1766869276440092080L;

	public PesoException(){
		super("A soma das probabilidades de cada grupo deve ser menor ou igual a 1");
		JOptionPane.showMessageDialog(null, "Soma de probabilidades máxima <= 1.0");
	}
}
