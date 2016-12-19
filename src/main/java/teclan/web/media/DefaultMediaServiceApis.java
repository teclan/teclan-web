package teclan.web.media;

import teclan.web.api.AbstractMediaServiceApis;

public class DefaultMediaServiceApis extends AbstractMediaServiceApis
        implements MediaServiceApis {

    @Override
    public String getResource() {
        return "media";
    }

}
