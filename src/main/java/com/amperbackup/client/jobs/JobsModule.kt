/*
 * Nextcloud Android client application
 *
 * @author Chris Narkiewicz
 * Copyright (C) 2019 Chris Narkiewicz <hello@ezaquarii.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.amperbackup.client.jobs

import android.content.Context
import android.content.ContextWrapper
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class JobsModule {

    @Provides
    @Singleton
    fun backgroundJobManager(context: Context, factory: BackgroundJobFactory): BackgroundJobManager {
        val configuration = Configuration.Builder()
            .setWorkerFactory(factory)
            .build()

        val contextWrapper = object : ContextWrapper(context) {
            override fun getApplicationContext(): Context {
                return this
            }
        }

        WorkManager.initialize(contextWrapper, configuration)
        val wm = WorkManager.getInstance(context)
        return BackgroundJobManagerImpl(wm)
    }
}
