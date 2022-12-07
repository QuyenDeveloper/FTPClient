/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FTP;

import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import javax.swing.text.NavigationFilter;
import javax.swing.text.Position;

/**
 *
 * @author ACER
 */
public class filter extends NavigationFilter {

    private int prefixLength;

    public filter(int prefixLength, JTextComponent component) {
        this.prefixLength = prefixLength;
        component.setCaretPosition(prefixLength);
    }

    public void setDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias) {
        fb.setDot(dot, bias);
    }

    public void moveDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias) {
        fb.moveDot(dot, bias);
    }
    
}
