package org.maroubra.pemsserver.api.models.alarms.responses;

package org.maroubra.pemsserver.api.models.alarms.requests;

public class CreatedAlarmResponse {
    
    
    import api.models.alarms.requests.CreateAlarmRequest;
    import api.models.alarms.requests.StartAlarmRequest;
    import api.models.alarms.requests.StopAlarmRequest;
    import rx.Subscription;
    
    import java.io.IOException;
    
    public class main {
    
        private static AlarmThread sAlarmThread = new AlarmThread();
    
        public static void main(String[] args) throws IOException {
            sAlarmThread.start();
            System.out.println("Test to start or stop alarm request");
    
            for (; ; ) {
                System.in.read();
                if (!sAlarmThread.isRunning()) {
                    CreateAlarmRequest.getInstance().post(new CreateAlarmRequest.StartAlarmRequest());
                } else {
                    CreateAlarmRequest.getInstance().post(new CreateAlarmRequest.StopAlarmRequest());
                }
            }
        }
    
        private static class AlarmThread extends Thread {
    
            private Subscription mStartAlarmSubscription;
            private Subscription mStopAlarmSubscription;
            private boolean mRunning;
    
            @Override
            public void run() {
                mStartAlarmSubscription = CreateAlarmRequest.getInstance().register(StartAlarmEvent.class, event -> {
                    mRunning = true;
                    synchronized (this) {
                        notify();
                    }
                });
                mStopAlarmSubscription = CreateAlarmRequest.getInstance().register(StopAlarmEvent.class, event -> {
                    mRunning = false;
                });
    
                for (; ; ) {
                    if (Thread.interrupted()) {
                        cleanUp();
                        return;
                    }
    
                    if (!mRunning) {
                        System.out.println("Stop Alarm");
    
                        try {
                            synchronized (this) {
                                wait();
                            }
                        } catch (InterruptedException e) {
                            cleanUp();
                            return;
                        }
    
                        System.out.println("Start Alarm");
                    }
    
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        cleanUp();
                        return;
                    }
    
                    triggerAlarm();
                }
            }
    
            private void triggerAlarm() {
                System.out.print(".");
            }
    
            private void cleanUp() {
                mStartAlarmSubscription.unsubscribe();
                mStopAlarmSubscription.unsubscribe();
            }
    
            public boolean isRunning() {
                return mRunning;
            }
        }
    }
}
