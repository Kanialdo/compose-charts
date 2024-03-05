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

public val Icons.AreaChart: ImageVector
    get() {
        if (_areaChart != null) {
            return _areaChart!!
        }
        _areaChart = materialIcon(name = "AreaChart") {
            materialPath {
                moveTo(3.0f, 13.0f)
                verticalLineToRelative(7.0f)
                horizontalLineToRelative(18.0f)
                verticalLineToRelative(-1.5f)
                lineToRelative(-9.0f, -7.0f)
                lineTo(8.0f, 17.0f)
                lineTo(3.0f, 13.0f)
                close()
                moveTo(3.0f, 7.0f)
                lineToRelative(4.0f, 3.0f)
                lineToRelative(5.0f, -7.0f)
                lineToRelative(5.0f, 4.0f)
                horizontalLineToRelative(4.0f)
                verticalLineToRelative(8.97f)
                lineToRelative(-9.4f, -7.31f)
                lineToRelative(-3.98f, 5.48f)
                lineTo(3.0f, 10.44f)
                verticalLineTo(7.0f)
                close()
            }
        }
        return _areaChart!!
    }

private var _areaChart: ImageVector? = null
