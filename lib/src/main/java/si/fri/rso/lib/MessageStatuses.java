package si.fri.rso.lib;

public class MessageStatuses {
    private boolean isMessageSend;
    private boolean isMessageSaved;

    public MessageStatuses(boolean isMessageSend, boolean isMessageSaved) {
        this.isMessageSend = isMessageSend;
        this.isMessageSaved = isMessageSaved;
    }

    public boolean isMessageSaved() {
        return isMessageSaved;
    }

    public boolean isMessageSend() {
        return isMessageSend;
    }

    public void setMessageSaved(boolean messageSaved) {
        isMessageSaved = messageSaved;
    }

    public void setMessageSend(boolean messageSend) {
        isMessageSend = messageSend;
    }
}
