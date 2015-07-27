package com.zc.manage.web.renderer;

import java.util.Map;

import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.zc.common.util.Utils;

public class PlayerRenderer implements RowRenderer {

    @Override
    @SuppressWarnings({ "unchecked" })
    public void render(Row row, Object data) throws Exception {
        Map.Entry<String, String> player = ((Map.Entry<String, String>) data);
        Utils.bindData(row, player.getKey());
//        Utils.bindData(row, (String) player.getValue());
        Html html = new Html();
        html.setContent(player.getValue());
        html.setParent(row);
    }

}
