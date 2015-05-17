package gameOfWar.affichage;

//import java.awt.Color;
//import java.awt.GradientPaint;
//import java.awt.Graphics;
//import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JButton;

public class Bouton extends JButton{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @SuppressWarnings("unused")
  private Icon name;
  private String names;
  
  public Bouton(String str, int x, int y){
    super(str);
    this.setLocation(x, y);
    this.names=str;
  }
  
  public Bouton(Icon str, int x, int y){
    super(str);
    this.setLocation(x, y);
    this.name=str;
  }
}
