/*
 * Copyright (c) 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.skara.bots.synclabel;

import org.openjdk.skara.bot.*;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class SyncLabelBotFactory implements BotFactory {
    private static final Logger log = Logger.getLogger("org.openjdk.skara.bots");

    static final String NAME = "synclabel";
    @Override
    public String name() {
        return NAME;
    }

    @Override
    public List<Bot> create(BotConfiguration configuration) {
        var bots = new ArrayList<Bot>();
        var specific = configuration.specific();
        for (var issueproject : specific.get("issueprojects").asArray()) {
            var project = configuration.issueProject(issueproject.get("project").asString());
            var inspect = issueproject.contains("inspect") ? Pattern.compile(issueproject.get("inspect").asString()) : Pattern.compile(".*");
            var ignore = issueproject.contains("ignore") ? Pattern.compile(issueproject.get("ignore").asString()) : Pattern.compile("\\b\\B");
            bots.add(new SyncLabelBot(project, inspect, ignore));
        }
        return bots;
    }
}
