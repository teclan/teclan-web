package teclan.web.example.service.achieve;

import teclan.web.db.service.achieve.AbstracActiveJdbcService;
import teclan.web.example.model.ContentRecord;
import teclan.web.example.service.ContentRecordService;

public class DefaultContentRecordService
        extends AbstracActiveJdbcService<ContentRecord>
        implements ContentRecordService {

}
