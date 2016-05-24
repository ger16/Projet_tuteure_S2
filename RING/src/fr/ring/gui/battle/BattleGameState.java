package fr.ring.gui.battle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.command.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import fr.ring.gui.map.MapGameState;
import fr.ring.personnage.Imp;
import fr.ring.personnage.Personnage;

public class BattleGameState extends BasicGameState {
	
	public static final int ID = 4;
	
	private StateBasedGame game;
	private Image background;
	private BattleHud battleHud;
	private BattlePlayer player = new BattlePlayer(MapGameState.p);
	private Imp i = new Imp();
	private BattleEnnemy ennemy;
	
	
	public enum BattleCommand implements Command {
		ATTACK, DEFEND, HEAL, SURRENDER
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		i.initBattle();
		this.ennemy = new BattleEnnemy(i);
		this.game = game;
		background = new Image("fr/ring/gui/ressources/map/background/background2.png");
		this.player.init();
		this.ennemy.init();
	    InputProvider provider = new InputProvider(container.getInput());
	    provider.bindCommand(new KeyControl(Input.KEY_A), BattleCommand.ATTACK);
	    provider.bindCommand(new KeyControl(Input.KEY_D), BattleCommand.DEFEND);
	    provider.bindCommand(new KeyControl(Input.KEY_S), BattleCommand.HEAL);
	    provider.bindCommand(new KeyControl(Input.KEY_C), BattleCommand.SURRENDER);
	    provider.addListener(new BattleController(player, ennemy, game));
	    BattleController controller = new BattleController(player, ennemy, game);
	    this.battleHud = new BattleHud(controller, game);
	    this.battleHud.init(container, game);
		provider.addListener(controller);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		background.draw(0, 0, container.getWidth(), container.getHeight());
		this.player.render(container, g);
		this.ennemy.render(container, g);
		this.battleHud.render(container, g);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

	}

	@Override
	public int getID() {
		
		return this.ID;
	}

}