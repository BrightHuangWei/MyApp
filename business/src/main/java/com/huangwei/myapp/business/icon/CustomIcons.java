package com.huangwei.business.icon;

import com.joanzapata.iconify.Icon;

/**
 * Created by HuangWei on 2018/3/25.
 */

public enum CustomIcons implements Icon {
    icon_scan('\ue610'),
    icon_ali_pay('\ue659')
    ;

    private char character;

    CustomIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}
