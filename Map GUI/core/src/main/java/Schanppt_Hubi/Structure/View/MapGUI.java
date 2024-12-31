package Schanppt_Hubi.Structure.View;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import Schanppt_Hubi.Structure.Main;

import java.util.*;

public class MapGUI implements Screen {
    private Stage stage;
    private final Main game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private ShapeRenderer shapeRenderer;
    private Room[][] rooms;

    public MapGUI(final Main game) {

        rooms = new Room[4][4];
        this.game = game;
        shapeRenderer = new ShapeRenderer();
        // Initialize the camera and viewport with a custom transformation
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 850, 850); // Flip the Y-axis
        viewport = new ScreenViewport();
        viewport.apply();

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        // Center the camera
        camera.position.set(850 / 2f, 850 / 2f, 0);
        camera.update();

        // Load the tiled map
        TmxMapLoader mapLoader = new TmxMapLoader();
        tiledMap = mapLoader.load("MapGUI.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        RoomInfoAssign();
        InfoTest();

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        TextButton left = new TextButton("left", skin);
        left.setSize(200, 200);
        left.addListener(new ClickListener() {

        });
        //Button position here
        left.setPosition(0,0);
        // Add table to the stage
        stage.addActor(left);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.15f, 0.65f, 0.3f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update the camera and set the view for the renderer
        camera.update();
        mapRenderer.setView(camera);

        // Render the map
        mapRenderer.render();

        // Draw a shape at the given coordinates
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 0, 0, 1); // Red color
        shapeRenderer.rect(600, 712, 56, 56); // Draw a rectangle with width and height
        shapeRenderer.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    public void RoomInfoAssign() {
        MapObjects objects = tiledMap.getLayers().get("Rooms").getObjects();
        int currentIndexing = 0;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j <= 3; j++) {
                Rectangle rect = ((RectangleMapObject) objects.get(currentIndexing++)).getRectangle();
                rooms[i][j] = new Room(rect.x, rect.y, rect.width, rect.height);
                // Debugging statement to verify room assignment
                System.out.println("Assigned Room: x=" + rect.x + ", y=" + rect.y + ", width=" + rect.width + ", height=" + rect.height);
            }
        }
    }

    public void InfoTest() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= 3; j++) {
                if (rooms[i][j] != null) {
                    System.out.println("Room at (" + i + ", " + j + "): x=" + rooms[i][j].x + ", y=" + rooms[i][j].y);
                } else {
                    System.out.println("Room at (" + i + ", " + j + ") is not initialized.");
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        tiledMap.dispose();
        mapRenderer.dispose();
        shapeRenderer.dispose();
        stage.dispose();
    }
}

class Room {
    public float x, y, width, height;
    private HashMap<Integer, Player> playerMap = new HashMap<>();
    public Room(float x, float y, float width, float height) {
        playerMap = new HashMap<>();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void addPlayer(Player player)     {
        int id = player.Getid();
        playerMap.put(id, player);
    }

    public void DisplayPlayer()     {

    }
}

class Player    {
    private int id;
    boolean userControlled;
    public int x;
    public int y;
    public Player(int id, int x, int y, int userControlled)     {
        this.x=x;
        this.y=y;
        this.id=id;
        this.userControlled=true;
        if (userControlled==0)  {
            this.userControlled=false;
        }
    }
    public int Getid() {
        return id;
    }
}
