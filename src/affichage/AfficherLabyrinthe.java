package affichage;
import algorithme.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import java.util.ArrayList;
import java.util.List;

public class AfficherLabyrinthe {
	private Labyrinthe labyrinthe;
	private final int TAILLE_CASE = 5;
	
	public AfficherLabyrinthe(Labyrinthe labyrinthe) {
        this.labyrinthe = labyrinthe;
    }
	
    //write a prpogram that displays the maze with slick2d
	//there is only one public method in this class : "public void afficherLabyrinthe(Coordonnee centre, GameContainer gc, Graphics g)"
	
	//this method displays the maze with the center at the coordinates "centre"
	//the maze is displayed with the graphics object "g"
	//the game container "gc" is used to get the width and height of the window
	
	//the maze is displayed as follows:
	// - the center of the maze is at the coordinates "centre"
	// - the maze is displayed in a grid of squares of size "TAILLE_CASE"
	// - the squares that are part of the maze are displayed in white
	// - the squares that are not part of the maze are displayed in black
	
	public void afficherLabyrinthe(Coordonnee centre, GameContainer gc, Graphics g) {
		int x = centre.getX() - (gc.getWidth() / TAILLE_CASE) / 2;
		int y = centre.getY() - (gc.getHeight() / TAILLE_CASE) / 2;
		for (int i = 0; i < gc.getWidth() / TAILLE_CASE; i++) {
			for (int j = 0; j < gc.getHeight() / TAILLE_CASE; j++) {
				Coordonnee c = new Coordonnee(x + i, y + j);
				if (labyrinthe.estNoir(c)) {
					g.setColor(Color.black);
				} else {
					g.setColor(Color.white);
				}
				g.fillRect(i * TAILLE_CASE, j * TAILLE_CASE, TAILLE_CASE, TAILLE_CASE);
			}
		}
	}
	
	//main method
	public static void main(String[] args) {
		try {
			AppGameContainer appgc;
			Labyrinthe l = new Labyrinthe();
			Coordonnee debut = l.getDebut();
			Coordonnee fin = l.getFin();
			Chemin chemin = l.trouverChemin(debut, fin);
			List<Coordonnee> cheminSet = new ArrayList<>();
			cheminSet.add(chemin.getCoordonnee());
			while (chemin.hasNext()) {
				cheminSet.add(chemin.next());
			}
			appgc = new AppGameContainer(new StateBasedGame("Afficher Labyrinthe") {
				@Override
				public void initStatesList(GameContainer gc) throws SlickException {
					addState(new BasicGameState() {
						private AfficherLabyrinthe afficherLabyrinthe = new AfficherLabyrinthe(l);
						private Coordonnee centre = new Coordonnee(Labyrinthe.getLargeur() / 2,
								Labyrinthe.getLargeur() / 2);

						@Override
						public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
						}

						@Override
						public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
							afficherLabyrinthe.afficherLabyrinthe(centre, gc, g);
						}

						@Override
						public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
						}

						@Override
						public int getID() {
							return 1;
						}
					});
				}
			});
			appgc.setDisplayMode(800, 600, false);
			appgc.start();
		} catch (SlickException ex) {
			ex.printStackTrace();
		}
	}
}