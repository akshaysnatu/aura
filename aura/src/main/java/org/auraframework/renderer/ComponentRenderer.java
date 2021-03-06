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
package org.auraframework.renderer;

import java.io.IOException;
import java.util.List;

import org.auraframework.Aura;
import org.auraframework.def.Renderer;
import org.auraframework.instance.BaseComponent;
import org.auraframework.throwable.quickfix.QuickFixException;

/**
 */
public class ComponentRenderer implements Renderer {

    @SuppressWarnings("unchecked")
    @Override
    public void render(BaseComponent<?, ?> component, Appendable out) throws IOException, QuickFixException {
        Object bodyAttribute = component.getAttributes().getValue("body");
        if(bodyAttribute !=null && bodyAttribute instanceof List) {
            List<BaseComponent<?, ?>> body = (List<BaseComponent<?, ?>>) bodyAttribute;
            for (BaseComponent<?, ?> c : body) {
                Aura.getRenderingService().render(c, out);
            }
        }
    }
}
