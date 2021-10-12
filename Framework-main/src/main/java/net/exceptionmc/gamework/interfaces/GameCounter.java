package net.exceptionmc.gamework.interfaces;

@SuppressWarnings("unused")
public interface GameCounter {

    static void start(Integer seconds) {

    }

    static void stop() {

    }

    static boolean isCounting() {

        return false;
    }

    static Integer getSeconds() {

        return null;
    }

    static void setSeconds(Integer seconds) {

    }
}
