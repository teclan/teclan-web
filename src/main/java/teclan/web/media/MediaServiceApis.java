package teclan.web.media;

import com.google.inject.ImplementedBy;

import teclan.web.api.ServiceApis;

@ImplementedBy(DefaultMediaServiceApis.class)
public interface MediaServiceApis extends ServiceApis {

}
