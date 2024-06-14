package org.kpmp.Notification;

import java.util.Objects;

public class NotificationEvent {
    private String origin;
    private String userId;

    public NotificationEvent(String userId, String origin){
        this.origin = origin;
        this.userId = userId;
    }


    public String getOrigin() {
        return this.origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationEvent that = (NotificationEvent) o;
        return Objects.equals(origin, that.origin) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, userId);
    }
}
