package com.freeproxy.vpnmaster.hotspot2.ui.main.fragment.vpn;

import com.freeproxy.vpnmaster.hotspot2.base.MVPView;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;

public interface IVpnView extends MVPView {
    void updateSpeed(String finalDown, String finalUp);

    void updateState(int i);

    void updateCountry(Country selectedCountry);

    void showLoading();

    void hideLoading();

    void setConectionStatus(int i);

    void updatePoint(int point);
}
