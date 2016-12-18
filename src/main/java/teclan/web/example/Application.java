package teclan.web.example;


import com.google.inject.Inject;
import com.google.inject.Singleton;

import teclan.web.RestapiApplication;
import teclan.web.example.api.achieve.DefaultContentServerApis;

@Singleton
public class Application extends RestapiApplication {

    @Inject
    private DefaultContentServerApis contentApis;

    @Override
    public void creatApis() {
        contentApis.initApis();
    }

}
