package io.github.GRUMv2.EngSim.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;

public class Renderer {

    //final private OrthographicCamera camera;
    final private ShapeRenderer shapeRenderer;
    final private BitmapFont font;
    final private SpriteBatch spriteBatch;
    final private GlyphLayout glyphLayout;

    public Renderer(OrthographicCamera camera) {
        //this.camera = camera;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();
        glyphLayout = new GlyphLayout();
        shapeRenderer.setProjectionMatrix(camera.combined);
        spriteBatch.setProjectionMatrix(camera.combined);
    }

    public void update() {
        drawScreen();
    }

    public void dispose() {
        shapeRenderer.dispose();
        font.dispose();
        spriteBatch.dispose();
    }

    public void drawRect(Vector2 position, Vector2 size, Color color) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(position.x, position.y, size.x, size.y);
        shapeRenderer.end();
    }


    public void drawBorder(Vector2 position, Vector2 size, Color color, float borderWidth) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(color);
        Gdx.gl.glLineWidth(borderWidth);
        shapeRenderer.rect(position.x, position.y, size.x, size.y);
        shapeRenderer.end();
    }


    // TODO, low priority: Rewrite font rendering
    // There's no context of bounds to the existing system outside of the
    // hack added in drawScalingText
    // The entire system needs to be replaced with, at very minimum,
    // the concept of "objects with text" that control their own font scaling
    // rather than the current "draw a box of hardcoded size then draw text of hardcoded size on top"
    public float calcFontScale(Vector2 boundSize, String text, float maxSize) {
        // Scaling hack
        // There are numerous better ways of doing this that would require a rewrite of a lot of other stuff
        font.getData().setScale(1.0f);
        glyphLayout.setText(font, text);
        float scaleSize = Float.min(boundSize.x / glyphLayout.width, boundSize.y / glyphLayout.height);
        return Float.min(scaleSize, maxSize);
    }

    public float calcFontScale(Vector2 boundSize, String text) {
        return this.calcFontScale(boundSize, text, Float.MAX_VALUE);
    }


    public void drawText(String text, Vector2 position, Color color, float fontSize, int alignment) {
        spriteBatch.begin();
        font.getData().setScale(fontSize);
        glyphLayout.setText(font, text, color,
            3.0f,           // targetWidth; ignored if no wrapping/truncation, I think
            alignment,      // Alignment in respect to pos XY
            false           // Text wrap
        );
        font.draw(spriteBatch, glyphLayout,
            position.x,
            position.y + (glyphLayout.height / 2)
        );
        font.getData().setScale(1.0f);
        spriteBatch.end();
    }

    public void drawText(String text, Vector2 position, Color color, float fontSize) {
        this.drawText(text, position, color, fontSize, Align.left);
    }

    private void drawScreen() {
        ScreenUtils.clear(1, 1, 1, 1);
        // TODO_: figure out why any line below is necessary
        // Current verdict: No idea

        //Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(1, 1, 1, 1);
        //shapeRenderer.rect(0, 0, camera.viewportWidth, camera.viewportHeight);
        //shapeRenderer.end();
    }
}
