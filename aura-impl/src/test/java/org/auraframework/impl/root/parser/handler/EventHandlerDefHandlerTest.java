/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.impl.root.parser.handler;

import org.auraframework.def.ComponentDef;
import org.auraframework.def.DefDescriptor;
import org.auraframework.def.EventDef;
import org.auraframework.impl.AuraImplTestCase;
import org.auraframework.impl.root.parser.XMLParser;
import org.auraframework.impl.source.StringSource;
import org.auraframework.impl.system.DefDescriptorImpl;
import org.auraframework.system.Parser.Format;

import org.auraframework.throwable.quickfix.InvalidDefinitionException;

public class EventHandlerDefHandlerTest extends AuraImplTestCase {

    public EventHandlerDefHandlerTest(String name) {
        super(name);
    }

    public void testEventHandlerDefHandler() throws Exception {
        XMLParser parser = XMLParser.getInstance();
        DefDescriptor<ComponentDef> descriptor = DefDescriptorImpl.getInstance("test:fakeparser", ComponentDef.class);
        StringSource<ComponentDef> source = new StringSource<>(descriptor,
                "<aura:component><aura:handler event='aura:click' action='{!c.action}'/></aura:component>", "myID",
                Format.XML);
        ComponentDef def = parser.parse(descriptor, source);
        assertNotNull(def);
    }

    public void testRegisterDuplicateEventNames() throws Exception {
        XMLParser parser = XMLParser.getInstance();
        DefDescriptor<ComponentDef> descriptor = DefDescriptorImpl.getInstance("test:fakeparser", ComponentDef.class);
        StringSource<ComponentDef> source = new StringSource<>(descriptor, "<aura:component>"
                + "<aura:registerevent name='dupName' type='aura:click'/>"
                + "<aura:registerevent name='dupName' type='aura:click'/>" + "</aura:component>", "myID", Format.XML);
        ComponentDef cd = parser.parse(descriptor, source);
        try {
            cd.validateDefinition();
            fail("Should have thrown AuraRuntimeException for registering two events with the same name");
        } catch (Exception e) {
            checkExceptionContains(e, InvalidDefinitionException.class, 
                    "There is already an event named 'dupName' registered on component 'test:fakeparser'.");
        }

    }

    /**
     * Events cannot be abstract
     * 
     * @throws Exception
     */
    public void testAbstractEvent() throws Exception {
        XMLParser parser = XMLParser.getInstance();
        DefDescriptor<EventDef> descriptor = DefDescriptorImpl.getInstance("aura:testevent", EventDef.class);
        StringSource<EventDef> source = new StringSource<>(descriptor,
                "<aura:event type='component' abstract='true'></aura:event>", "myID", Format.XML);
        EventDef ed = parser.parse(descriptor, source);
        try {
            ed.validateDefinition();
            fail("Should have thrown AuraRuntimeException for creating an abstract event");
        } catch (Exception e) {
            checkExceptionContains(e, InvalidDefinitionException.class, 
                    "Invalid attribute \"abstract\"");
        }
    }
}
