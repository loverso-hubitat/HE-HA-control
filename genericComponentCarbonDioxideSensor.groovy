/*

Copyright 2022

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

-------------------------------------------

Change history:

0.1.47 - mboisson - initial version

*/

metadata
{
    definition(name: "Generic Component Carbon Dioxide Sensor", namespace: "community", author: "community", importUrl: "https://raw.githubusercontent.com/mboisson/HE-HA-control/airthings/genericComponentCarbonDioxideSensor.groovy")
    {
        capability "CarbonDioxideMeasurement"
        capability "Refresh"
    }
    preferences {
        input name: "txtEnable", type: "bool", title: "Enable descriptionText logging", defaultValue: true
    }
}

void updated() {
    log.info "Updated..."
    log.warn "description logging is: ${txtEnable == true}"
}

void installed() {
    log.info "Installed..."
    device.updateSetting("txtEnable",[type:"bool",value:true])
    refresh()
}

void parse(String description) { log.warn "parse(String description) not implemented" }

void updateAttr(String aKey, aValue, String aUnit = ""){
    sendEvent(name:aKey, value:aValue, unit:aUnit)
}

void parse(List<Map> description) {
    description.each {
        if (it.name in ["carbon_dioxide"]) {
            if (txtEnable) log.info it.descriptionText
            unit="ppm"
            updateAttr("carbonDioxide", it.value, unit)
            sendEvent(it)
        }
    }
}

void refresh() {
    parent?.componentRefresh(this.device)
}
