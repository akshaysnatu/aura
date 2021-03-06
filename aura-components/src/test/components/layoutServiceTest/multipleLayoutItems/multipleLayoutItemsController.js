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
({
    forward:function(cmp){
        $A.util.removeClass(cmp.find("ready").getElement(),"layoutDone");
        if($A.historyService.get().token === 'Step1'){
            $A.historyService.set("Step2");
            return;
        }else if($A.historyService.get().token === ''){
            $A.historyService.set("Step1");
        }
    },
    backward:function(cmp){
        $A.util.removeClass(cmp.find("ready").getElement(),"layoutDone");
        $A.layoutService.back();
    },
    layoutDone : function(cmp, event) {
        $A.util.addClass(cmp.find("ready").getElement(),"layoutDone");
        cmp._layoutChanged = true ;
        cmp._layoutChangeEvt = event;
    },
    removeLayoutDone : function(cmp) {
        $A.util.removeClass(cmp.find("ready").getElement(),"layoutDone");
    },
    layoutChanging : function(cmp, event) {
	cmp._layoutChanging = true ;
        cmp._beforeLayoutChangeEvt = event;
    }
})
