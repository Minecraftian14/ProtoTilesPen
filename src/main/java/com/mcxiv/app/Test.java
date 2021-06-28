package com.mcxiv.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Test {

    public Texture texture;

    public Test() {
        texture = new Texture(Gdx.files.internal("assets/grass.png"));
    }

}
