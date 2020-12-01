package com.freeproxy.vpnmaster.hotspot2.data;

import com.freeproxy.vpnmaster.hotspot2.data.api.IApiHelper;
import com.freeproxy.vpnmaster.hotspot2.data.local.ILocalHepler;
import com.freeproxy.vpnmaster.hotspot2.data.share_pref.ISharePreferHelper;

public interface IAppDataHelper extends IApiHelper, ILocalHepler, ISharePreferHelper {
}
