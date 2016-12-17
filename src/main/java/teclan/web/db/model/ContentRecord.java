package teclan.web.db.model;

import teclan.utils.db.ActiveRecord;

public class ContentRecord extends ActiveRecord {
    static {
        validatePresenceOf("content");
    }

}
