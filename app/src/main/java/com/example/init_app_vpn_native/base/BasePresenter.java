package com.example.init_app_vpn_native.base;

public abstract class   BasePresenter<V extends MVPView> implements MVPPresenter<V> {
    Boolean isActivityAlive = false;
    public V view;
    @Override
    public void onAttact(V view) {
        isActivityAlive  = true;
        this.view = view;
    }

    @Override
    public void onDetact() {
        isActivityAlive = false;
    }

    @Override
    public boolean isAttacted() {
        return isActivityAlive;
    }
}
