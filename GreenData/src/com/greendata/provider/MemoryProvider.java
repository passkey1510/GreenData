package com.greendata.provider;

public class MemoryProvider {
	private static MemoryProvider sInstance;

    public static MemoryProvider getInstance() {
        if (sInstance == null) {
            sInstance = new MemoryProvider();
        }
        return sInstance;
    }

    public static void resetInstance() {
        sInstance = null;
    }

    private MemoryProvider() {

    }
}
