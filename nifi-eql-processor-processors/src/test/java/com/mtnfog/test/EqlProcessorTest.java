/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mtnfog.test;

import java.io.IOException;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Test;

import com.mtnfog.EqlProcessor;

public class EqlProcessorTest {

    private TestRunner runner;

    @Before
    public void init() {
    	runner = TestRunners.newTestRunner(EqlProcessor.class);
    }

    @Test
    public void testOnTrigger() throws IOException {
    	
        /*InputStream content = new ByteArrayInputStream("George Washington was president.".getBytes());
        
        runner.setProperty(EqlProcessor.IDYL_E3_HOST, "http://localhost:9000/");
        runner.setProperty(EqlProcessor.IDYL_E3_CONTEXT, "${uuid}");
        runner.enqueue(content);
        runner.run(1);
        runner.assertQueueEmpty();
        
        List<MockFlowFile> results = runner.getFlowFilesForRelationship(EqlProcessor.REL_SUCCESS);
        assertTrue("1 match", results.size() == 1);
        MockFlowFile result = results.get(0);

        String json = IOUtils.toString(runner.getContentAsByteArray(result), "UTF-8");
        Type listType = new TypeToken<HashSet<Entity>>(){}.getType();
        Set<Entity> entities = new Gson().fromJson(json, listType);
             
        assertEquals(1, entities.size());*/
        
    }
    
}