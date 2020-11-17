package com.example.init_app_vpn_native.data;

import com.example.init_app_vpn_native.data.api.IApiHelper;
import com.example.init_app_vpn_native.data.local.ILocalHepler;
import com.example.init_app_vpn_native.data.share_pref.ISharePreferHelper;

public interface IAppDataHelper extends IApiHelper, ILocalHepler, ISharePreferHelper {
}
