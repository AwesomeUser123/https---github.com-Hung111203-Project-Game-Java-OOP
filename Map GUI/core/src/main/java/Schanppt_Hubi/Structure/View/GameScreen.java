package Schanppt_Hubi.Structure.View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import Schanppt_Hubi.Structure.Main;

public class GameScreen implements Screen {
    private final Main game;
    private Stage stage;
    private Texture gameBackground;
    private FitViewport viewport;

    public GameScreen(final Main game) {
        this.game = game;
        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Create a skin (optional, for button styles)
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create a back to menu button
        TextButton backButton = new TextButton("Back to Menu", skin);
        backButton.setSize(200, 50);
        //  click on the button and every lines of code in addlistener will be executed
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        TextButton startGame = new TextButton("start game", skin);
        startGame.setSize(200, 50);
        startGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MapGUI(game));
            }
        });



        startGame.setPosition(500,150);
        // Add table to the stage
        stage.addActor(startGame);

        // Load game background texture
        gameBackground = new Texture(Gdx.files.internal("game_background.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(gameBackground, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {}

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        gameBackground.dispose();
    }
}
