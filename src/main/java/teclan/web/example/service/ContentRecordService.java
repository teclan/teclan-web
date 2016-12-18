package teclan.web.example.service;

import com.google.inject.ImplementedBy;

import teclan.web.db.service.ActiveJdbcService;
import teclan.web.example.model.ContentRecord;
import teclan.web.example.service.achieve.DefaultContentRecordService;

@ImplementedBy(DefaultContentRecordService.class)
public interface ContentRecordService extends ActiveJdbcService<ContentRecord> {

}
