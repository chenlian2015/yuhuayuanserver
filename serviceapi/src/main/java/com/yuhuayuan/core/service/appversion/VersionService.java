package com.yuhuayuan.core.service.appversion;

import com.yuhuayuan.core.dto.version.AppVersion;
import com.yuhuayuan.core.enums.GeneralStateEnum;

/**
 * Created by cl on 2017/2/23.
 */
public interface VersionService {

    boolean addVersion(AppVersion versionDto);

    boolean updateVersionState(long id, GeneralStateEnum stateEnum);

    boolean updateVersion(AppVersion versionDto);

    AppVersion selectVersionByid(final long id);

    boolean queryByName(String name, String value, String channel, int plateform, int id);
}
