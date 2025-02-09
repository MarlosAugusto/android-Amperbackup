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
package com.amperbackup.client.logger

enum class Level(val tag: String) {
    UNKNOWN("U"),
    VERBOSE("V"),
    DEBUG("D"),
    INFO("I"),
    WARNING("W"),
    ERROR("E"),
    ASSERT("A");

    companion object {
        @JvmStatic
        fun fromTag(tag: String): Level = when (tag) {
            "V" -> VERBOSE
            "D" -> DEBUG
            "I" -> INFO
            "W" -> WARNING
            "E" -> ERROR
            "A" -> ASSERT
            else -> UNKNOWN
        }
    }
}
