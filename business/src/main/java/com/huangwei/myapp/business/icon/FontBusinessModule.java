package com.huangwei.business.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * Created by HuangWei on 2018/3/25.
 */

public class FontBusinessModule implements IconFontDescriptor {


    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return CustomIcons.values();
    }
}
