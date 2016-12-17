package teclan.web.api;

import com.google.inject.ImplementedBy;

@ImplementedBy(ContentApis.class)
public interface ServiceApis {

    public void initApis();

}
