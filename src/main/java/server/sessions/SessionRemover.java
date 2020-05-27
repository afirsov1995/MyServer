package server.sessions;

import com.artem.server.api.HttpHandler;
import com.artem.server.api.HttpSession;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

public class SessionRemover extends TimerTask {

    private final Map<String, HttpSession> sessions;
    private final long sessionLifeTime;

    public SessionRemover(Map<String, HttpSession> sessions, long sessionLifeTime){
        this.sessions = sessions;
        this.sessionLifeTime = sessionLifeTime;
    }

    @Override
    public void run() {
        Date nowTime = new Date();
        for (Map.Entry<String, HttpSession> entry: sessions.entrySet()) {
            String key = entry.getKey();
            HttpSession value = entry.getValue();
            Date lastRequestTime = value.getLastRequestTime();
            if (Math.abs(lastRequestTime.getTime() - nowTime.getTime()) > sessionLifeTime) {
                sessions.remove(key);
            }
        }
    }


}
