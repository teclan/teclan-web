package teclan.web.db.service;

import java.util.List;
import java.util.Map;

import teclan.utils.db.ActiveRecord;
import teclan.web.db.Page;

public interface ActiveJdbcService<T extends ActiveRecord> {

    public List<T> all();

    public Page<T> fetch(int page, int limit, String query, Object... params);

    public T create(Map<String, Object> attributes);

    public void delete(long id);

    public void deleteAll(Long... ids);

    public T findById(long integerIdParam);

    public T update(long id, Object... namesAndValues);

    public T update(long id, Map<String, Object> attributes);

    public List<T> sync(List<Map<String, Object>> maps);

}