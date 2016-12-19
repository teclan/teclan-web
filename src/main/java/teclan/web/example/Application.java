package teclan.web.example;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import teclan.web.core.RestapiApplication;
import teclan.web.example.api.ContentServerApis;

@Singleton
public class Application extends RestapiApplication {

    @Inject
    private ContentServerApis contentApis;

    @Override
    public void creatApis() {
        contentApis.initApis();
    }

}
