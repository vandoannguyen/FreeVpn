package com.freeproxy.vpnmaster.hotspot2.ui.switchRegion;

import com.freeproxy.vpnmaster.hotspot2.base.MVPView;
import com.freeproxy.vpnmaster.hotspot2.data.api.model.Country;

import java.util.List;

public interface ISwitchRegionView extends MVPView {

    void showDialogLoading(boolean b);

    void updateListCountry(List<Country> listCountry);
}
