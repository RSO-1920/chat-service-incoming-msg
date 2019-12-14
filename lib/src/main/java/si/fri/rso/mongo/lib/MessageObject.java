package si.fri.rso.mongo.lib;

public class MessageObject {
    private String message;
    private String userName;
    private Integer channelId;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
