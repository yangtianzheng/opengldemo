package com.example.administrator.opengltest.camera.Filter;

import android.content.Context;
import android.content.res.Resources;

import com.example.administrator.opengltest.Filter.AFilter;

/**
 * Created by ytz on 2018/4/16.
 */

public class GrayFilter extends BaseFilter {



    public GrayFilter(Resources mRes) {
        super(mRes);
    }

    @Override
    protected void onCreate() {
        createProgramByAssetsFile("shader/base_vertex.sh",
                "shader/color/gray_fragment.frag");
    }

    @Override
    protected void onSizeChanged(int width, int height) {

    }
}
