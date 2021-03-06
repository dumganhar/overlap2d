/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package com.uwsoft.editor.gdx.ui.thumbnailbox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.uwsoft.editor.gdx.stage.UIStage;
import com.uwsoft.editor.gdx.ui.DropDown;
import com.uwsoft.editor.gdx.ui.SelectionActions;
import com.uwsoft.editor.gdx.ui.payloads.AssetPayloadObject;
import com.uwsoft.editor.renderer.data.SimpleImageVO;

public class ImageThumbnailBox extends DraggableThumbnailBox {

    private float scaleSize = 1;

    private AtlasRegion region;

    public ImageThumbnailBox(UIStage s, AtlasRegion region) {
        super(s);
        Image img = new Image(region);
        this.region = region;

        if (img.getWidth() > thumbnailSize || img.getHeight() > thumbnailSize) {
            // resizing is needed
            float scaleFactor = 1.0f;
            if (img.getWidth() > img.getHeight()) {
                //scale by width
                scaleFactor = 1.0f / (img.getWidth() / thumbnailSize);
            } else {
                scaleFactor = 1.0f / (img.getHeight() / thumbnailSize);
            }
            scaleSize = scaleFactor;
            img.setScale(scaleFactor);

            img.setX((getWidth() - img.getWidth() * img.getScaleX()) / 2);
            img.setY((getHeight() - img.getHeight() * img.getScaleY()) / 2);
        } else {
            // put it in middle
            img.setX((getWidth() - img.getWidth()) / 2);
            img.setY((getHeight() - img.getHeight()) / 2);
        }

        addActor(img);


        Image payloadImg = new Image(region);
        AssetPayloadObject payload = new AssetPayloadObject();
        payload.assetName = region.name;
        payload.type = AssetPayloadObject.AssetType.Sprite;
        DraggableThumbnailEvent event = new DraggableThumbnailEvent() {
            @Override
            public void drop(AssetPayloadObject pld, float x, float y) {
                itemDropped(pld.assetName, x, y);
            }
        };

        initDragDrop(stage, payloadImg, scaleSize, scaleSize, payload, event);

        initAdditionalListeners();
    }

    public void initAdditionalListeners() {
        addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(button == Buttons.RIGHT){
					 showRightClickDropDown(region.name);
				}
				super.touchUp(event, x, y, pointer, button);
			}
        	
        });
    }

    public float getScaleSize() {
        return scaleSize;
    }

    protected void itemDropped(String assetName, float x, float y) {
        stage.getSandbox().getUac().createImage(assetName, x, y);
    }

	 private void showRightClickDropDown(final String regionName) {
		  DropDown dropDown = stage.mainDropDown;
		  dropDown.clearItems();
		  dropDown.addItem(SelectionActions.EDIT_ASSET_PHYSICS, "Edit Physics");
		  dropDown.initView(Gdx.input.getX(), Gdx.input.getY());

		  dropDown.setEventListener(new DropDown.SelectionEvent() {

				@Override public void doAction (int action) {
					 switch (action) {
						  case SelectionActions.EDIT_ASSET_PHYSICS:
								 stage.editPhysics(regionName);
								break;
						  default:
								break;
					 }
				}
		  });
	 }

}
