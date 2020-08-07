package tech.zeroed.libgdxzxing;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class QRTest implements ApplicationListener {

    SpriteBatch batch;
    TextureRegion[] qrcodes;
    OrthographicCamera cam;

    @Override
    public void create() {
        String input = "Zeroed";
        batch = new SpriteBatch();
        qrcodes = new TextureRegion[]{
                // Generate a basic QR code
                new QRGenerator(12).generate(input),
                // Generate a QR code with arcs on the eye borders
                new QRGenerator(12).setEyeBorderShape(QRGenerator.Shape.ARC).generate(input),
                // Generate a QR code with arcs on the eye borders and circular inner bits
                new QRGenerator(12).setEyeBorderShape(QRGenerator.Shape.ARC).setEyeInnerShape(QRGenerator.Shape.CIRCLE).generate(input),
                // Generate a QR code with arcs on the eye borders and circular everything else
                new QRGenerator(12).setEyeBorderShape(QRGenerator.Shape.ARC).setEyeInnerShape(QRGenerator.Shape.CIRCLE).setInnerShape(QRGenerator.Shape.CIRCLE).generate(input),
                // Generate a QR code where everything is a circle
                new QRGenerator(12).setEyeBorderShape(QRGenerator.Shape.CIRCLE).setEyeInnerShape(QRGenerator.Shape.CIRCLE).setInnerShape(QRGenerator.Shape.CIRCLE).generate(input),
                // Change up the colors
                new QRGenerator(12).primaryColor(Color.WHITE).secondaryColor(Color.BLACK).generate(input),
                new QRGenerator(12).primaryColor(Color.GREEN).secondaryColor(Color.BLACK).generate(input),
                // Generate a QR code with a larger border
                new QRGenerator(12).borderSize(3).generate(input),
                // Generate a larger QR code
                new QRGenerator(20).generate(input),

        };


        cam = new OrthographicCamera();

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

    }

    @Override
    public void render() {
        cam.update();
        Gdx.gl.glClearColor(0,0,0,0.7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        qrcode = new QRGenerator(30)
//                .generate("Zeroed");

        batch.begin();
        int x = 0, y = 0;
        for(TextureRegion qrcode : qrcodes) {
            if(x + qrcode.getRegionWidth() >= Gdx.graphics.getWidth()){
                x = 0;
                y += qrcode.getRegionHeight();
            }
            batch.draw(qrcode, x, y);
            x += qrcode.getRegionWidth();
            if(x >= Gdx.graphics.getWidth()){
                x = 0;
                y += qrcode.getRegionHeight();
            }
        }
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        for(TextureRegion qrcode : qrcodes)
            qrcode.getTexture().dispose();
        batch.dispose();
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = 30f;
        cam.viewportHeight = 30f * height/width;
        cam.update();
    }

    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.resizable  = false;
        config.samples = 4;
        new LwjglApplication(new QRTest(), config);
    }
}
