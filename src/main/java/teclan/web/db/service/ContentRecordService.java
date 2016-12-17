package teclan.web.db.service;

import com.google.inject.ImplementedBy;

import teclan.web.db.model.ContentRecord;
import teclan.web.db.service.achieve.DefaultContentRecordService;

@ImplementedBy(DefaultContentRecordService.class)
public interface ContentRecordService extends ActiveJdbcService<ContentRecord> {

}
