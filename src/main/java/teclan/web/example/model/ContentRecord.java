package teclan.web.example.model;

import teclan.utils.db.ActiveRecord;

public class ContentRecord extends ActiveRecord {
    static {
        validatePresenceOf("content");
    }

}
