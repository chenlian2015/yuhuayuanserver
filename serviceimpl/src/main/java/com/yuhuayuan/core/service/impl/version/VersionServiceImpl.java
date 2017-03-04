package com.yuhuayuan.core.service.impl.version;

import com.yuhuayuan.core.enums.GeneralStateEnum;
import com.yuhuayuan.core.dto.version.AppVersion;
import com.yuhuayuan.core.persistence.VersionMapper;
import com.yuhuayuan.core.service.appversion.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by cl on 2017/2/27.
 */
@Service
public class VersionServiceImpl implements VersionService {
    @Autowired
    VersionMapper versionMapperx;

    public boolean addVersion(AppVersion versionDto) {
        AppVersion version = versionMapperx.selectByPrimaryKey(1l);
        version.setId(12345l);
        versionMapperx.insert(version);
        return false;
    }

    public boolean updateVersionState(long id, GeneralStateEnum stateEnum) {
        return false;
    }

    public boolean updateVersion(AppVersion versionDto) {
        return false;
    }

    public AppVersion selectVersionByid(long id) {
        return null;
    }

    public boolean queryByName(String name, String value, String channel, int plateform, int id) {
        return false;
    }
}
