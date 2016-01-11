package com.ychstudio.gamesys;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GM {
    private static GM instance = new GM();
    
    public static final float PPM = 64;
    
    public static final short GROUND_BITS = 1;
    public static final short PLAYER_BITS = 2;

    private AssetManager assetManager;

    public static GM getInstance() {
        return instance;
    }

    public static AssetManager getAssetManager() {
        return instance.assetManager;
    }

    private GM() {
        assetManager = new AssetManager();
        assetManager.load("images/Rock.png", Texture.class);
        assetManager.load("images/SpaceRocket.png", Texture.class);
        assetManager.finishLoading();
    }
}
