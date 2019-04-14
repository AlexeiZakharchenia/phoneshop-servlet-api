package com.es.phoneshop.dosProtection;


import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DosServiceImpl implements DosService {

    private static volatile DosServiceImpl instance = null;
    private static int THRESHOLD = 10;
    private volatile Date lastResetDate = new Date();

    private Map<String, AtomicInteger> ipCallCount = new ConcurrentHashMap<>();

    private DosServiceImpl() {
    }

    public static DosServiceImpl getInstance() {
        DosServiceImpl localInstance = instance;

        if (instance == null) {
            synchronized (DosServiceImpl.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new DosServiceImpl();
                }

            }

        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        AtomicInteger count = ipCallCount.get(ip);
        if (count == null) {
            count = new AtomicInteger(0);
            ipCallCount.put(ip, count);
        }
        int value = count.incrementAndGet();
        return value < THRESHOLD;
    }

    private void mayBeReset() {
        Date date = new Date();
        if ((date.getTime()) - lastResetDate.getTime() > 60 * 1000) {
            lastResetDate = new Date();
            ipCallCount.clear();
            System.out.println("Clearing ip call map");
        }
    }
}
