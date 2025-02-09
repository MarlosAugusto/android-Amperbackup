/*
 * Nextcloud Android client application
 *
 * @author Alejandro Bautista
 * @author Chris Narkiewicz
 *
 * Copyright (C) 2017 Alejandro Bautista
 * Copyright (C) 2019 Chris Narkiewicz <hello@ezaquarii.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU AFFERO GENERAL PUBLIC LICENSE
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU AFFERO GENERAL PUBLIC LICENSE for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.owncloud.android.utils.glide;

import android.accounts.Account;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.amperbackup.client.account.CurrentAccountProvider;
import com.owncloud.android.MainApp;
import com.owncloud.android.lib.common.OwnCloudAccount;
import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientManagerFactory;
import com.owncloud.android.lib.common.operations.RemoteOperation;
import com.owncloud.android.lib.common.utils.Log_OC;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.InputStream;

/**
 * Fetcher with OwnCloudClient
 */
public class HttpStreamFetcher implements DataFetcher<InputStream> {

    private static final String TAG = HttpStreamFetcher.class.getName();
    private final String url;
    private final CurrentAccountProvider currentAccount;

    HttpStreamFetcher(final CurrentAccountProvider currentAccount, final String url) {
        this.currentAccount = currentAccount;
        this.url = url;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Account account = currentAccount.getCurrentAccount();
        OwnCloudAccount ocAccount = new OwnCloudAccount(account, MainApp.getAppContext());
        OwnCloudClient mClient = OwnCloudClientManagerFactory.getDefaultSingleton().
                getClientFor(ocAccount, MainApp.getAppContext());

        if (mClient != null) {
            GetMethod get;
            try {
                get = new GetMethod(url);
                get.setRequestHeader("Cookie", "nc_sameSiteCookielax=true;nc_sameSiteCookiestrict=true");
                get.setRequestHeader(RemoteOperation.OCS_API_HEADER, RemoteOperation.OCS_API_HEADER_VALUE);
                int status = mClient.executeMethod(get);
                if (status == HttpStatus.SC_OK) {
                    return get.getResponseBodyAsStream();
                } else {
                    mClient.exhaustResponse(get.getResponseBodyAsStream());
                }
            } catch (Exception e) {
                Log_OC.e(TAG, e.getMessage(), e);
            }
        }
        return null;
    }

    @Override
    public void cleanup() {
        Log_OC.i(TAG,"Cleanup");
    }

    @Override
    public String getId() {
        return url;
    }

    @Override
    public void cancel() {
        Log_OC.i(TAG,"Cancel");
    }
}
