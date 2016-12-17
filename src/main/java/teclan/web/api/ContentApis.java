package teclan.web.api;

import com.google.inject.Inject;

import teclan.web.db.model.ContentRecord;
import teclan.web.db.service.ActiveJdbcService;
import teclan.web.db.service.ContentRecordService;

public class ContentApis extends AbstractServiceApis<ContentRecord> {

    @Inject
    private ContentRecordService service;

    @Override
    public ActiveJdbcService<ContentRecord> getService() {
        return service;
    }

    @Override
    public String getResource() {
        return "content";
    }

}
