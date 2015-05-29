package gameOfWar.affichage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gameOfWar.config.Constante;
import gameOfWar.config.Coordonnees;
import gameOfWar.jeux.Equipe;
import gameOfWar.jeux.IA;
import gameOfWar.jeux.Plateau;
import gameOfWar.jeux.Vue;
import gameOfWar.robot.Char;
import gameOfWar.robot.Piegeur;
import gameOfWar.robot.Robot;
import gameOfWar.robot.Tireur;
import gameOfWar.robot.Worker;

/**
 * Affiche des menus en mode texte
 * 
 * @author clement
 */
public class MenuTexte {

  public static Equipe[] ans = new Equipe[2];
  public static Option oJPanel = new Option();
  private static Properties PROPERTIES = new Properties();

  /**
   * Efface l'écran si la console associé prend en charge les codes ANSI
   */
  public static void clearScreen() {
    //System.out.print("\u001b[2J");
    for (int i = 0; i < 50; i++) {
      System.out.println("");
    }
  }

  /**
   * Demande a l'utilisateur l'Equipe qu'il souhaite et l'adversaire qu'il souhaite
   * 
   * @param p le plateau qui servira a creer la vue de l'equipe
   * @return Equipe[] les equipes qui s'affronteront sur le plateau
   */
  public static Equipe[] initialisationEquipes(Plateau p) {
    clearScreen();
    System.out.println(quadrillage("INITIALISATION DES EQUIPES"));
    System.out
        .println("Qu'elle mode de jeux choisissez-vous ? 1) Joueur vs Joueur, 2) Joueur vs IA, 3) IA VS IA");
    int choix = Constante.secureInput(1, 3);
    System.out.println("A combien de robot sera fixer la partie ?");
    int nbRobot = Constante.secureInput(1, 5);
    switch (choix) {
      case 1:
        System.out.println("Vous allez jouer entre joueur !");
        ans[0] = new Equipe(choisitPays(), p, Constante.EQUIPE_UN, new Coordonnees(0, 0));
        choisirRobot(nbRobot, Constante.EQUIPE_UN, false);
        ans[1] =
            new Equipe(choisitPays(), p, Constante.EQUIPE_DEUX, new Coordonnees(p.getLargeur() - 1,
                p.getLongueur() - 1));
        choisirRobot(nbRobot, Constante.EQUIPE_DEUX, false);
        return ans;
      case 2:
        System.out.println("Vous allez jouer avec un joueur et un ordinnateur facile !");
        ans[0] = new Equipe(choisitPays(), p, Constante.EQUIPE_UN, new Coordonnees(0, 0));
        choisirRobot(nbRobot, Constante.EQUIPE_UN, false);
        ans[1] =
            new IA("IA", p, Constante.EQUIPE_DEUX, new Coordonnees(p.getLargeur() - 1,
                p.getLongueur() - 1));
        choisirRobot(nbRobot, Constante.EQUIPE_DEUX, true);
        return ans;
      case 3:
        System.out.println("Deux Ordinnateur facile vont s'affronter !");
        ans[0] = new IA("IA", p, Constante.EQUIPE_UN, new Coordonnees(0, 0));
        choisirRobot(nbRobot, Constante.EQUIPE_UN, true);
        ans[1] =
            new IA("IA 2", p, Constante.EQUIPE_DEUX, new Coordonnees(p.getLargeur() - 1,
                p.getLongueur() - 1));
        choisirRobot(nbRobot, Constante.EQUIPE_DEUX, true);
        return ans;
      default:
        return ans;
    }
  }

  /**
   * Demande a l'utilisateur la taille du plateau qu'il souhaite
   * 
   * @return Plateau le plateau voulu
   */
  public static Plateau initialisationPlateau(/*Integer x, Integer y, Integer ob*/) {
    clearScreen();
    System.out.println(quadrillage("INITIALISATION DU PLATEAU"));
    int  x1 = Constante.X_CHOICE;
    int  y1 = Constante.Y_CHOICE;
    int  nb = Constante.OBSTACLES_CHOICE;
    JOptionPane.showMessageDialog(null, "Les dimensions du plateaux sont donc ("+Constante.X_CHOICE+","+Constante.Y_CHOICE+"), et le pourcentage d'obstacles de : "+Constante.OBSTACLES_CHOICE);
      return new Plateau(x1, y1, nb);
  }

  /**
   * Demande au joueur de choisir c'est robot par rapport un nombre predefini.
   * 
   * @param nbRobot
   * @param noEquipe
   * @param bot
   */
  private static void choisirRobot(int nbRobot, int noEquipe, boolean bot) {
    Random rd = new Random();
    String[] listeRobotPourLesfeignants = new String[] {"Tireur", "Piegeur", "Char","Worker"};
    while (nbRobot != 0) {
      System.out.println("Choisis entre la liste des robots pour constituer ton equipe : ");
      for (int i = 0; i < listeRobotPourLesfeignants.length; i++) {
        System.out.println("N°" + (i + 1) + " " + listeRobotPourLesfeignants[i]);
      }

      int choix =
          (!bot) ? Constante.secureInput(1, listeRobotPourLesfeignants.length) : (rd
              .nextInt(listeRobotPourLesfeignants.length) + 1);
      switch (choix) {
        case 1:
          ans[noEquipe - 1].addRobot(new Tireur(ans[noEquipe - 1].getVue(), ans[noEquipe - 1]));
          break;
        case 2:
          ans[noEquipe - 1].addRobot(new Piegeur(ans[noEquipe - 1].getVue(), ans[noEquipe - 1]));
          break;
        case 3:
          ans[noEquipe - 1].addRobot(new Char(ans[noEquipe - 1].getVue(), ans[noEquipe - 1]));
          break;
        case 4:
          ans[noEquipe - 1].addRobot(new Worker(ans[noEquipe - 1].getVue(), ans[noEquipe - 1]));
          break;
        default:
          System.err.println("Hahahahahaha");
      }
      --nbRobot;
    }
  }

  /**
   * Choisir le Pays parmis une liste de pays proposer.
   * 
   * @return
   */
  private static String choisitPays() {
    System.out.println("Veuillez choisir votre pays parmis ceux-ci :");
    for (int i = 0; i < Constante.PAYS.length; i++) {
      System.out.println("\t" + i + " - " + Constante.PAYS[i]);
    }
    return Constante.PAYS[Constante.secureInput(0, Constante.PAYS.length - 1)];
  }

  private static String quadrillage(String message) {
    message = "\t| " + message + " |\n";
    String ans = "";
    for (int i = 0; i < message.length() - 2; i++) {
      ans += "*";
    }
    ans += "\n" + message + "\t";
    for (int i = 0; i < message.length() - 2; i++) {
      ans += "*";
    }
    return "\t" + ans + "\n";
  }

  public static boolean toto(){
    try {
      PROPERTIES.load(new FileReader("ressources/plateau.properties"));
      return true;
    } catch (IOException e) {
      System.err.println("Fichier plateau.properties introuvable, verifier son orthographe");
      return false;
    }
  }
}
