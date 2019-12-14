package si.fri.rso.lib;

public class MongoMessageObject {
    private String _id;
    private String message;
    private String userName;
    private Integer channelId;

    public MongoMessageObject(String _id, String message, String userName, Integer channelId) {
        this._id = _id;
        this.message = message;
        this.userName = userName;
        this.channelId = channelId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void set_id(String _id) {
        this._id = _id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public String getUserName() {
        return userName;
    }

    public String get_id() {
        return _id;
    }
}
