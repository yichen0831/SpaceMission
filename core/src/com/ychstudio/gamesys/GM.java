package com.ychstudio.gamesys;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GM {
    private static GM instance = new GM();

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
        assetManager.finishLoading();
    }
}
