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

package com.uwsoft.editor.gdx.ui;

import com.uwsoft.editor.gdx.stage.UIStage;
import com.uwsoft.editor.gdx.ui.CustomTabBar.TabBarEvent;

public class UILibraryBox extends ExpandableUIBox {

    private UIStage s;

    private AssetList assetList;
    private ComponentList componentList;
    private LibraryList libraryList;
    private ParticleList particleList;

    public UILibraryBox(final UIStage s) {
        super(s, 250, 350);
        this.s = s;
    }

    public void initContent() {
        mainLayer.clear();
        CustomTabBar tabBox = new CustomTabBar(s);
        tabBox.addTab("Assets");
        tabBox.addTab("UI");
        tabBox.addTab("Library");
        tabBox.addTab("Particles");
        tabBox.setWidth(getWidth() - 4);
        tabBox.initView();
        tabBox.setX(4);
        tabBox.setY(getHeight() - tabBox.getHeight() - 13);


        assetList = new AssetList(s, getWidth() - 10, getHeight() - 20);
        assetList.setX(7);
        assetList.setY(6);
        mainLayer.addActor(assetList);

        componentList = new ComponentList(s, getWidth() - 10, getHeight() - 20);
        componentList.setX(7);
        componentList.setY(6);
        mainLayer.addActor(componentList);
        componentList.setVisible(false);

        libraryList = new LibraryList(s, getWidth() - 10, getHeight() - 20);
        libraryList.setX(7);
        libraryList.setY(6);
        mainLayer.addActor(libraryList);
        libraryList.setVisible(false);

        particleList = new ParticleList(s, getWidth() - 10, getHeight() - 20);
        particleList.setX(7);
        particleList.setY(6);
        mainLayer.addActor(particleList);
        particleList.setVisible(false);

        tabBox.setTabEventListener(new TabBarEvent() {

            @Override
            public void tabOpened(int index) {
                switch (index) {
                    case 0:
                        assetList.setVisible(true);
                        componentList.setVisible(false);
                        libraryList.setVisible(false);
                        particleList.setVisible(false);
                        break;
                    case 1:
                        assetList.setVisible(false);
                        componentList.setVisible(true);
                        libraryList.setVisible(false);
                        particleList.setVisible(false);
                        break;
                    case 2:
                        assetList.setVisible(false);
                        componentList.setVisible(false);
                        libraryList.setVisible(true);
                        particleList.setVisible(false);
                        break;
                    case 3:
                        assetList.setVisible(false);
                        componentList.setVisible(false);
                        libraryList.setVisible(false);
                        particleList.setVisible(true);
                        break;
                    default:
                        break;
                }
            }
        });

        mainLayer.addActor(tabBox);
    }

    @Override
    protected void expand() {
        setHeight(expandedHeight);
        if (mainLayer != null) {
            mainLayer.setVisible(true);
        }
    }

    @Override
    protected void collapse() {
        setHeight(topImg.getHeight());
        if (mainLayer != null) {
            mainLayer.setVisible(false);
        }
    }
}
