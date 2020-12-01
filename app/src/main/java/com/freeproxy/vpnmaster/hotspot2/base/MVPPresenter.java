package com.freeproxy.vpnmaster.hotspot2.base;

public interface MVPPresenter<V> {
    void onAttact(V view);
    void onDetact();
    boolean isAttacted();
}
