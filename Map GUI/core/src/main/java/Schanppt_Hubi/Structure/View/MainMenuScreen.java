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

public class MainMenuScreen implements Screen {
    private final Main game;
    private Stage stage;
    private Texture background;
    private FitViewport viewport;

    public MainMenuScreen(final Main game) {
        this.game = game;
        viewport = new FitViewport(800, 600);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Create a skin (optional, for button styles)
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Create a start button
        TextButton startButton = new TextButton("Start Game", skin);
        startButton.setSize(200, 50);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        // Create a table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(startButton).size(200, 50);

        // Add table to the stage
        stage.addActor(table);

        // Load background texture
        background = new Texture(Gdx.files.internal("menu_background.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
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
        background.dispose();
    }
}
