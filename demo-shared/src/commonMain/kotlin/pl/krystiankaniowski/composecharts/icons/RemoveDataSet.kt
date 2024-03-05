/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.krystiankaniowski.composecharts.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val Icons.RemoveDataSet: ImageVector
    get() {
        if (_playlistRemove != null) {
            return _playlistRemove!!
        }
        _playlistRemove = materialIcon(name = "RemoveDataSet") {
            materialPath {
                moveTo(14.0f, 10.0f)
                horizontalLineTo(3.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(11.0f)
                verticalLineTo(10.0f)
                close()
                moveTo(14.0f, 6.0f)
                horizontalLineTo(3.0f)
                verticalLineToRelative(2.0f)
                horizontalLineToRelative(11.0f)
                verticalLineTo(6.0f)
                close()
                moveTo(3.0f, 16.0f)
                horizontalLineToRelative(7.0f)
                verticalLineToRelative(-2.0f)
                horizontalLineTo(3.0f)
                verticalLineTo(16.0f)
                close()
                moveTo(14.41f, 22.0f)
                lineTo(17.0f, 19.41f)
                lineTo(19.59f, 22.0f)
                lineTo(21.0f, 20.59f)
                lineTo(18.41f, 18.0f)
                lineTo(21.0f, 15.41f)
                lineTo(19.59f, 14.0f)
                lineTo(17.0f, 16.59f)
                lineTo(14.41f, 14.0f)
                lineTo(13.0f, 15.41f)
                lineTo(15.59f, 18.0f)
                lineTo(13.0f, 20.59f)
                lineTo(14.41f, 22.0f)
                close()
            }
        }
        return _playlistRemove!!
    }

private var _playlistRemove: ImageVector? = null
