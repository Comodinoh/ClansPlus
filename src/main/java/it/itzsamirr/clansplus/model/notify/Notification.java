package it.itzsamirr.clansplus.model.notify;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
public final class Notification {
    private UUID player;
    private List<String> message;
    private boolean sendOnJoin;
    private boolean strict;
    private boolean disabled;
    private long delay, timeStamp;
    private Notification(UUID player, List<String> message, boolean sendOnJoin, boolean strict, boolean disabled, long delay, long timeStamp){
        this.player = player;
        this.message = message;
        this.sendOnJoin = sendOnJoin;
        this.strict = strict;
        this.disabled = disabled;
        this.delay = delay;
        this.timeStamp = timeStamp;
    }

    public boolean isValid(long expire){
        return !disabled && System.currentTimeMillis()-timeStamp < expire;
    }

    public static Builder newBuilder(UUID player){
        return new Builder(player);
    }

    public static final class Builder{
        private UUID player;
        private List<String> message;
        private boolean sendOnJoin, strict, disabled;
        private long delay = 0;
        private final long timeStamp;
        private Builder(UUID player){
            this.player = player;
            this.sendOnJoin = true;
            this.strict = false;
            this.timeStamp = new Date().getTime();
        }

        public Builder message(String... message){
            this.message = Arrays.asList(message);
            return this;
        }

        public Builder sendOnJoin(boolean bool){
            this.sendOnJoin = bool;
            return this;
        }

        public Builder strict(boolean bool){
            this.strict = bool;
            return this;
        }

        public Builder disable(boolean bool){
            this.disabled = bool;
            return this;
        }

        public Builder delay(long delay){
            this.delay = delay;
            return this;
        }

        public Notification build(){
            return new Notification(player, message, sendOnJoin, strict, disabled, delay, timeStamp);
        }

    }


}
