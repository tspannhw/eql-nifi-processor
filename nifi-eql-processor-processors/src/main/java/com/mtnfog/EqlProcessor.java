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
package com.mtnfog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.commons.io.IOUtils;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.io.StreamCallback;
import org.apache.nifi.processor.util.StandardValidators;

@Tags({ "nlp, entities, extraction" })
@CapabilityDescription("Provides entity extraction through Idyl E3.")
@SeeAlso({})
@ReadsAttributes({ @ReadsAttribute(attribute = "", description = "") })
@WritesAttributes({ @WritesAttribute(attribute = "", description = "") })
public class EqlProcessor extends AbstractProcessor {

	public static final PropertyDescriptor IDYL_E3_HOST = new PropertyDescriptor.Builder()
			.name("Endpoint")
			.defaultValue("http://localhost:9000")
			.description("The Idyl E3 IP or host name endpoint such as http://localhost:9000.")
			.required(true)
			.addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
			.build();
	
	public static final Relationship REL_SUCCESS = new Relationship.Builder()
			.name("success").description("success").build();

	public static final Relationship REL_FAILURE = new Relationship.Builder()
			.name("failure").description("failure").build();

	private List<PropertyDescriptor> descriptors;
	private Set<Relationship> relationships;
	
	@Override
	protected void init(final ProcessorInitializationContext context) {
		
		descriptors = new ArrayList<PropertyDescriptor>();
		descriptors.add(IDYL_E3_HOST);
		descriptors = Collections.unmodifiableList(descriptors);

		relationships = new HashSet<Relationship>();
		relationships.add(REL_SUCCESS);
		relationships.add(REL_FAILURE);
		relationships = Collections.unmodifiableSet(relationships);		
		
	}

	@Override
	public Set<Relationship> getRelationships() {
		return this.relationships;
	}

	@Override
	public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
		return descriptors;
	}

	@OnScheduled
	public void onScheduled(final ProcessContext context) {

	}

	@Override
	public void onTrigger(final ProcessContext ctx,	final ProcessSession session) throws ProcessException {
		
		FlowFile flowFile = session.get();
		
		if (flowFile == null) {
			return;
		}
		
		final String host = ctx.getProperty(IDYL_E3_HOST).getValue();

		final AtomicReference<String> value = new AtomicReference<>();
		
		try {
						
			flowFile = session.write(flowFile, new StreamCallback() {
				
				@Override
				public void process(InputStream inputStream, OutputStream outputStream) throws IOException {
					
					String input = IOUtils.toString(inputStream, Charset.forName("UTF-8"));
					
					/*EntityExtractionResponse response = idylE3Client.extract(input, confidence, context, documentId, language, type);
					
					final String json = gson.toJson(response.getEntities());
					value.set(json);
					IOUtils.write(json, outputStream, Charset.forName("UTF-8"));*/									
	
				}
				
			});
			
	        flowFile = session.putAttribute(flowFile, "idyl-e3-response", value.get());
	
			session.transfer(flowFile, REL_SUCCESS);
			
		} catch (Exception ex) {
			
			getLogger().error(String.format("Unable to extract entities using Idyl E3 at: %s. Exception: %s", host, ex.getMessage()), ex);
			session.transfer(flowFile, REL_FAILURE);
			
		}

	}
	
}
