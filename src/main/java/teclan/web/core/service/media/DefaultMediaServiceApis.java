package teclan.web.core.service.media;

import java.io.File;

import teclan.web.api.AbstractMediaServiceApis;

public class DefaultMediaServiceApis extends AbstractMediaServiceApis
        implements MediaServiceApis {

    @Override
    public String getResource() {
        return "media";
    }

    @Override
    public void handle(File file) {
        // TO DO
        // 添加对刚刚上传的文件一些处理逻辑，默认不进行任何操作

    }

}
