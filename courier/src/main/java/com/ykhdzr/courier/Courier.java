package com.ykhdzr.courier;

import java.util.HashMap;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ykhdzr on 9/15/17.
 */

public class Courier {

    private static Courier instance;

    private HashMap<String, Packet> packetGroup;

    private Packet packet;

    public static Courier getInstance() {
        if (instance == null) {
            instance = new Courier();
        }
        return instance;
    }

    public Courier packet(Packet packet) {
        this.packet = packet;

        if (packetGroup == null) {
            packetGroup = new HashMap<>();
        }

        if (packetGroup.get(packet.getTag()) == null) {
            packetGroup.put(packet.getTag(), packet);
        } else if (packet.getData() != null) {
            Packet srcPacket = packetGroup.get(packet.getTag());
            srcPacket.setData(packet.getData());
            packetGroup.put(packet.getTag(), packet);
        }

        return instance;
    }

    public void dispatch() {
        checkPacketNull();
        packetGroup.get(packet.getTag()).accept();
    }

    public void clear() {
        if (packetGroup != null) {
            packetGroup.clear();
        }
    }

    public <T> Subscription subscribe(final Action1<? super T> onNext,
        final Action1<Throwable> onError) {
        checkPacketNull();
        checkActionNull(onNext, onError);
        return packetGroup
            .get(packet.getTag())
            .getBehaviorSubject()
            .subscribe(onNext, onError);
    }

    private void checkPacketNull() {
        if (packet == null) {
            throw new IllegalArgumentException("event can not be null");
        }
    }

    private <T> void checkActionNull(final Action1<? super T> onNext,
        final Action1<Throwable> onError) {
        if (onNext == null) {
            throw new IllegalArgumentException("onNext can not be null");
        }
        if (onError == null) {
            throw new IllegalArgumentException("onError can not be null");
        }
    }
}
