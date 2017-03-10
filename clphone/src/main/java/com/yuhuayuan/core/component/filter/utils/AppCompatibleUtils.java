package com.yuhuayuan.core.component.filter.utils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by cl on 2017/3/8.
 */
@UtilityClass
public class AppCompatibleUtils {

    public int getAppIntVersion(final HttpServletRequest request) {
        final String appVersion = request.getHeader("app_version");
        if (StringUtils.isBlank(appVersion)) {//H5 兼容
            return 0;
        }
        final List<String> itemList = Splitter.on('.').omitEmptyStrings().trimResults().splitToList(appVersion);

        final List<String> itemList2 = itemList.stream().map(item -> {
            if (item.length() == 1) {
                return "0" + item;
            }
            return item;
        }).collect(Collectors.toList());

        return NumberUtils.toInt(Joiner.on("").join(itemList2));
    }

}
