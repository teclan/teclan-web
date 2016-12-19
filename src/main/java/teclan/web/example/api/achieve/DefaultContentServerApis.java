package teclan.web.example.api.achieve;

import com.google.inject.Inject;

import teclan.web.api.AbstractServiceApis;
import teclan.web.core.service.db.ActiveJdbcService;
import teclan.web.example.api.ContentServerApis;
import teclan.web.example.model.ContentRecord;
import teclan.web.example.service.ContentRecordService;

public class DefaultContentServerApis extends AbstractServiceApis<ContentRecord>
        implements ContentServerApis {

    @Inject
    private ContentRecordService service;

    @Override
    public ActiveJdbcService<ContentRecord> getService() {
        return service;
    }

    @Override
    public String getResource() {
        return "contents";
    }

}
