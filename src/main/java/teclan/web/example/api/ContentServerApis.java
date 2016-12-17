package teclan.web.example.api;

import com.google.inject.ImplementedBy;

import teclan.web.api.ServiceApis;
import teclan.web.example.api.achieve.DefaultContentServerApis;

@ImplementedBy(DefaultContentServerApis.class)
public interface ContentServerApis extends ServiceApis {

}
