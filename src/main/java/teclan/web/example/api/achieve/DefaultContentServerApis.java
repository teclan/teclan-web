package teclan.web.example.api.achieve;

import com.google.inject.Inject;

import teclan.web.api.AbstractServiceApis;
import teclan.web.db.model.ContentRecord;
import teclan.web.db.service.ActiveJdbcService;
import teclan.web.db.service.ContentRecordService;

public class DefaultContentServerApis extends AbstractServiceApis<ContentRecord> {

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
