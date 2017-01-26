package com.dylandelobel.spaceshooter;

import android.util.Log;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import java.io.IOException;

public class GameScene extends SimpleBaseGameActivity {

    private BoundCamera camera;
    private TextureRegion texture_Background;

    public Scene scene;
    public VertexBufferObjectManager vbom;

    Sprite sprite_Background;

    @Override
    public EngineOptions onCreateEngineOptions() {
        camera = new BoundCamera(0, 0, 1080, 1920);
        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(), camera);
        engineOptions.getRenderOptions().setDithering(true);
        engineOptions.getRenderOptions().getConfigChooserOptions().setRequestedMultiSampling(true);
        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
        return engineOptions;
    }

    @Override
    protected void onCreateResources() throws IOException {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("graphics/");

        BuildableBitmapTextureAtlas textureAtlas_Background = new BuildableBitmapTextureAtlas(getTextureManager(), 2048, 2048);
        texture_Background = BitmapTextureAtlasTextureRegionFactory.createFromAsset(textureAtlas_Background, getAssets(), "background.png");

        try {
            textureAtlas_Background.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 1, 0));
            textureAtlas_Background.load();
        } catch (ITextureAtlasBuilder.TextureAtlasBuilderException e) {
            Log.e("Error:", e.getMessage());
        }
    }

    @Override
    protected Scene onCreateScene() {

        scene = new Scene();

        vbom = getVertexBufferObjectManager();

        sprite_Background = new Sprite(camera.getCenterX(), camera.getCenterY(), texture_Background, vbom);
        sprite_Background.setScale(1.5f);

        scene.attachChild(sprite_Background);

        return scene;
    }
}
