/* 
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 * Copyright IBM Corp. 2024, 2025
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 */
package org.quartz;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


import org.junit.jupiter.api.Test;
import org.quartz.core.QuartzScheduler;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VersionTest  {
    @SuppressWarnings("unused")
    private static final String SNAPSHOT_SUFFIX = "-SNAPSHOT";
    @SuppressWarnings("unused")
    private static final String PROTOTYPE_SUFFIX = "-PROTO";

    @Test
    void testVersionParsing() {
        assertNonNegativeInteger(QuartzScheduler.getVersionMajor());
        assertNonNegativeInteger(QuartzScheduler.getVersionMinor());

        String iter = QuartzScheduler.getVersionIteration();
        assertNotNull(iter);
        Pattern suffix = Pattern.compile("(\\d+)(-\\w+)?");
        Matcher m = suffix.matcher(iter);
        if (m.matches()) {
          assertNonNegativeInteger(m.group(1));
        } else {
          throw new RuntimeException(iter + " doesn't match pattern '(\\d+)(-\\w+)?'");
        } 

    }

    private void assertNonNegativeInteger(String s) {
        assertNotNull(s);
        boolean parsed = false;
        int intVal = -1;
        try {
            intVal = Integer.parseInt(s);
            parsed = true;
        } catch (NumberFormatException e) {}

        assertTrue(parsed, "Failed parse version segment: " + s);
        assertTrue(intVal >= 0);
    }
}

